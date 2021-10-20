val sonatypeUsername: String? by project
val sonatypePassword: String? by project

plugins {
    java
    `java-library`
    `maven-publish`
    signing
    kotlin("jvm") version "1.5.30"
    kotlin("plugin.serialization") version "1.5.30"
    id("org.jetbrains.dokka") version "1.5.0"
    id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
    id("org.jetbrains.kotlinx.kover") version "0.3.0"
}

group = "com.galarzaa"
version = "1.0-SNAPSHOT"


nexusPublishing {
    repositories {
        sonatype {
            username.set(sonatypeUsername ?: System.getenv("SONATYPE_USER") ?: "")
            password.set(sonatypePassword ?: System.getenv("SONATYPE_PASSWORD") ?: "")
            nexusUrl.set(uri(Repo.releasesUrl))
            snapshotRepositoryUrl.set(uri(Repo.snapshotsUrl))
        }
    }
}

allprojects {
    repositories {
        mavenCentral()
    }
}

tasks.dokkaHtmlMultiModule.configure {
    outputDirectory.set(buildDir.resolve("dokka"))
}
