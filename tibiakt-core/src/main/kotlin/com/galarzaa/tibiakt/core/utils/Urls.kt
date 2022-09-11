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
    }${anchor?.let { "#$it" }.orEmpty()}"
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
    newParams.add(0, Pair("subtopic", subtopic))
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
public fun getCharacterUrl(name: String): String = buildTibiaUrl("community", "characters", Pair("name", name))

/**
 * Get the URL to the list of guilds for a specific [world].
 */
public fun getWorldGuildsUrl(world: String): String = buildTibiaUrl("community", "guilds", Pair("world", world))

/**
 * Get the URL to a specific guild.
 */
public fun getGuildUrl(name: String): String =
    buildTibiaUrl("community", "guilds", Pair("GuildName", name), Pair("page", "view"))

/**
 * Get the URL to a house on a specific [world].
 */
public fun getHouseUrl(world: String, houseId: Int): String =
    buildTibiaUrl("community", "houses", Pair("page", "view"), Pair("world", world), Pair("houseid", houseId))

/**
 * Get the URL to the World Overview section
 */
public fun getWorldOverviewUrl(): String = buildTibiaUrl("community", "worlds")

/**
 * Get the URL to a specific world.
 */
public fun getWorldUrl(name: String): String = buildTibiaUrl("community", "worlds", Pair("world", name))

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
public fun getNewsUrl(newsId: Int): String = buildTibiaUrl("news", "newsarchive", Pair("id", newsId))


/**
 * Get the URL to a specific forum section
 */
public fun getForumSectionUrl(sectionId: Int): String =
    buildTibiaUrl("forum", Pair("action", "main"), Pair("sectionid", sectionId))


/**
 * Get the URL to a specific forum section
 */
public fun getForumSectionUrl(section: AvailableForumSection): String =
    buildTibiaUrl("forum", Pair("subtopoic", section.subtopic))

/**
 * Get the URL to a specific forum section by its name.
 */
public fun getForumSectionUrl(sectionName: String): String = buildTibiaUrl("forum", sectionName)

public fun getForumBoardUrl(boardId: Int, page: Int = 1, threadAge: Int? = null): String = buildTibiaUrl(
    "forum", Pair("action", "board"), Pair("boardid", boardId), Pair("pagenumber", page), Pair("threadage", threadAge)
)


public fun getForumAnnouncementUrl(announcementId: Int): String =
    buildTibiaUrl("forum", Pair("action", "announcement"), Pair("announcementid", announcementId))

/**
 * Get the URL to a specific thread in the forums.
 */
public fun getForumThreadUrl(threadId: Int, page: Int = 1): String =
    buildTibiaUrl("forum", Pair("action", "thread"), Pair("threadid", threadId), Pair("pagenumber", page))

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
): String = buildTibiaUrl(
    "community",
    Pair("subtopic", "highscores"),
    Pair("world", world),
    Pair("profession", vocations.value),
    Pair("currentpage", page),
    Pair("category", category.value),
    Pair("beprotection", battleEye.value),
    *worldTypes.orEmpty().map { Pair("${PvpType.highscoresQueryParam}[]", it.highscoresFilterValue) }.toTypedArray()
)

/**
 * Get the URL to the kill statistics of a specific world.
 */
public fun getKillStatisticsUrl(world: String): String =
    buildTibiaUrl("community", "killstatistics", Pair("world", world))

/**
 * Get the URL to the events schedule.
 *
 * @param yearMonth The specific year and month to show the schedule for.
 *
 * Note that going past the allowed limits, will take you the current month and year.
 */
public fun getEventsScheduleUrl(yearMonth: YearMonth? = null): String {
    return buildTibiaUrl(
        "news",
        "eventcalendar",
        *yearMonth?.let { arrayOf("calendarmonth" to it.month.value, "calendaryear" to it.year) }.orEmpty()
    )
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
    Pair("world", world),
    Pair("town", town),
    Pair("state", status?.value),
    Pair("type", type?.value),
    Pair("order", order?.value),
)

/**
 * Get the URL to a specific auction.
 */
public fun getAuctionUrl(auctionId: Int): String =
    buildTibiaUrl("charactertrade", "currentcharactertrades", Pair("page", "details"), Pair("auctionid", auctionId))


/**
 * Get the URL to the character bazaar.
 *
 * @param type Whether to show current auctions or the auction history.
 */
public fun getBazaarUrl(type: BazaarType = BazaarType.CURRENT, filters: BazaarFilters? = null, page: Int = 1): String {
    return buildTibiaUrl("charactertrade", type.subtopic, "currentpage" to page, *filters.getQueryParams())
}

private fun BazaarFilters?.getQueryParams(): Array<Pair<String, Any?>> {
    return if (this != null) arrayOf(
        Pair("filter_world", world),
        Pair(AuctionVocationFilter.queryParam, vocation?.value),
        Pair("filter_levelrangefrom", minimumLevel),
        Pair("filter_levelrangeto", maximumLevel),
        Pair(PvpType.bazaarQueryParam, pvpType?.bazaarFilterValue),
        Pair(AuctionBattlEyeFilter.queryParam, battlEyeType?.value),
        Pair(AuctionSkillFilter.queryParam, skill?.value),
        Pair("filter_skillrangefrom", minimumSkillLevel),
        Pair("filter_skillrangeto", maximumSkillLevel),
        Pair(AuctionOrderBy.queryParam, orderBy?.value),
        Pair(AuctionOrderDirection.queryParam, orderDirection?.value),
        Pair("searchstring", searchString),
        Pair(AuctionSearchType.queryParam, searchType?.value),
    ) else emptyArray()
}

/**
 * Get the URL to the CM Post Archive in Tibia.com
 */
public fun getCMPostArchiveUrl(startDate: LocalDate, endDate: LocalDate, page: Int = 1): String {
    return buildTibiaUrl(
        "forum",
        "forum",
        Pair("action", "cm_post_archive"),
        Pair("startyear", startDate.year),
        Pair("startmonth", startDate.monthValue),
        Pair("startday", startDate.dayOfMonth),
        Pair("endyear", endDate.year),
        Pair("endmonth", endDate.monthValue),
        Pair("endday", endDate.dayOfMonth),
        Pair("currentpage", page)
    )
}


/**
 * Get the URL of a forum post with a specific [postId] in Tibia.com.
 */
public fun getForumPostUrl(postId: Int): String =
    buildTibiaUrl("forum", Pair("action", "thread"), Pair("postid", postId), anchor = "post$postId")

/**
 * Get the URL to the Leaderboard of a specific [world] in Tibia.com.
 */
public fun getLeaderboardUrl(world: String, rotation: Int? = null, page: Int = 1): String = buildTibiaUrl(
    "community", "leaderboards", "world" to world, "rotation" to rotation, "currentpage" to page
)

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
public fun getCreatureUrl(identifier: String): String = buildTibiaUrl("library", "creatures", Pair("race", identifier))
