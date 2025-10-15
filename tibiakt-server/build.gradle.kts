/*
 * Copyright © 2025 Allan Galarza
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

plugins {
    id("com.galarzaa.base")
    kotlin("plugin.serialization") version libs.versions.kotlin
    application
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("org.jetbrains.kotlinx.kover")
}

application {
    mainClass.set("com.galarzaa.tibiakt.server.ApplicationKt")
}

dependencies {
    implementation(project(":tibiakt-client"))

    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.client.okhttp)

    implementation(libs.bundles.ktor.server)
    implementation(libs.ktor.serialization.json)
    implementation(libs.kotlinx.serialization.json)

    implementation(libs.logback)
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    archiveFileName.set("tibiatk-server-shadow.jar")
}
