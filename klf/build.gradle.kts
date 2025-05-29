@file:Suppress("UnstableApiUsage")

import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinJvmCompilation
import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("multiplatform")
    id("com.android.library")
}


val assertKVersion: String by project

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
            testLogging {     // This is for logging and can be removed.
                events("passed", "skipped", "failed")
            }
        }

        // register additional compilations and test tasks for slf4j bindings tests
        createCompilation("logback", compilations, testRuns, "ch.qos.logback:logback-classic:$logbackVersion")
        createCompilation("log4j2", compilations, testRuns, "org.apache.logging.log4j:log4j-slf4j-impl:$log4j2Version")
        createCompilation("log4j1", compilations, testRuns, "org.slf4j:slf4j-log4j12:1.7.33") // slf4j-log4j12:1.7.34 relocates to reload4j
        createCompilation("jul", compilations, testRuns, "org.slf4j:slf4j-jdk14:$slf4jVersion")
        createCompilation("slf4jSimple", compilations, testRuns, "org.slf4j:slf4j-simple:$slf4jVersion")
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
    val logFormatterVersion: String by project

    sourceSets {
        commonMain.dependencies {
            implementation("net.codinux.kotlin:kmp-base:$kmpBaseVersion")
            implementation("net.codinux.log:log-formatter:$logFormatterVersion")

            api("net.codinux.log:log-data:1.0.0")
        }
        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation(kotlin("reflect"))

            implementation("com.willowtreeapps.assertk:assertk:$assertKVersion")
        }


        val javaAndNativeCommonMain by creating {
            dependsOn(commonMain.get())
            nativeMain.get().dependsOn(this)
        }
        val javaAndNativeCommonTest by creating {
            dependsOn(commonTest.get())
            nativeTest.get().dependsOn(this)
        }


        val javaCommonMain by creating {
            dependsOn(javaAndNativeCommonMain)
            jvmMain.get().dependsOn(this)

            dependencies {
                compileOnly("org.slf4j:slf4j-api:$slf4jVersion")
                compileOnly("org.apache.logging.log4j:log4j-core:$log4j2Version")
            }
        }
        val javaCommonTest by creating {
            dependsOn(javaAndNativeCommonTest)
            jvmTest.get().dependsOn(this)

            dependencies {
                implementation("io.mockk:mockk:1.13.5") {
                    exclude(group = "org.slf4j")
                }
            }
        }

        val androidMain by getting {
            dependsOn(javaCommonMain)
        }
        val androidUnitTest by getting {
            dependsOn(javaCommonTest)
        }

        val linuxAndMingwMain by creating {
            dependsOn(nativeMain.get())
            linuxMain.get().dependsOn(this)
            mingwMain.get().dependsOn(this)
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


fun createCompilation(name: String, compilations: NamedDomainObjectContainer<KotlinJvmCompilation>,
                      testRuns: NamedDomainObjectContainer<org.jetbrains.kotlin.gradle.targets.jvm.KotlinJvmTestRun>, mavenDependency: String) {
    val test = compilations.getByName("test")

    val compilationName = name + "Test"

    // see https://kotlinlang.org/docs/multiplatform-configure-compilations.html#create-a-custom-compilation
    val compilation = compilations.create(compilationName).apply {
        // Import test and its classpath as dependencies and establish internal visibility
        associateWith(test)

        defaultSourceSet.dependencies {
            implementation(mavenDependency)

            implementation(kotlin("test"))
            implementation("com.willowtreeapps.assertk:assertk:$assertKVersion")
        }
    }

    // Create a test task to run the tests produced by this compilation:
    testRuns.create(compilationName) {
        // Configure the test task
        setExecutionSourceFrom(compilation)
    }
}


if (File(projectDir, "../gradle/scripts/publish-codinux.gradle.kts").exists()) {
    apply(from = "../gradle/scripts/publish-codinux.gradle.kts")
}