plugins {
    kotlin("jvm")
}


java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}


dependencies {
    api(project(":klf"))

    compileOnly("org.graalvm.sdk:graal-sdk:23.0.1")
}


ext["customArtifactId"] = "klf-graal"
ext["projectDescription"] = "Substitutions to make klf work in native images generated with GraalVM (e.g. in Quarkus)"

apply(from = "../gradle/scripts/publish-codinux.gradle.kts")