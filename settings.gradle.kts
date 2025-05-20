plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.9.0"
}

rootProject.name = "klf-Project"


include("klf")

include("klf-graal")

include("klf-loki-appender")

include("sampleApps:NativeSampleApp")

