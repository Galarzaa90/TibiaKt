val ktorVersion: String by project
val kotestVersion: String by project
val logbackVersion: String by project

plugins {
    application
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("com.github.johnrengelman.shadow") version "7.1.0"
}

application {
    mainClass.set("com.galarzaa.tibiakt.server.ApplicationKt")
}


dependencies {
    implementation(project(":tibiakt-client"))
    implementation("io.ktor:ktor-server-cio:$ktorVersion")
    implementation("io.ktor:ktor-serialization:$ktorVersion")
    implementation("io.ktor:ktor-locations:$ktorVersion")

    implementation("ch.qos.logback:logback-classic:$logbackVersion")
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    archiveFileName.set("tibiatk-server.jar")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>() {
    kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
}
