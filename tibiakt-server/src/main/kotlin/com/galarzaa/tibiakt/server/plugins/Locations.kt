@file:OptIn(KtorExperimentalLocationsAPI::class)

package com.galarzaa.tibiakt.server.plugins

import com.galarzaa.tibiakt.core.enums.*
import io.ktor.application.*
import io.ktor.locations.*
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

@Location("/bazaar")
data class GetBazaar(
    val page: Int = 1,
    val type: BazaarType = BazaarType.CURRENT,
    val world: String? = null,
    val pvpType: AuctionPvpTypeFilter? = null,
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