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

package com.galarzaa


plugins {
    id("org.jetbrains.dokka")
}

dokka {
    dokkaSourceSets.configureEach {
        includes.from("Module.md")
        sourceLink {
            localDirectory.set(file("src/main/kotlin"))
            remoteUrl("https://github.com/Galarzaa90/TibiaKt/blob/main/tibiakt-core/src/main/kotlin")
            remoteLineSuffix.set("#L")
        }
        externalDocumentationLinks {
            register("Ktor") {
                url.set(uri("https://api.ktor.io/"))
            }
            register("kotlinx-datetime") {
                url.set(uri("https://kotlinlang.org/api/kotlinx-datetime/"))
                packageListUrl.set(uri("https://kotlinlang.org/api/kotlinx-datetime/kotlinx-datetime/package-list"))
            }
        }
    }
}
