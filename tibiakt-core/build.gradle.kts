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

import org.jetbrains.kotlin.gradle.dsl.JvmTarget


apply("../publish.gradle.kts")

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    alias(libs.plugins.dokka)
    id("io.gitlab.arturbosch.detekt")
    id("org.jetbrains.kotlinx.kover")
}

tasks.test {
    useJUnitPlatform()
}


dependencies {
    implementation(libs.jsoup)
    implementation(libs.kotlinx.serialization.json)
    api(libs.kotlinx.datetime)
    testImplementation(libs.bundles.kotest)
}


dokka {
    dokkaSourceSets.main {
        includes.from("Module.md")
        sourceLink {
            localDirectory.set(file("src/main/kotlin"))
            remoteUrl("https://github.com/Galarzaa90/TibiaKt/blob/main/tibiakt-core/src/main/kotlin")
            remoteLineSuffix.set("#L")
        }
        externalDocumentationLinks {
            uri("https://api.ktor.io/")
        }
    }
}



kotlin {
    explicitApi()
    compilerOptions {
        jvmTarget = JvmTarget.JVM_1_8
    }
}

detekt {
    config.setFrom(file(rootProject.file("detekt.yml")))
}


java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}
