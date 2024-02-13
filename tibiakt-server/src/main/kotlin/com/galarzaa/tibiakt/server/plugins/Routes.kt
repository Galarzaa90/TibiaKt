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

package com.galarzaa.tibiakt.server.plugins

import com.galarzaa.tibiakt.client.TibiaKtClient
import com.galarzaa.tibiakt.client.TibiaResponse
import com.galarzaa.tibiakt.core.models.bazaar.BazaarFilters
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.resources.get
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

internal fun Application.configureRouting(client: TibiaKtClient) {
    routing {
        get<Characters> { (name) -> call.respondOrNotFound(client.fetchCharacter(name)) }

        get<Worlds> { call.respond(client.fetchWorldOverview()) }
        get<Worlds.ByName> { (_, name) -> call.respondOrNotFound(client.fetchWorld(name)) }
        get<Guilds> { (name) -> call.respondOrNotFound(client.fetchGuild(name)) }
        get<Worlds.ByName.Guilds> { (it) -> call.respondOrNotFound(client.fetchWorldGuilds(it.name)) }
        get<NewsArchive> {
            if (it.start != null && it.end != null) {
                call.respond(client.fetchRecentNews(it.start, it.end, it.category?.toSet(), it.type?.toSet()))
            } else if (it.days != null) {
                call.respond(client.fetchRecentNews(it.days, it.category?.toSet(), it.type?.toSet()))
            } else {
                call.respond(HttpStatusCode.BadRequest, "Query parameters 'start' and 'end', or 'days' are required")
            }
        }
        get<News> { (newsId) -> call.respondOrNotFound(client.fetchNews(newsId)) }
        get<News.Html> { (it) ->
            val response = client.fetchNews(it.newsId)
            response.data?.content?.let {
                call.respondText(it, ContentType.Text.Html)
            } ?: call.respond(HttpStatusCode.NotFound, "")
        }
        get<KillStatistics> { (world) -> call.respondOrNotFound(client.fetchKillStatistics(world)) }
        get<EventsSchedule> { (year, month) -> call.respondOrNotFound(client.fetchEventsSchedule(year, month)) }
        get("/eventsSchedule") { call.respondOrNotFound(client.fetchEventsSchedule()) }
        get<WorldHouses> {
            call.respondOrNotFound(client.fetchHousesSection(it.world, it.town, it.type, it.status, it.order))
        }
        get<Houses> { (world, houseId) -> call.respondOrNotFound(client.fetchHouse(houseId, world)) }
        get<HighscoresPage> {
            val world = if (it.world.lowercase() == "all") null else it.world
            call.respondOrNotFound(client.fetchHighscoresPage(world, it.category, it.profession, it.page))
        }

        get<HighscoresComplete> {
            val world = if (it.world.lowercase() == "all") null else it.world
            call.respondOrNotFound(client.fetchHigscores(world, it.category, it.profession))
        }
        get<Bazaar> {
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
        get<Auctions> { (auctionId, detailsOnly, fetchAll) ->
            call.respondOrNotFound(
                client.fetchAuction(
                    auctionId,
                    detailsOnly != 0 && fetchAll == 0,
                    fetchItems = fetchAll != 0,
                    fetchMounts = fetchAll != 0,
                    fetchOutfits = fetchAll != 0
                )
            )
        }

        get<CMPosts> {
            if (it.start != null && it.end != null) {
                call.respond(client.fetchCMPostArchive(it.start, it.end, it.page))
            } else if (it.days != null) {
                call.respond(client.fetchCMPostArchive(it.days, it.page))
            } else {
                call.respond(HttpStatusCode.BadRequest, "Query parameters 'start' and 'end', or 'days' are required")
            }
        }

        get<Forums.Section> { call.respondOrNotFound(client.fetchForumSection(it.sectionId)) }
        get<Forums.Boards> { call.respondOrNotFound(client.fetchForumBoard(it.boardId, it.page, it.threadAge)) }
        get<Forums.Announcement> { call.respondOrNotFound(client.fetchForumAnnouncement(it.announcementId)) }
        get<Forums.Threads> { call.respondOrNotFound(client.fetchForumThread(it.threadId, it.page)) }
        get<Forums.Posts> { call.respondOrNotFound(client.fetchForumPost(it.postId)) }

        get<Leaderboards> { call.respondOrNotFound(client.fetchLeaderboard(it.world, it.rotation, it.page)) }

        get<CreaturesSection> { call.respondOrNotFound(client.fetchCreaturesSection()) }
        get<BoostableBosses> { call.respondOrNotFound(client.fetchBoostableBosses()) }
    }
}


private suspend inline fun <reified T : Any?> ApplicationCall.respondOrNotFound(body: TibiaResponse<T>) {
    respond(if (body.data != null) HttpStatusCode.OK else HttpStatusCode.NotFound, body)
}
