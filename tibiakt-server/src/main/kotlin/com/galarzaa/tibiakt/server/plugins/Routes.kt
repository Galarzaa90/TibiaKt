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
import com.galarzaa.tibiakt.client.model.TibiaResponse
import com.galarzaa.tibiakt.core.section.charactertrade.bazaar.model.BazaarFilters
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationCall
import io.ktor.server.resources.get
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import kotlinx.datetime.YearMonth

internal fun Application.configureRouting(client: TibiaKtClient) {
    routing {
        get("/healthcheck") {
            call.respond("true")
        }

        get<Characters> { (name) -> call.respondOrNotFound(client.fetchCharacter(name)) }

        get<Worlds> { call.respond(client.fetchWorldOverview()) }
        get<Worlds.ByName> { (_, name) -> call.respondOrNotFound(client.fetchWorld(name)) }
        get<Guilds> { (name) -> call.respondOrNotFound(client.fetchGuild(name)) }
        get<Worlds.ByName.Guilds> { (it) -> call.respondOrNotFound(client.fetchWorldGuilds(it.name)) }
        get<NewsArchive> {
            check(it.startAt >= it.endAt) { "endAt must be after startAt" }
            call.respond(client.fetchNewsArchive(it.startAt, it.endAt, it.category?.toSet(), it.type?.toSet()))
        }
        get<News> { (newsId) -> call.respondOrNotFound(client.fetchNewsArticleById(newsId)) }
        get<News.Html> { (it) ->
            val response = client.fetchNewsArticleById(it.newsId)
            response.data?.content?.let {
                call.respondText(it, ContentType.Text.Html)
            } ?: call.respond(HttpStatusCode.NotFound, "")
        }
        get<KillStatistics> { (world) -> call.respondOrNotFound(client.fetchKillStatistics(world)) }
        get<EventsSchedule> { call.respondOrNotFound(client.fetchEventsSchedule()) }
        get<EventsSchedule.ByMonth> { (_, year, month) ->
            call.respondOrNotFound(
                client.fetchEventsSchedule(YearMonth(year, month))
            )
        }
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
            call.respondOrNotFound(client.fetchHighscores(world, it.category, it.profession))
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
            call.respond(client.fetchCMPostArchive(it.startOn, it.endOn, it.page))
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
