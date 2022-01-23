val sonatypeUsername: String? by project
val sonatypePassword: String? by project

plugins {
    java
    `java-library`
    `maven-publish`
    signing
    kotlin("jvm") version "1.6.10"
    kotlin("plugin.serialization") version "1.6.10"
    id("org.jetbrains.dokka") version "1.6.0"
    id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
    id("org.jetbrains.kotlinx.kover") version "0.4.4"
    id("org.sonarqube") version "3.3"
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


sonarqube {
    properties {
        property("sonar.projectKey", "Galarzaa90_TibiaKt")
        property("sonar.organization", "galarzaa90")
        property("sonar.host.url", "https://sonarcloud.io")
        property("sonar.coverage.jacoco.xmlReportPaths", "tibiakt-core/build/reports/kover/report.xml")
    }
}