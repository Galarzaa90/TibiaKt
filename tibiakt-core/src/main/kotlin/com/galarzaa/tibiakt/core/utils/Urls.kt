/*
 * Copyright © 2022 Allan Galarza
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

package com.galarzaa.tibiakt.core.utils

import com.galarzaa.tibiakt.core.enums.AuctionBattlEyeFilter
import com.galarzaa.tibiakt.core.enums.AuctionOrderBy
import com.galarzaa.tibiakt.core.enums.AuctionOrderDirection
import com.galarzaa.tibiakt.core.enums.AuctionSearchType
import com.galarzaa.tibiakt.core.enums.AuctionSkillFilter
import com.galarzaa.tibiakt.core.enums.AuctionVocationFilter
import com.galarzaa.tibiakt.core.enums.AvailableForumSection
import com.galarzaa.tibiakt.core.enums.BazaarType
import com.galarzaa.tibiakt.core.enums.HighscoresBattlEyeType
import com.galarzaa.tibiakt.core.enums.HighscoresCategory
import com.galarzaa.tibiakt.core.enums.HighscoresProfession
import com.galarzaa.tibiakt.core.enums.HouseOrder
import com.galarzaa.tibiakt.core.enums.HouseStatus
import com.galarzaa.tibiakt.core.enums.HouseType
import com.galarzaa.tibiakt.core.enums.NewsCategory
import com.galarzaa.tibiakt.core.enums.NewsType
import com.galarzaa.tibiakt.core.enums.PvpType
import com.galarzaa.tibiakt.core.models.bazaar.BazaarFilters
import java.net.URLEncoder
import java.time.LocalDate
import java.time.YearMonth

private typealias P<A, B> = Pair<A, B>

/**
 * Build a URL to Tibia.com.
 *
 * @param section The desired section.
 * @param params The query arguments to pass.
 * @param test Whether to get a URL to the testing version of Tibia.com.
 */
public fun buildTibiaUrl(
    section: String,
    vararg params: Pair<String, Any?>,
    test: Boolean = false,
    anchor: String? = null,
): String {
    val baseUrl = if (test) "www.test.tibia.com" else "www.tibia.com"
    return "https://$baseUrl/$section/?${
        params.filter { it.second != null }.joinToString("&") { (name, value) ->
            "$name=${URLEncoder.encode(value.toString(), Charsets.ISO_8859_1)}"
        }
    }${anchor?.let { "#$it" } ?: ""}"
}

/**
 * Build a URL to Tibia.com.
 *
 * @param section The desired section.
 * @param subtopic The desired subtopic.
 * @param params The query arguments to pass.
 * @param test Whether to get a URL to the testing version of Tibia.com.
 */
public fun buildTibiaUrl(
    section: String,
    subtopic: String,
    vararg params: Pair<String, Any?>,
    test: Boolean = false,
    anchor: String? = null,
): String {
    val newParams = mutableListOf(*params)
    newParams.add(0, P("subtopic", subtopic))
    return buildTibiaUrl(section, params = newParams.toTypedArray(), test = test, anchor = anchor)
}

/**
 * Get the URL of a static asset in Tibia.com
 *
 * @param path The path to the asset.
 * @param test Whether to use the testing version of Tibia.com or not.
 */
public fun getStaticFileUrl(path: String, test: Boolean = false): String {
    return "https://${if (test) "test." else ""}static.tibia.com/${path.replace("//", "/")}"
}

/**
 * Get the URL of a static asset in Tibia.com
 *
 * @param path The path to the asset, represented as an array of directories with the filename at the end.
 * @param test Whether to use the testing version of Tibia.com or not.
 */
public fun getStaticFileUrl(vararg path: String, test: Boolean = false): String {
    return getStaticFileUrl(path.joinToString("/"), test)
}

/**
 * Get the URL to specific character.
 */
public fun getCharacterUrl(name: String): String = buildTibiaUrl("community", "characters", P("name", name))

/**
 * Get the URL to the list of guilds for a specific [world].
 */
public fun getWorldGuildsUrl(world: String): String = buildTibiaUrl("community", "guilds", P("world", world))

/**
 * Get the URL to a specific guild.
 */
public fun getGuildUrl(name: String): String =
    buildTibiaUrl("community", "guilds", P("GuildName", name), P("page", "view"))

/**
 * Get the URL to a house on a specific [world].
 */
public fun getHouseUrl(world: String, houseId: Int): String =
    buildTibiaUrl("community", "houses", P("page", "view"), P("world", world), P("houseid", houseId))

/**
 * Get the URL to the World Overview section
 */
public fun getWorldOverviewUrl(): String = buildTibiaUrl("community", "worlds")

/**
 * Get the URL to a specific world.
 */
public fun getWorldUrl(name: String): String = buildTibiaUrl("community", "worlds", P("world", name))

/**
 * Get the URL to the News Archive.
 */
public fun getNewsArchiveUrl(): String = buildTibiaUrl("news", "newsarchive")

/**
 * Get the post parameters to filter news in the News Archive
 */
public fun getNewArchiveFormData(
    startDate: LocalDate,
    endDate: LocalDate,
    categories: Set<NewsCategory>? = null,
    types: Set<NewsType>? = null,
): List<Pair<String, String>> {
    val data: MutableList<Pair<String, String>> = mutableListOf()
    startDate.apply {
        data.add(Pair("filter_begin_day", dayOfMonth.toString()))
        data.add(Pair("filter_begin_month", monthValue.toString()))
        data.add(Pair("filter_begin_year", year.toString()))
    }
    endDate.apply {
        data.add(Pair("filter_end_day", dayOfMonth.toString()))
        data.add(Pair("filter_end_month", monthValue.toString()))
        data.add(Pair("filter_end_year", year.toString()))
    }
    for (category: NewsCategory in categories ?: NewsCategory.values().toSet()) {
        data.add(Pair(category.filterName, category.value))
    }
    for (type: NewsType in types ?: NewsType.values().toSet()) {
        data.add(Pair(type.filterName, type.filterValue))
    }
    return data
}

/**
 * Get the URL to a specific news article
 */
public fun getNewsUrl(newsId: Int): String = buildTibiaUrl("news", "newsarchive", P("id", newsId))


/**
 * Get the URL to a specific forum section
 */
public fun getForumSectionUrl(sectionId: Int): String =
    buildTibiaUrl("forum", P("action", "main"), P("sectionid", sectionId))


/**
 * Get the URL to a specific forum section
 */
public fun getForumSectionUrl(section: AvailableForumSection): String =
    buildTibiaUrl("forum", P("subtopoic", section.subtopic))

/**
 * Get the URL to a specific forum section by its name.
 */
public fun getForumSectionUrl(sectionName: String): String = buildTibiaUrl("forum", sectionName)

public fun getForumBoardUrl(boardId: Int, page: Int = 1, threadAge: Int? = null): String = buildTibiaUrl(
    "forum",
    P("action", "board"),
    P("boardid", boardId),
    P("pagenumber", page),
    P("threadage", threadAge)
)


public fun getForumAnnouncementUrl(announcementId: Int): String =
    buildTibiaUrl("forum", P("action", "announcement"), P("announcementid", announcementId))

/**
 * Get the URL to a specific thread in the forums.
 */
public fun getForumThreadUrl(threadId: Int, page: Int = 1): String =
    buildTibiaUrl("forum", P("action", "thread"), P("threadid", threadId), P("pagenumber", page))

/**
 * Get the URL to the highscores, with the specified parameters.
 *
 * @param world If null, the highscores for all worlds will be displayed.
 */
public fun getHighscoresUrl(
    world: String?,
    category: HighscoresCategory = HighscoresCategory.EXPERIENCE_POINTS,
    vocations: HighscoresProfession = HighscoresProfession.ALL,
    page: Int = 1,
    battleEye: HighscoresBattlEyeType = HighscoresBattlEyeType.ANY_WORLD,
    worldTypes: Set<PvpType>? = null,
): String = buildTibiaUrl("community",
    P("subtopic", "highscores"),
    P("world", world),
    P("profession", vocations.value),
    P("currentpage", page),
    P("category", category.value),
    P("beprotection", battleEye.value),
    *worldTypes.orEmpty().map { P("${PvpType.highscoresQueryParam}[]", it.highscoresFilterValue) }.toTypedArray()
)

/**
 * Get the URL to the kill statistics of a specific world.
 */
public fun getKillStatisticsUrl(world: String): String = buildTibiaUrl("community", "killstatistics", P("world", world))

/**
 * Get the URL to the events schedule.
 *
 * @param yearMonth The specific year and month to show the schedule for.
 *
 * Note that going past the allowed limits, will take you the current month and year.
 */
public fun getEventsScheduleUrl(yearMonth: YearMonth? = null): String {
    val params =
        if (yearMonth != null) arrayOf(P("calendarmonth", yearMonth.month.value), P("calendaryear", yearMonth.year))
        else emptyArray()
    return buildTibiaUrl("news", "eventcalendar", *params)
}


/**
 * Get the URL to the houses section with the provided parameters.
 */
public fun getHousesSectionUrl(
    world: String,
    town: String,
    type: HouseType? = null,
    status: HouseStatus? = null,
    order: HouseOrder? = null,
): String = buildTibiaUrl(
    "community",
    "houses",
    P("world", world),
    P("town", town),
    P("state", status?.value),
    P("type", type?.value),
    P("order", order?.value),
)

/**
 * Get the URL to a specific auction.
 */
public fun getAuctionUrl(auctionId: Int): String =
    buildTibiaUrl("charactertrade", "currentcharactertrades", P("page", "details"), P("auctionid", auctionId))

/**
 * Get the URL to the character bazaar.
 *
 * @param type Whether to show current auctions or the auction history.
 */
public fun getBazaarUrl(type: BazaarType = BazaarType.CURRENT, filters: BazaarFilters? = null, page: Int = 1): String {
    return buildTibiaUrl("charactertrade", type.subtopic, P("currentpage", page), *filters.getQueryParams())
}

private fun BazaarFilters?.getQueryParams(): Array<P<String, Any?>> {
    return if (this != null) arrayOf(
        P("filter_world", world),
        P(AuctionVocationFilter.queryParam, vocation?.value),
        P("filter_levelrangefrom", minimumLevel),
        P("filter_levelrangeto", maximumLevel),
        P(PvpType.bazaarQueryParam, pvpType?.bazaarFilterValue),
        P(AuctionBattlEyeFilter.queryParam, battlEyeType?.value),
        P(AuctionSkillFilter.queryParam, skill?.value),
        P("filter_skillrangefrom", minimumSkillLevel),
        P("filter_skillrangeto", maximumSkillLevel),
        P(AuctionOrderBy.queryParam, orderBy?.value),
        P(AuctionOrderDirection.queryParam, orderDirection?.value),
        P("searchstring", searchString),
        P(AuctionSearchType.queryParam, searchType?.value),
    ) else emptyArray()
}

/**
 * Get the URL to the CM Post Archive in Tibia.com
 */
public fun getCMPostArchiveUrl(startDate: LocalDate, endDate: LocalDate, page: Int = 1): String {
    return buildTibiaUrl(
        "forum",
        "forum",
        P("action", "cm_post_archive"),
        P("startyear", startDate.year),
        P("startmonth", startDate.monthValue),
        P("startday", startDate.dayOfMonth),
        P("endyear", endDate.year),
        P("endmonth", endDate.monthValue),
        P("endday", endDate.dayOfMonth),
        P("currentpage", page)
    )
}


/**
 * Get the URL of a forum post with a specific [postId] in Tibia.com.
 */
public fun getForumPostUrl(postId: Int): String =
    buildTibiaUrl("forum", P("action", "thread"), P("postid", postId), anchor = "post$postId")

/**
 * Get the URL to the Leaderboard of a specific [world] in Tibia.com.
 */
public fun getLeaderboardUrl(world: String, rotation: Int? = null, page: Int = 1): String =
    buildTibiaUrl("community", "leaderboards", P("world", world), P("rotation", rotation), P("currentpage", page))

/**
 * Get the URL to the Creatures section in Tibia.com.
 */
public fun getCreaturesSectionUrl(): String = buildTibiaUrl("library", "creatures")


/**
 * Get the URL to the Creatures section in Tibia.com.
 */
public fun getBoostableBossesUrl(): String = buildTibiaUrl("library", "boostablebosses")

/**
 * Get the URL to a specific creature in Tibia.com.
 */
public fun getCreatureUrl(identifier: String): String = buildTibiaUrl("library", "creatures", P("race", identifier))
