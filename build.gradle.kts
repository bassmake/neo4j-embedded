plugins {
    kotlin("jvm") version "1.4.30"
}

group = "sk.bsmk"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val kotestVersion = "4.4.1"
val neo4jDriverVersion = "4.2.0"
val neo4jVersion = "4.2.3"

dependencies {

    implementation("org.neo4j.driver:neo4j-java-driver:$neo4jDriverVersion")

    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("org.neo4j:neo4j:$neo4jVersion")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
