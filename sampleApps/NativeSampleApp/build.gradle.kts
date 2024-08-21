plugins {
    kotlin("multiplatform")
}


kotlin {
    val hostOs = System.getProperty("os.name")
    val isMingwX64 = hostOs.startsWith("Windows")
    val nativeTarget = when {
        hostOs == "Mac OS X" -> macosX64("native")
        hostOs == "Linux" -> linuxX64("native")
        isMingwX64 -> mingwX64("native")
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }

    nativeTarget.apply {
        binaries {
            executable {
                baseName = "NativeSampleApp"

                entryPoint = "net.codinux.log.main"
            }
        }
    }


    sourceSets {

        commonMain {
            dependencies {
                implementation(project(":klf"))
            }
        }

    }
}