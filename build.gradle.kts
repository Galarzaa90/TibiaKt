val sonatypeUsername: String by project
val sonatypePassword: String by project

plugins {
    java
    `java-library`
    `maven-publish`
    signing
    kotlin("jvm") version "1.5.30"
    kotlin("plugin.serialization") version "1.5.30"
    id("org.jetbrains.dokka") version "1.5.0"
    id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
}

group = "com.galarzaa"
version = "1.0-SNAPSHOT"

nexusPublishing {
    repositories {
        sonatype {
            username.set(sonatypeUsername)
            password.set(sonatypePassword)
            nexusUrl.set(uri("https://s01.oss.sonatype.org/service/local/"))
            snapshotRepositoryUrl.set(uri("https://s01.oss.sonatype.org/content/repositories/snapshots/"))
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