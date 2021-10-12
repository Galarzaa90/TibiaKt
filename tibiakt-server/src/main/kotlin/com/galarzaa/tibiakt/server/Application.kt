package com.galarzaa.tibiakt.server

import com.galarzaa.tibiakt.client.TibiaKtClient
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*

fun main() {
    val client = TibiaKtClient()
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
                call.respond(
                    if (response.data != null) HttpStatusCode.OK else HttpStatusCode.NotFound,
                    response
                )
            }
            get("/worlds") {
                val response = client.fetchWorldOverview()
                call.respond(response)
            }
            get("/worlds/{name}") {
                val name = call.parameters["name"] ?: return@get call.respondText(
                    "Bad Request",
                    status = HttpStatusCode.BadRequest
                )
                val response = client.fetchWorld(name)
                call.respond(
                    if (response.data != null) HttpStatusCode.OK else HttpStatusCode.NotFound,
                    response
                )
            }
            get("/guilds/{name}") {
                val name = call.parameters["name"] ?: return@get call.respondText(
                    "Bad Request",
                    status = HttpStatusCode.BadRequest
                )
                val response = client.fetchGuild(name)
                call.respond(
                    if (response.data != null) HttpStatusCode.OK else HttpStatusCode.NotFound,
                    response
                )
            }
            get("/worlds/{name}/guilds") {
                val name = call.parameters["name"] ?: return@get call.respondText(
                    "Bad Request",
                    status = HttpStatusCode.BadRequest
                )
                val response = client.fetchWorldGuilds(name)
                call.respond(
                    if (response.data != null) HttpStatusCode.OK else HttpStatusCode.NotFound,
                    response
                )
            }
            get("/news/") {
                val response = client.fetchRecentNews(90)
                call.respond(response)
            }
        }
    }.start(wait = true)
}