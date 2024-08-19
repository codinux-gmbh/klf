pluginManagement {
    val kotlinVersion: String by settings
    val quarkusVersion: String by settings

    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
    plugins {
        kotlin("jvm") version kotlinVersion
        id("io.quarkus") version quarkusVersion
    }
}

rootProject.name="QuarkusSampleApp"
