/*
 * Copyright © 2023 Allan Galarza
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

rootProject.name = "TibiaKt"

include("tibiakt-core")
include("tibiakt-client")
include("tibiakt-server")



dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            version("jsoup", "1.14.3")
            version("kotest", "5.6.2")
            version("kotlin", "1.8.10")
            version("kotlinLogging", "3.0.5")
            version("kotlinxDatetime", "0.4.0")
            version("kotlinxSerialization", "1.5.1")
            version("ktor", "2.3.0")
            version("logback", "1.4.5")

            library("kotlinx-datetime", "org.jetbrains.kotlinx", "kotlinx-datetime").versionRef("kotlinxDatetime")
            library("kotlinx-serialization-json", "org.jetbrains.kotlinx", "kotlinx-serialization-json").versionRef("kotlinxSerialization")

            library("jsoup", "org.jsoup", "jsoup").versionRef("jsoup")

            library("kotlinLogging", "io.github.microutils", "kotlin-logging").versionRef("kotlinLogging")

            library("kotest-runner-junit5", "io.kotest", "kotest-runner-junit5").versionRef("kotest")
            library("kotest-assertions-core", "io.kotest", "kotest-assertions-core").versionRef("kotest")
            library("kotest-property", "io.kotest", "kotest-property").versionRef("kotest")
            library("kotest-framework-datatest", "io.kotest", "kotest-framework-datatest").versionRef("kotest")

            library("ktor-client-encoding", "io.ktor", "ktor-client-encoding").versionRef("ktor")
            library("ktor-client-cio", "io.ktor", "ktor-client-cio").versionRef("ktor")
            library("ktor-client-core", "io.ktor", "ktor-client-core").versionRef("ktor")
            library("ktor-client-content-negotiation", "io.ktor", "ktor-client-content-negotiation").versionRef("ktor")

            library("ktor-client-mock", "io.ktor", "ktor-client-mock").versionRef("ktor")

            library("ktor-server-cio", "io.ktor", "ktor-server-cio").versionRef("ktor")
            library("ktor-server-content-negotiation", "io.ktor", "ktor-server-content-negotiation").versionRef("ktor")
            library("ktor-server-data-conversion", "io.ktor", "ktor-server-data-conversion").versionRef("ktor")
            library("ktor-server-status-pages", "io.ktor", "ktor-server-status-pages").versionRef("ktor")
            library("ktor-server-locations", "io.ktor", "ktor-server-locations").versionRef("ktor")
            library("ktor-server-resources", "io.ktor", "ktor-server-resources").versionRef("ktor")

            library("ktor-serialization", "io.ktor", "ktor-serialization").versionRef("ktor")
            library("ktor-serialization-json", "io.ktor", "ktor-serialization-kotlinx-json").versionRef("ktor")

            library("logback", "ch.qos.logback", "logback-classic").versionRef("logback")

            bundle(
                "ktor-client", listOf(
                    "ktor-client-encoding",
                    "ktor-client-cio",
                    "ktor-client-core",
                    "ktor-client-content-negotiation",
                )
            )

            bundle(
                "ktor-server", listOf(
                    "ktor-server-cio",
                    "ktor-server-content-negotiation",
                    "ktor-server-data-conversion",
                    "ktor-server-status-pages",
                    "ktor-server-resources",
                )
            )

            bundle("kotest", listOf(
                "kotest-runner-junit5",
                "kotest-assertions-core",
                "kotest-property",
                "kotest-framework-datatest",
            ))
        }
    }
}
