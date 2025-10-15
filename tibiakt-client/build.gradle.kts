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
    id("com.galarzaa.library")
    kotlin("plugin.serialization") version libs.versions.kotlin
    id("com.galarzaa.docs")
    id("com.galarzaa.linting")
    id("org.jetbrains.kotlinx.kover")
    id("com.galarzaa.publish")
}


tasks.test {
    useJUnitPlatform()
}

dependencies {
    api(project(":tibiakt-core"))
    api(platform(libs.ktor.bom))
    implementation(libs.ktor.client.core)
    implementation(libs.bundles.ktor.client.extras)
    implementation(libs.kotlinx.serialization.json)

    implementation(libs.kotlinLogging)

    testImplementation(libs.bundles.kotest)
    testImplementation(libs.ktor.client.mock)
}
