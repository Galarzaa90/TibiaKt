/*
 * Copyright © 2024 Allan Galarza
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

val sonatypeUsername: String? by project
val sonatypePassword: String? by project

plugins {
    java
    `java-library`
    `maven-publish`
    signing
    kotlin("jvm") version "1.9.22"
    kotlin("plugin.serialization") version "1.9.22"
    id("io.github.gradle-nexus.publish-plugin") version "1.3.0"
    alias(libs.plugins.gradle.versions)
    alias(libs.plugins.detekt)
    alias(libs.plugins.dokka)
    alias(libs.plugins.sonarqube)
    alias(libs.plugins.kover)
}

group = "com.galarzaa"
version = "0.2.0"


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
        property("sonar.coverage.jacoco.xmlReportPaths", "build/reports/kover/report.xml")
    }
}

fun String.isStable(): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { uppercase().contains(it) }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    return stableKeyword || regex.matches(this)
}

tasks.withType<com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask> {
    rejectVersionIf {
        // If the current version is stable, reject all non-stable candidates
        currentVersion.isStable() && !candidate.version.isStable()
    }
    gradleReleaseChannel = "current"
    outputFormatter = "html"
}
