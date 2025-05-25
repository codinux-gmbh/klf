buildscript {
    val kotlinVersion: String by extra

    repositories {
        mavenCentral()
        google()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:7.4.2")

        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
    }
}


allprojects {
    group = "net.codinux.log"
    version = "1.8.4-SNAPSHOT"

    ext["projectDescription"] = "Kotlin (Multiplatform) logging facade for idiomatic logging in Kotlin"
    ext["sourceCodeRepositoryBaseUrl"] = "github.com/codinux-gmbh/klf"


    repositories {
        mavenCentral()
        google()
    }

}

tasks.register("publishAllToMavenLocal") {
    dependsOn(
            ":klf:publishToMavenLocal",

            ":klf-graal:publishToMavenLocal",

            ":klf-loki-appender:publishToMavenLocal"
    )
}

tasks.register("publishAll") {
    dependsOn(
            ":klf:publish",

            ":klf-graal:publish",

            ":klf-loki-appender:publish"
    )
}