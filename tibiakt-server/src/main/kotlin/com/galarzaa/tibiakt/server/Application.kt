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

package com.galarzaa.tibiakt.server

import com.galarzaa.tibiakt.client.TibiaKtClient
import com.galarzaa.tibiakt.server.plugins.configureDataConversion
import com.galarzaa.tibiakt.server.plugins.configureLocations
import com.galarzaa.tibiakt.server.plugins.configureRouting
import com.galarzaa.tibiakt.server.plugins.configureStatusPages
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.cio.CIO
import io.ktor.server.engine.embeddedServer
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import kotlinx.serialization.json.Json

fun main() {
    embeddedServer(CIO, port = 8090, module = Application::tibiaKtModule).start(wait = true)
}

fun Application.tibiaKtModule() {
    val client = TibiaKtClient()
    install(ContentNegotiation) {
        json(Json {
            encodeDefaults = true
            ignoreUnknownKeys = true
        })
    }
    configureDataConversion()
    configureLocations()
    configureRouting(client)
    configureStatusPages()
}
