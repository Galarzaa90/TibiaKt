/*
 * Copyright © 2022 Allan Galarza
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
    kotlin("jvm") version "1.8.10"
    kotlin("plugin.serialization") version "1.8.10"
    id("org.jetbrains.dokka") version "1.7.20"
    id("io.github.gradle-nexus.publish-plugin") version "1.2.0"
    id("org.jetbrains.kotlinx.kover") version "0.5.1"
    id("org.sonarqube") version "4.0.0.2929"
    id("com.github.ben-manes.versions") version "0.46.0"
    id("io.gitlab.arturbosch.detekt") version "1.22.0"
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

tasks.koverMergedHtmlReport {
    isEnabled = true
    htmlReportDir.set(layout.buildDirectory.dir("kover/"))
    excludes = listOf("com.galarzaa.tibiakt.server.*")
}


sonarqube {
    properties {
        property("sonar.projectKey", "Galarzaa90_TibiaKt")
        property("sonar.organization", "galarzaa90")
        property("sonar.host.url", "https://sonarcloud.io")
        property("sonar.coverage.jacoco.xmlReportPaths", "tibiakt-core/build/reports/kover/report.xml")
    }
}
