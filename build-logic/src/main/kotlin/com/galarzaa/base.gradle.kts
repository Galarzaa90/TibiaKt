package com.galarzaa

import org.jetbrains.kotlin.gradle.dsl.JvmTarget

group = "com.galarzaa"
version = Library.version

plugins {
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

kotlin {
    compilerOptions {
        freeCompilerArgs.add("-opt-in=kotlin.RequiresOptIn")
        freeCompilerArgs.add("-Xcontext-receivers")
        jvmTarget = JvmTarget.JVM_1_8
    }
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}
