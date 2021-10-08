val ktorVersion = "1.6.3"
val kotestVersion = "4.6.3"

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.5.30"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.5.30"

    id("org.jetbrains.dokka") version "1.5.0"
}

group = "com.galarzaa"
version = "1.0-SNAPSHOT"


dependencies {
    implementation(project(":tibiakt-core"))
    implementation("io.ktor:ktor-client-encoding:$ktorVersion")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-java:$ktorVersion")
    implementation("io.ktor:ktor-serialization:$ktorVersion")
}