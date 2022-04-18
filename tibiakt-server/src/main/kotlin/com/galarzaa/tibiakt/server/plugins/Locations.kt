@file:OptIn(KtorExperimentalLocationsAPI::class)

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
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.locations.KtorExperimentalLocationsAPI
import io.ktor.server.locations.Location
import io.ktor.server.locations.Locations
import java.time.LocalDate

internal fun Application.configureLocations() {
    install(Locations)
}

@Location("/characters/{name}")
data class GetCharacter(val name: String)

@Location("/worlds/{name}")
data class GetWorld(val name: String) {
    @Location("/guilds")
    data class Guilds(val parent: GetWorld)
}

@Location("guilds/{name}")
data class GetGuild(val name: String)


@Location("/news")
data class GetNewsArchive(
    val start: LocalDate? = null,
    val end: LocalDate? = null,
    val days: Int? = null,
    val type: List<NewsType>? = null,
    val category: List<NewsCategory>? = null,
)

@Location("/news/{newsId}")
data class GetNews(val newsId: Int) {
    @Location("/html")
    data class Html(val parent: GetNews)
}

@Location("/killStatistics/{world}")
data class GetKillStatistics(val world: String)

@Location("/eventsSchedule/{year}/{month}")
data class GetEventsSchedule(val year: Int, val month: Int)

@Location("/houses/{world}/town/{town}")
data class GetWorldHouses(
    val world: String,
    val town: String,
    val type: HouseType? = null,
    val status: HouseStatus? = null,
    val order: HouseOrder? = null,
)

@Location("/houses/{world}/{houseId}")
data class GetHouse(
    val world: String,
    val houseId: Int,
)

@Location("/highscores/{world}/{category}")
data class GetHighscoresPage(
    val world: String,
    val category: HighscoresCategory,
    val page: Int = 1,
    val profession: HighscoresProfession = HighscoresProfession.ALL,
)

@Location("/highscores/{world}/{category}/all")
data class GetHighscoresComplete(
    val world: String,
    val category: HighscoresCategory,
    val page: Int = 1,
    val profession: HighscoresProfession = HighscoresProfession.ALL,
)

@Location("/bazaar")
data class GetBazaar(
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

@Location("/auctions/{auctionId}")
data class GetAuction(val auctionId: Int, val detailsOnly: Int = 0, val fetchAll: Int = 0)

@Location("/cmPosts")
data class GetCMPosts(
    val start: LocalDate? = null,
    val end: LocalDate? = null,
    val days: Int? = null, val page: Int = 1,
)

@Location("/forum/sections/{sectionId}")
data class GetForumSection(val sectionId: Int)

@Location("/forum/boards/{boardId}")
data class GetForumBoard(val boardId: Int, val page: Int = 1, val threadAge: Int? = null)


@Location("/forum/announcements/{announcementId}")
data class GetForumAnnouncement(val announcementId: Int)

@Location("/forum/threads/{threadId}")
data class GetForumThread(val threadId: Int, val page: Int = 1)

@Location("/forum/post/{postId}")
data class GetForumPost(val postId: Int)

@Location("/leaderboards/{world}")
data class GetLeaderboards(
    val world: String,
    val rotation: Int? = null,
    val page: Int = 1,
)

@Location("/library/creatures")
class GetCreaturesSection