package com.galarzaa.tibiakt.server

import com.galarzaa.tibiakt.client.TibiaKtClient
import com.galarzaa.tibiakt.server.plugins.configureDataConversion
import com.galarzaa.tibiakt.server.plugins.configureLocations
import com.galarzaa.tibiakt.server.plugins.configureRouting
import com.galarzaa.tibiakt.server.plugins.configureStatusPages
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.serialization.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*

fun main() {
    val client = TibiaKtClient()
    embeddedServer(CIO, port = 8080) {
        install(ContentNegotiation) {
            json()
        }
        configureDataConversion()
        configureLocations()
        configureRouting(client)
        configureStatusPages()
    }.start(wait = true)
}