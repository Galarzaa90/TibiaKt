@file:OptIn(KtorExperimentalLocationsAPI::class)

package com.galarzaa.tibiakt.server.plugins

import com.galarzaa.tibiakt.client.TibiaKtClient
import com.galarzaa.tibiakt.client.TibiaResponse
import com.galarzaa.tibiakt.core.models.bazaar.BazaarFilters
import io.ktor.application.Application
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.get
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing

internal fun Application.configureRouting(client: TibiaKtClient) {
    routing {
        get<GetCharacter> { (name) -> call.respondOrNotFound(client.fetchCharacter(name)) }
        get("/worlds") { call.respond(client.fetchWorldOverview()) }
        get<GetWorld> { (name) -> call.respondOrNotFound(client.fetchWorld(name)) }
        get<GetGuild> { (name) -> call.respondOrNotFound(client.fetchGuild(name)) }
        get<GetWorld.Guilds> { (params) -> call.respondOrNotFound(client.fetchWorldGuilds(params.name)) }
        get<GetNewsArchive> {
            if (it.start != null && it.end != null) {
                call.respond(client.fetchRecentNews(it.start, it.end, it.category?.toSet(), it.type?.toSet()))
            } else if (it.days != null) {
                call.respond(client.fetchRecentNews(it.days, it.category?.toSet(), it.type?.toSet()))
            } else {
                call.respond(HttpStatusCode.BadRequest, "Query parameters 'start' and 'end', or 'days' are required")
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
        get<GetHighscoresPage> { it ->
            val world = if (it.world.lowercase() == "all") null else it.world
            call.respondOrNotFound(client.fetchHighscoresPage(world, it.category, it.profession, it.page))
        }

        get<GetHighscoresComplete> { it ->
            val world = if (it.world.lowercase() == "all") null else it.world
            call.respondOrNotFound(client.fetchHigscores(world, it.category, it.profession))
        }
        get<GetBazaar> {
            val filters = BazaarFilters(
                world = it.world,
                pvpType = it.pvpType,
                battlEyeType = it.battlEyeType,
                vocation = it.vocation,
                minimumLevel = it.minLevel,
                maximumLevel = it.maxLevel,
                skill = it.skill,
                minimumSkillLevel = it.minSkillLevel,
                maximumSkillLevel = it.maxSkillLevel,
                orderDirection = it.orderDirection,
                orderBy = it.orderBy,
                searchString = it.searchString,
                searchType = it.searchType,
            )
            call.respondOrNotFound(client.fetchBazaar(it.type, filters, it.page))
        }
        get<GetAuction> { (auctionId, detailsOnly, fetchAll) ->
            call.respondOrNotFound(client.fetchAuction(auctionId,
                detailsOnly != 0 && fetchAll == 0,
                fetchItems = fetchAll != 0,
                fetchMounts = fetchAll != 0,
                fetchOutfits = fetchAll != 0))
        }

        get<GetCMPosts> { it ->
            if (it.start != null && it.end != null) {
                call.respond(client.fetchCMPostArchive(it.start, it.end, it.page))
            } else if (it.days != null) {
                call.respond(client.fetchCMPostArchive(it.days, it.page))
            } else {
                call.respond(HttpStatusCode.BadRequest, "Query parameters 'start' and 'end', or 'days' are required")
            }
        }

        get<GetForumSection> { call.respondOrNotFound(client.fetchForumSection(it.sectionId)) }
        get<GetForumBoard> { call.respondOrNotFound(client.fetchForumBoard(it.boardId, it.page, it.threadAge)) }
        get<GetForumAnnouncement> { call.respondOrNotFound(client.fetchForumAnnouncement(it.announcementId)) }

        get<GetLeaderboards> { it ->
            call.respondOrNotFound(client.fetchLeaderboards(it.world, null))
        }

        get<GetCreaturesSection> {
            call.respondOrNotFound(client.fetchCreaturesSection())
        }
    }
}


private suspend inline fun <reified T : Any?> ApplicationCall.respondOrNotFound(body: TibiaResponse<T>) {
    respond(if (body.data != null) HttpStatusCode.OK else HttpStatusCode.NotFound, body)
}




