@file:OptIn(KtorExperimentalLocationsAPI::class)

package com.galarzaa.tibiakt.server.plugins

import com.galarzaa.tibiakt.client.TibiaKtClient
import com.galarzaa.tibiakt.client.models.TibiaResponse
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.response.*
import io.ktor.routing.*

internal fun Application.configureRouting(client: TibiaKtClient) {
    routing {
        get<GetCharacter> { (name) -> call.respondOrNotFound(client.fetchCharacter(name)) }
        get("/worlds") { call.respond(client.fetchWorldOverview()) }
        get<GetWorld> { (name) -> call.respondOrNotFound(client.fetchWorld(name)) }
        get<GetGuild> { (name) -> call.respondOrNotFound(client.fetchGuild(name)) }
        get<GetWorld.Guilds> { (params) -> call.respondOrNotFound(client.fetchWorldGuilds(params.name)) }
        get<GetNewsArchive> {
            if (it.start != null && it.end != null) {
                call.respond(client.fetchRecentNews(it.start, it.end))
            } else if (it.days != null) {
                call.respond(client.fetchRecentNews(it.days))
            } else {
                call.respond(
                    HttpStatusCode.BadRequest,
                    "Query parameters 'start' and 'end', or 'days' are required"
                )
            }
        }
        get<GetNews> { (newsId) -> call.respondOrNotFound(client.fetchNews(newsId)) }
        get<GetNews.Html> { (it) ->
            val response = client.fetchNews(it.newsId)
            response.data?.content?.let {
                call.respondText(it, ContentType.Text.Html)
            } ?: call.respond(HttpStatusCode.NotFound, "")
        }
        get<GetKillStatistics> { (world) -> call.respondOrNotFound(client.fetchKillStatistics(world)) }
        get<GetEventsSchedule> { (year, month) -> call.respondOrNotFound(client.fetchEventsSchedule(year, month)) }
        get("/eventsSchedule") { call.respondOrNotFound(client.fetchEventsSchedule()) }
        get<GetWorldHouses> {
            call.respondOrNotFound(client.fetchHousesSection(it.world, it.town, it.type, it.status, it.order))
        }
        get<GetHouse> { (world, houseId) -> call.respondOrNotFound(client.fetchHouse(houseId, world)) }
    }
}

private suspend inline fun <reified T : Any?> ApplicationCall.respondOrNotFound(body: TibiaResponse<T>) {
    respond(
        if (body.data != null) HttpStatusCode.OK else HttpStatusCode.NotFound,
        body
    )
}




