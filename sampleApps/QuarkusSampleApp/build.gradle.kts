plugins {
    kotlin("jvm")
    id("io.quarkus")
}


group = "net.codinux.log"
version = "1.0.0-SNAPSHOT"


repositories {
    mavenCentral()
    mavenLocal()
}


kotlin {
    jvmToolchain(17)
}


val quarkusVersion: String by project

dependencies {
    implementation(enforcedPlatform("io.quarkus.platform:quarkus-bom:$quarkusVersion"))

    implementation("io.quarkus:quarkus-kotlin")
    implementation("io.quarkus:quarkus-rest")
    implementation("io.quarkus:quarkus-rest-jackson")

    implementation("io.quarkus:quarkus-smallrye-openapi")

    implementation("net.codinux.log:klf-graal:1.8.3")


    testImplementation("io.quarkus:quarkus-junit5")
    testImplementation("io.rest-assured:rest-assured")
}


tasks.withType<Test> {
    systemProperty("java.util.logging.manager", "org.jboss.logmanager.LogManager")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.compilerArgs.add("-parameters")
}