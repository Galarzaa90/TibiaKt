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
import com.galarzaa.tibiakt.server.plugins.configureLocations
import com.galarzaa.tibiakt.server.plugins.configureRouting
import com.galarzaa.tibiakt.server.plugins.configureStatusPages
import io.ktor.client.HttpClient
import io.ktor.client.engine.ProxyBuilder
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.cio.CIO
import io.ktor.server.engine.embeddedServer
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import kotlinx.serialization.json.Json
import io.ktor.client.engine.cio.CIO as CIOClientEngine

/**
 * The default HTTP port to use, read from environment variable `TIBIAKT_PORT` or `8080` by default.
 *
 * For Docker deployments, it is recommended to change the mapped port instead.
 */
val applicationPort = System.getenv("TIBIAKT_PORT")?.toIntOrNull() ?: 8080
val socksProxyHost: String? = System.getenv("SOCKS_PROXY_HOST")
val socksProxyPort = System.getenv("SOCKS_PROXY_PORT")?.toIntOrNull() ?: 1080

fun main() {
    embeddedServer(CIO, port = applicationPort, module = Application::tibiaKtModule).start(wait = true)
}

fun Application.tibiaKtModule() {
    val client = TibiaKtClient(if (socksProxyHost != null) HttpClient(OkHttp) {
        engine {
            proxy = ProxyBuilder.socks(host = socksProxyHost, port = socksProxyPort)
        }

    } else HttpClient(CIOClientEngine))

    install(ContentNegotiation) {
        json(Json {
            encodeDefaults = true
            ignoreUnknownKeys = true
        })
    }
    configureLocations()
    configureRouting(client)
    configureStatusPages()
}
