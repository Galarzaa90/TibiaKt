package com.galarzaa.tibiakt.server

import com.galarzaa.tibiakt.client.TibiaKtClient
import com.galarzaa.tibiakt.server.plugins.configureDataConversion
import com.galarzaa.tibiakt.server.plugins.configureLocations
import com.galarzaa.tibiakt.server.plugins.configureRouting
import com.galarzaa.tibiakt.server.plugins.configureStatusPages
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.install
import io.ktor.server.cio.CIO
import io.ktor.server.engine.embeddedServer
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import kotlinx.serialization.json.Json

fun main() {
    val client = TibiaKtClient()
    embeddedServer(CIO, port = 8080) {
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
    }.start(wait = true)
}