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

import java.net.URL
val kotlinVersion: String by project
val ktorVersion: String by project
val kotestVersion: String by project
val jsoupVersion: String by project
val kotlinLoggingVersion: String by project
val logbackVersion: String by project
val kotlinxDatetime: String by project

apply("../publish.gradle.kts")

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("org.jetbrains.dokka")
    id("io.gitlab.arturbosch.detekt")
}

tasks.test {
    useJUnitPlatform()
}


dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")
    implementation("org.jsoup:jsoup:$jsoupVersion")

    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")

    api("org.jetbrains.kotlinx:kotlinx-datetime:$kotlinxDatetime")

    testImplementation("org.jetbrains.kotlin:kotlin-test:$kotlinVersion")

    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.kotest:kotest-property:$kotestVersion")
    testImplementation("io.kotest:kotest-framework-datatest:$kotestVersion")
}


tasks.withType<org.jetbrains.dokka.gradle.DokkaTaskPartial>().configureEach {
    dokkaSourceSets {
        configureEach {
            includes.from("Module.md")
            sourceLink {
                localDirectory.set(file("src/main/kotlin"))
                remoteUrl.set(URL("https://github.com/Galarzaa90/TibiaKt/blob/main/tibiakt-core/src/main/kotlin"))
                remoteLineSuffix.set("#L")
            }
            externalDocumentationLink {
                url.set(URL("https://api.ktor.io/"))
            }
        }
    }
}

kotlin {
    explicitApi()
}

detekt {
    config = files(rootProject.file("detekt.yml"))
}
