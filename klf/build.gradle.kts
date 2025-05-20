@file:Suppress("UnstableApiUsage")

import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinJvmCompilation
import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("multiplatform")
    id("com.android.library")
}


kotlin {
    @OptIn(ExperimentalKotlinGradlePluginApi::class)
    compilerOptions {
        // suppresses compiler warning: [EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING] 'expect'/'actual' classes (including interfaces, objects, annotations, enums, and 'actual' typealiases) are in Beta.
        freeCompilerArgs.add("-Xexpect-actual-classes")

        // avoid "variable has been optimized out" in debugging mode
        if (System.getProperty("idea.debugger.dispatch.addr") != null) {
            freeCompilerArgs.add("-Xdebug")
        }
    }


    jvmToolchain(11)


    val logbackVersion: String by project
    val slf4jVersion: String by project
    val log4j2Version: String by project

    jvm {
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()

            testLogging {     // This is for logging and can be removed.
                events("passed", "skipped", "failed")
            }
        }

        // register additional compilations and test tasks for slf4j bindings tests
        createCompilation("logbackTest", compilations, "ch.qos.logback:logback-classic:$logbackVersion")
        createCompilation("log4j2Test", compilations, "org.apache.logging.log4j:log4j-slf4j-impl:$log4j2Version")
        createCompilation("log4j1Test", compilations, "org.slf4j:slf4j-log4j12:$slf4jVersion")
        createCompilation("julTest", compilations, "org.slf4j:slf4j-jdk14:$slf4jVersion")
        createCompilation("slf4jSimpleTest", compilations, "org.slf4j:slf4j-simple:$slf4jVersion")
    }

    androidTarget { // name in Kotlin 1.9
        publishLibraryVariants("release")
    }

    js {
        binaries.library()

        browser {
            testTask {
                useKarma {
                    useChromeHeadless()
                    useFirefoxHeadless()
                }
            }
        }


        nodejs {
            testTask {
                useMocha {
                    timeout = "20s" // Mocha times out after 2 s, which may is too short for same tests
                }
            }
        }
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser {
            testTask {
                useKarma {
                    useChromeHeadless()
                    useFirefoxHeadless()
                }
            }

            // Uncomment the next line to apply Binaryen and get optimized wasm binaries
             applyBinaryen()
        }

        //nodejs() // WASM Node.js does currently not work
    }


    linuxX64()
    mingwX64()


    iosX64()
    iosArm64()
    iosSimulatorArm64()
    macosX64()
    macosArm64()
    watchosArm64()
    watchosSimulatorArm64()
    tvosArm64()
    tvosSimulatorArm64()

    applyDefaultHierarchyTemplate()


    val kmpBaseVersion: String by project

    val assertKVersion: String by project
    val assertJVersion: String by project

    sourceSets {
        commonMain {
            dependencies {
                implementation(kotlin("reflect"))

                implementation("net.codinux.kotlin:kmp-base:$kmpBaseVersion")
            }
        }
        commonTest {
            dependencies {
                implementation(kotlin("test"))

                implementation("com.willowtreeapps.assertk:assertk:$assertKVersion")
            }
        }


        val javaAndNativeCommonMain by creating {
            dependsOn(commonMain.get())
        }
        val javaAndNativeCommonTest by creating {
            dependsOn(commonTest.get())
        }


        val javaCommonMain by creating {
            dependsOn(javaAndNativeCommonMain)

            dependencies {
                compileOnly("org.slf4j:slf4j-api:$slf4jVersion")
                compileOnly("org.apache.logging.log4j:log4j-core:$log4j2Version")
            }
        }
        val javaCommonTest by creating {
            dependsOn(javaAndNativeCommonTest)

            dependencies {
                implementation("org.assertj:assertj-core:$assertJVersion")
                implementation("io.mockk:mockk:1.13.5") {
                    exclude(group = "org.slf4j")
                }

                implementation("ch.qos.logback:logback-classic:$logbackVersion")
            }
        }

        jvmMain {
            dependsOn(javaCommonMain)
        }
        jvmTest {
            dependsOn(javaCommonTest)
        }

        val androidMain by getting {
            dependsOn(javaCommonMain)
        }
        val androidUnitTest by getting {
            dependsOn(javaCommonTest)
        }

        jsMain.dependencies {

        }

        nativeMain {
            dependsOn(javaAndNativeCommonMain)
        }
        val linuxAndMingwMain by creating {
            dependsOn(nativeMain.get())
        }
        linuxMain {
            dependsOn(linuxAndMingwMain)
        }
        mingwMain {
            dependsOn(linuxAndMingwMain)
        }
    }
}

android {
    namespace = "net.codinux.log"
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")

    compileSdk = 33
    defaultConfig {
        minSdk = 21
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        buildConfig = true // so that BuildConfig gets generated
    }

    lint {
        abortOnError = false
    }

    testOptions {
        unitTests {
            // Otherwise we get this exception in tests:
            // Method e in android.util.Log not mocked. See https://developer.android.com/r/studio-ui/build/not-mocked for details.
            isReturnDefaultValues = true
        }

        unitTests.all {
            it.useJUnitPlatform()
        }
    }
}


tasks.withType<KotlinCompile>().configureEach {
    // Android needs JVM 11, but for all others we can set it to 8. Is also
    // good for some libraries that use klf and still use JVM 8.
    if (name.lowercase().contains("android") == false) {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }
}


fun createCompilation(name: String, compilations: NamedDomainObjectContainer<KotlinJvmCompilation>, mavenDependency: String) {
    val main = compilations.getByName("main")
    val test = compilations.getByName("test")

    // see https://kotlinlang.org/docs/multiplatform-configure-compilations.html#create-a-custom-compilation
    val compilation = compilations.create(name).apply {
        defaultSourceSet.dependencies {
            // Compile against the main compilation's compile classpath and outputs:
            implementation(main.compileDependencyFiles + main.output.classesDirs + test.output.classesDirs)
            implementation(project.project(":klf"))
            implementation(kotlin("test-junit"))
            implementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
            runtimeOnly("org.junit.jupiter:junit-jupiter-engine")
            implementation(mavenDependency)
        }
    }

    val taskName = "jvm${name.replaceFirstChar { it.uppercase() }}"
    val testTask = project.tasks.register(taskName, Test::class.java) {
        group = "verification"
        useJUnitPlatform()

        // Run the tests with the classpath containing the compile dependencies (including 'main'),
        // runtime dependencies, and the outputs of this compilation:
        classpath = compilation.compileDependencyFiles + compilation.runtimeDependencyFiles + compilation.output.allOutputs

        // Run only the tests from this compilation's outputs:
        testClassesDirs = compilation.output.classesDirs
    }

    tasks.named("jvmTest").configure {
        dependsOn(testTask)
    }
}


if (File(projectDir, "../gradle/scripts/publish-codinux.gradle.kts").exists()) {
    apply(from = "../gradle/scripts/publish-codinux.gradle.kts")
}