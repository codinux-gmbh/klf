plugins {
    kotlin("multiplatform")
}


kotlin {
    jvmToolchain(8)

    jvm()

    js {
        moduleName = "klf-loki-log-appender"
        binaries.executable()

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

//    wasmJs() // loki-log-appender is not compiled for WASM


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


    sourceSets {
        val lokiAppenderVersion: String by project

        val assertKVersion: String by project


        commonMain.dependencies {
            api(project(":klf"))

            api("net.codinux.log:loki-log-appender-base:$lokiAppenderVersion")
            implementation("net.codinux.log.web:loki-ktor-web-client:$lokiAppenderVersion")
        }

        commonTest.dependencies {
            implementation(kotlin("test"))

            implementation("com.willowtreeapps.assertk:assertk:$assertKVersion")
        }
    }
}



ext["artifactName"] = "klf-loki-appender"

ext["projectDescription"] = "Enables pushing logs collected with klf directly to Loki"

if (File(projectDir, "../gradle/scripts/publish-codinux.gradle.kts").exists()) {
    apply(from = "../gradle/scripts/publish-codinux.gradle.kts")
}