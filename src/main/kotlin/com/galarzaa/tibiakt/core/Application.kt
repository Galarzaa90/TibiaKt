package com.galarzaa.tibiakt.core

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*

fun main() {
    val client = Client()
    embeddedServer(CIO, port = 8080) {
        install(ContentNegotiation) {
            json()
        }
        routing {
            get("/characters/{name}") {
                val name = call.parameters["name"] ?: return@get call.respondText(
                    "Bad Request",
                    status = HttpStatusCode.BadRequest
                )
                val response = client.fetchCharacter(name)
                if (response.data == null)
                    call.respondText("Not Found", status = HttpStatusCode.NotFound)
                else {
                    call.respond(response)
                }
            }
        }
    }.start(wait = true)
}