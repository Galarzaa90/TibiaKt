/*
 * Copyright © 2023 Allan Galarza
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

 @file:UseSerializers(LocalDateSerializer::class)

package com.galarzaa.tibiakt.server.plugins

import com.galarzaa.tibiakt.core.enums.AuctionBattlEyeFilter
import com.galarzaa.tibiakt.core.enums.AuctionOrderBy
import com.galarzaa.tibiakt.core.enums.AuctionOrderDirection
import com.galarzaa.tibiakt.core.enums.AuctionSearchType
import com.galarzaa.tibiakt.core.enums.AuctionSkillFilter
import com.galarzaa.tibiakt.core.enums.AuctionVocationFilter
import com.galarzaa.tibiakt.core.enums.BazaarType
import com.galarzaa.tibiakt.core.enums.HighscoresCategory
import com.galarzaa.tibiakt.core.enums.HighscoresProfession
import com.galarzaa.tibiakt.core.enums.HouseOrder
import com.galarzaa.tibiakt.core.enums.HouseStatus
import com.galarzaa.tibiakt.core.enums.HouseType
import com.galarzaa.tibiakt.core.enums.NewsCategory
import com.galarzaa.tibiakt.core.enums.NewsType
import com.galarzaa.tibiakt.core.enums.PvpType
import com.galarzaa.tibiakt.core.serializers.LocalDateSerializer
import io.ktor.resources.Resource
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.resources.Resources
import kotlinx.serialization.UseSerializers
import java.time.LocalDate

internal fun Application.configureLocations() {
    install(Resources)
}

@Resource("/characters/{name}")
data class Characters(val name: String)

@Resource("/worlds")
class Worlds() {

    @Resource("/{name}")
    data class ByName(val parent: Worlds, val name: String) {
        @Resource("/guilds")
        data class Guilds(val parent: ByName)
    }
}

@Resource("/guilds/{name}")
data class Guilds(val name: String)


@Resource("/news")
data class NewsArchive(
    val start: LocalDate? = null,
    val end: LocalDate? = null,
    val days: Int? = null,
    val type: List<NewsType>? = null,
    val category: List<NewsCategory>? = null,
)

@Resource("/news/{newsId}")
data class News(val newsId: Int) {
    @Resource("/html")
    data class Html(val parent: News)
}

@Resource("/killStatistics/{world}")
data class KillStatistics(val world: String)

@Resource("/eventsSchedule/{year}/{month}")
data class EventsSchedule(val year: Int, val month: Int)

@Resource("/houses/{world}/town/{town}")
data class WorldHouses(
    val world: String,
    val town: String,
    val type: HouseType? = null,
    val status: HouseStatus? = null,
    val order: HouseOrder? = null,
)

@Resource("/houses/{world}/{houseId}")
data class Houses(
    val world: String,
    val houseId: Int,
)

@Resource("/highscores/{world}/{category}")
data class HighscoresPage(
    val world: String,
    val category: HighscoresCategory,
    val page: Int = 1,
    val profession: HighscoresProfession = HighscoresProfession.ALL,
)

@Resource("/highscores/{world}/{category}/all")
data class HighscoresComplete(
    val world: String,
    val category: HighscoresCategory,
    val page: Int = 1,
    val profession: HighscoresProfession = HighscoresProfession.ALL,
)

@Resource("/bazaar")
data class Bazaar(
    val page: Int = 1,
    val type: BazaarType = BazaarType.CURRENT,
    val world: String? = null,
    val pvpType: PvpType? = null,
    val battlEyeType: AuctionBattlEyeFilter? = null,
    val vocation: AuctionVocationFilter? = null,
    val minLevel: Int? = null,
    val maxLevel: Int? = null,
    val skill: AuctionSkillFilter? = null,
    val minSkillLevel: Int? = null,
    val maxSkillLevel: Int? = null,
    val orderDirection: AuctionOrderDirection? = null,
    val orderBy: AuctionOrderBy? = null,
    val searchString: String? = null,
    val searchType: AuctionSearchType? = null,
)

@Resource("/auctions/{auctionId}")
data class Auctions(val auctionId: Int, val detailsOnly: Int = 0, val fetchAll: Int = 0)

@Resource("/cmPosts")
data class CMPosts(
    val start: LocalDate? = null,
    val end: LocalDate? = null,
    val days: Int? = null, val page: Int = 1,
)

@Resource("/forums")
class Forums {

    @Resource("/sections/{sectionId}")
    data class Section(val parent: Forums, val sectionId: Int)

    @Resource("/boards/{boardId}")
    data class Boards(val parent: Forums, val boardId: Int, val page: Int = 1, val threadAge: Int? = null)


    @Resource("/announcements/{announcementId}")
    data class Announcement(val parent: Forums, val announcementId: Int)

    @Resource("/threads/{threadId}")
    data class Threads(val parent: Forums, val threadId: Int, val page: Int = 1)

    @Resource("/post/{postId}")
    data class Posts(val parent: Forums, val postId: Int)
}

@Resource("/leaderboards/{world}")
data class Leaderboards(
    val world: String,
    val rotation: Int? = null,
    val page: Int = 1,
)

@Resource("/library/creatures")
class CreaturesSection


@Resource("/library/bosses")
class BoostableBosses
