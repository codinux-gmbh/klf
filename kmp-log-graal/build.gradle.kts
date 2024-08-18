plugins {
    kotlin("jvm")
}


java {
    withSourcesJar()

    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}


dependencies {
    api(project(":kmp-log"))

    compileOnly("org.graalvm.sdk:graal-sdk:23.0.1")
}


ext["customArtifactId"] = "kmp-log-graal"
ext["projectDescription"] = "Substitutions to make kmp-log work in native images generated with GraalVM (e.g. in Quarkus)"

apply(from = "../gradle/scripts/publish-codinux.gradle.kts")