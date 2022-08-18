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

package com.galarzaa.tibiakt.client

import com.galarzaa.tibiakt.client.exceptions.ForbiddenException
import com.galarzaa.tibiakt.client.exceptions.NetworkException
import com.galarzaa.tibiakt.client.exceptions.SiteMaintenanceException
import com.galarzaa.tibiakt.core.builders.BazaarFiltersBuilder
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
import com.galarzaa.tibiakt.core.models.KillStatistics
import com.galarzaa.tibiakt.core.models.Leaderboards
import com.galarzaa.tibiakt.core.models.bazaar.AjaxPaginator
import com.galarzaa.tibiakt.core.models.bazaar.Auction
import com.galarzaa.tibiakt.core.models.bazaar.BazaarFilters
import com.galarzaa.tibiakt.core.models.bazaar.CharacterBazaar
import com.galarzaa.tibiakt.core.models.bazaar.ItemEntry
import com.galarzaa.tibiakt.core.models.bazaar.MountEntry
import com.galarzaa.tibiakt.core.models.bazaar.OutfitEntry
import com.galarzaa.tibiakt.core.models.character.Character
import com.galarzaa.tibiakt.core.models.creatures.BosstableBosses
import com.galarzaa.tibiakt.core.models.creatures.CreaturesSection
import com.galarzaa.tibiakt.core.models.forums.CMPostArchive
import com.galarzaa.tibiakt.core.models.forums.ForumAnnouncement
import com.galarzaa.tibiakt.core.models.forums.ForumBoard
import com.galarzaa.tibiakt.core.models.forums.ForumSection
import com.galarzaa.tibiakt.core.models.forums.ForumThread
import com.galarzaa.tibiakt.core.models.guild.Guild
import com.galarzaa.tibiakt.core.models.guild.GuildsSection
import com.galarzaa.tibiakt.core.models.highscores.Highscores
import com.galarzaa.tibiakt.core.models.highscores.HighscoresEntry
import com.galarzaa.tibiakt.core.models.house.House
import com.galarzaa.tibiakt.core.models.house.HousesSection
import com.galarzaa.tibiakt.core.models.news.EventsSchedule
import com.galarzaa.tibiakt.core.models.news.News
import com.galarzaa.tibiakt.core.models.news.NewsArchive
import com.galarzaa.tibiakt.core.models.world.World
import com.galarzaa.tibiakt.core.models.world.WorldOverview
import com.galarzaa.tibiakt.core.parsers.AuctionParser
import com.galarzaa.tibiakt.core.parsers.BosstableBossesParser
import com.galarzaa.tibiakt.core.parsers.CMPostArchiveParser
import com.galarzaa.tibiakt.core.parsers.CharacterBazaarParser
import com.galarzaa.tibiakt.core.parsers.CharacterParser
import com.galarzaa.tibiakt.core.parsers.CreaturesSectionParser
import com.galarzaa.tibiakt.core.parsers.EventsScheduleParser
import com.galarzaa.tibiakt.core.parsers.ForumAnnouncementParser
import com.galarzaa.tibiakt.core.parsers.ForumBoardParser
import com.galarzaa.tibiakt.core.parsers.ForumSectionParser
import com.galarzaa.tibiakt.core.parsers.ForumThreadParser
import com.galarzaa.tibiakt.core.parsers.GuildParser
import com.galarzaa.tibiakt.core.parsers.GuildsSectionParser
import com.galarzaa.tibiakt.core.parsers.HighscoresParser
import com.galarzaa.tibiakt.core.parsers.HouseParser
import com.galarzaa.tibiakt.core.parsers.HousesSectionParser
import com.galarzaa.tibiakt.core.parsers.KillStatisticsParser
import com.galarzaa.tibiakt.core.parsers.LeaderboardsParser
import com.galarzaa.tibiakt.core.parsers.NewsArchiveParser
import com.galarzaa.tibiakt.core.parsers.NewsParser
import com.galarzaa.tibiakt.core.parsers.WorldOverviewParser
import com.galarzaa.tibiakt.core.parsers.WorldParser
import com.galarzaa.tibiakt.core.utils.getAuctionUrl
import com.galarzaa.tibiakt.core.utils.getBazaarUrl
import com.galarzaa.tibiakt.core.utils.getBoostableBossesUrl
import com.galarzaa.tibiakt.core.utils.getCMPostArchiveUrl
import com.galarzaa.tibiakt.core.utils.getCharacterUrl
import com.galarzaa.tibiakt.core.utils.getCreaturesSectionUrl
import com.galarzaa.tibiakt.core.utils.getEventsScheduleUrl
import com.galarzaa.tibiakt.core.utils.getForumAnnouncementUrl
import com.galarzaa.tibiakt.core.utils.getForumBoardUrl
import com.galarzaa.tibiakt.core.utils.getForumPostUrl
import com.galarzaa.tibiakt.core.utils.getForumSectionUrl
import com.galarzaa.tibiakt.core.utils.getForumThreadUrl
import com.galarzaa.tibiakt.core.utils.getGuildUrl
import com.galarzaa.tibiakt.core.utils.getHighscoresUrl
import com.galarzaa.tibiakt.core.utils.getHouseUrl
import com.galarzaa.tibiakt.core.utils.getHousesSectionUrl
import com.galarzaa.tibiakt.core.utils.getKillStatisticsUrl
import com.galarzaa.tibiakt.core.utils.getLeaderboardUrl
import com.galarzaa.tibiakt.core.utils.getNewArchiveFormData
import com.galarzaa.tibiakt.core.utils.getNewsArchiveUrl
import com.galarzaa.tibiakt.core.utils.getNewsUrl
import com.galarzaa.tibiakt.core.utils.getWorldGuildsUrl
import com.galarzaa.tibiakt.core.utils.getWorldOverviewUrl
import com.galarzaa.tibiakt.core.utils.getWorldUrl
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.Charsets
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.UserAgent
import io.ktor.client.plugins.compression.ContentEncoding
import io.ktor.client.plugins.expectSuccess
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.headers
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.request
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.Parameters
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.json.Json
import mu.KotlinLogging
import java.time.LocalDate
import java.time.YearMonth
import kotlin.system.measureTimeMillis

/**
 * A coroutine based client to fetch from Tibia.com
 *
 * @param engine The ktor client engine to use, by default CIO is used.
 */
open class TibiaKtClient constructor(
    engine: HttpClientEngine?,
    private val userAgent: String? = null,
) {

    /**
     * Creates an instance of the client, using the default engine (CIO)
     * @param userAgent The value that will be sent in the User-Agent header of every request.
     */
    constructor(userAgent: String? = null) : this(null, userAgent)

    private val client = HttpClient(engine ?: CIO.create()) {
        Charsets {
            register(Charsets.UTF_8)
            register(Charsets.ISO_8859_1)
        }
        followRedirects = false
        install(ContentEncoding) {
            gzip()
            deflate()
        }
        install(UserAgent) {
            agent = userAgent ?: "TibiaKtClient/? ktor/? Kotlin/${KotlinVersion.CURRENT}"
        }
    }

    /**
     * Perform a request using the required headers.
     * @param method The HTTP method to use for the request.
     * @param url The URL to request.
     * @param data The form parameters to add.
     * @param headers Additional headers to add.
     */
    open suspend fun request(
        method: HttpMethod,
        url: String,
        data: List<Pair<String, Any>> = emptyList(),
        headers: List<Pair<String, Any>> = emptyList(),
    ): HttpResponse {
        val response: HttpResponse = try {
            when (method) {
                HttpMethod.Get -> client.get(url) {
                    expectSuccess = true
                    if (headers.isNotEmpty()) {
                        headers { headers.forEach { header(it.first, it.second) } }
                    }
                }

                HttpMethod.Post -> client.submitForm(url, formParameters = Parameters.build {
                    data.forEach { append(it.first, it.second.toString()) }
                }, encodeInQuery = false) {

                }

                else -> throw IllegalArgumentException("Unsupported method $method")
            }
        } catch (re: ResponseException) {
            if (re.response.status in listOf(
                    HttpStatusCode.MovedPermanently,
                    HttpStatusCode.Found,
                    HttpStatusCode.TemporaryRedirect,
                    HttpStatusCode.PermanentRedirect,
                    HttpStatusCode.SeeOther
                )
            ) {
                throw SiteMaintenanceException("Tibia.com is under maintenance.", re)
            }
            if (re.response.status == HttpStatusCode.Forbidden) {
                throw ForbiddenException("403 Forbidden: Might be getting rate-limited", re)
            }
            throw NetworkException("${re.response.status.value} ${re.response.status.description}", re)
        }
        logger.info { "$url | ${method.value.uppercase()} | ${response.status.value} ${response.status.description} | ${response.fetchingTimeMillis}ms" }
        return response
    }

    // region News Section

    /**
     * Fetch the news for a given interval.
     */
    open suspend fun fetchRecentNews(
        startDate: LocalDate,
        endDate: LocalDate,
        categories: Set<NewsCategory>? = null,
        types: Set<NewsType>? = null,
    ): TibiaResponse<NewsArchive> {
        val data = getNewArchiveFormData(startDate, endDate, categories, types)
        val response = this.request(HttpMethod.Post, getNewsArchiveUrl(), data)
        return response.parse { NewsArchiveParser.fromContent(it) }
    }

    /**
     * Fetch the news from today to the last provided days.
     */
    open suspend fun fetchRecentNews(
        days: Int = 30,
        categories: Set<NewsCategory>? = null,
        types: Set<NewsType>? = null,
    ) = fetchRecentNews(LocalDate.now().minusDays(days.toLong()), LocalDate.now(), categories, types)

    /**
     * Fetch a specific news article by its [newsId]
     */
    open suspend fun fetchNews(newsId: Int): TibiaResponse<News?> {
        val response = this.request(HttpMethod.Get, getNewsUrl(newsId))
        return response.parse { NewsParser.fromContent(it, newsId) }
    }

    /**
     * Fetch the events schedule for a specific year and month
     */
    open suspend fun fetchEventsSchedule(yearMonth: YearMonth): TibiaResponse<EventsSchedule> {
        val response = this.request(HttpMethod.Get, getEventsScheduleUrl(yearMonth))
        return response.parse { EventsScheduleParser.fromContent(it) }
    }

    /**
     * Fetch the events schedule for a specific year and month
     */
    open suspend fun fetchEventsSchedule(year: Int, month: Int) = fetchEventsSchedule(YearMonth.of(year, month))

    /**
     * Fetch the events schedule for the current month.
     */
    open suspend fun fetchEventsSchedule() = fetchEventsSchedule(YearMonth.now())

    // endregion

    // region Library Section
    /** Fetch the boosted boss of the day as well as the list of bosstable bosses from Tibia.com */
    open suspend fun fetchBosstableBosses(): TibiaResponse<BosstableBosses> {
        val response = this.request(HttpMethod.Get, getBoostableBossesUrl())
        return response.parse { BosstableBossesParser.fromContent(it) }
    }

    /** Fetch the creatures section, containing the boosted creature */
    open suspend fun fetchCreaturesSection(): TibiaResponse<CreaturesSection> {
        val response = this.request(HttpMethod.Get, getCreaturesSectionUrl())
        return response.parse { CreaturesSectionParser.fromContent(it) }
    }

    // endregion

    // region Community Section

    /**
     * Fetch a character
     * @param name The name of the character.
     */
    open suspend fun fetchCharacter(name: String): TibiaResponse<Character?> {
        val response = this.request(HttpMethod.Get, getCharacterUrl(name))
        return response.parse { CharacterParser.fromContent(it) }
    }

    /**
     * Fetch the world overview, containing the list of worlds.
     */
    open suspend fun fetchWorldOverview(): TibiaResponse<WorldOverview> {
        val response = this.request(HttpMethod.Get, getWorldOverviewUrl())
        return response.parse { WorldOverviewParser.fromContent(it) }
    }

    /** Fetch a world's information. */
    open suspend fun fetchWorld(name: String): TibiaResponse<World?> {
        val response = this.request(HttpMethod.Get, getWorldUrl(name))
        return response.parse { WorldParser.fromContent(it) }
    }

    /** Fetch a guild by its [name]. */
    open suspend fun fetchGuild(name: String): TibiaResponse<Guild?> {
        val response = this.request(HttpMethod.Get, getGuildUrl(name))
        return response.parse { GuildParser.fromContent(it) }
    }

    /** Fetch active and in formation guilds in a [world].
     *
     * If the world does not exist, it will return null.
     */
    open suspend fun fetchWorldGuilds(world: String): TibiaResponse<GuildsSection?> {
        val response = this.request(HttpMethod.Get, getWorldGuildsUrl(world))
        return response.parse { GuildsSectionParser.fromContent(it) }
    }

    /**
     * Fetch the kill statistics for a [world].
     */
    open suspend fun fetchKillStatistics(world: String): TibiaResponse<KillStatistics?> {
        val response = this.request(HttpMethod.Get, getKillStatisticsUrl(world))
        return response.parse { KillStatisticsParser.fromContent(it) }
    }

    /**
     * Fetch a page of the highscores.
     *
     * @param world The world to get highscores from. If null, the highscores of all worlds are returned.
     * @param category The category to fetch.
     * @param vocation The vocation to filter by. By default all vocations will be returned
     * @param page The page number to fetch
     * @param battlEyeType The BattlEye type of the worlds to fetch. Only applies when [world] is null.
     * @param pvpTypes The PvP type of the worlds to fetch. Only applies when [world] is null.
     */
    open suspend fun fetchHighscoresPage(
        world: String?,
        category: HighscoresCategory,
        vocation: HighscoresProfession = HighscoresProfession.ALL,
        page: Int = 1,
        battlEyeType: HighscoresBattlEyeType = HighscoresBattlEyeType.ANY_WORLD,
        pvpTypes: Set<PvpType>? = null,
    ): TibiaResponse<Highscores?> {
        val response =
            this.request(HttpMethod.Get, getHighscoresUrl(world, category, vocation, page, battlEyeType, pvpTypes))
        return response.parse { HighscoresParser.fromContent(it) }
    }

    open suspend fun fetchHigscores(
        world: String?,
        category: HighscoresCategory,
        vocation: HighscoresProfession = HighscoresProfession.ALL,
        battlEyeType: HighscoresBattlEyeType = HighscoresBattlEyeType.ANY_WORLD,
        pvpTypes: Set<PvpType>? = null,
    ): TibiaResponse<Highscores?> {
        val now = Clock.System.now()
        var totalPages = 0
        var currentPage = 1
        val entries = mutableListOf<HighscoresEntry>()
        var fetchingTime = 0.0
        var parsingTime = 0.0
        var baseHighscores: Highscores? = null
        while (totalPages == 0 || currentPage <= totalPages) {
            val response = fetchHighscoresPage(world, category, vocation, currentPage, battlEyeType, pvpTypes)
            if (response.data == null) return response
            entries.addAll(response.data.entries)
            totalPages = response.data.totalPages
            fetchingTime += response.fetchingTime
            parsingTime += response.parsingTime
            if (currentPage == 1) {
                baseHighscores = response.data
            }
            currentPage++
        }
        return TibiaResponse(
            timestamp = now,
            isCached = false,
            cacheAge = 0,
            fetchingTime = fetchingTime,
            parsingTime = parsingTime,
            data = baseHighscores!!.copy(entries = entries)
        )
    }

    /**
     * Fetch the houses section for a [world] and [town].
     */
    open suspend fun fetchHousesSection(
        world: String,
        town: String,
        type: HouseType? = null,
        status: HouseStatus? = null,
        order: HouseOrder? = null,
    ): TibiaResponse<HousesSection?> {
        val response = this.request(HttpMethod.Get, getHousesSectionUrl(world, town, type, status, order))
        return response.parse { HousesSectionParser.fromContent(it) }
    }

    /** Fetch a house by its [houseId] in a specific world. */
    open suspend fun fetchHouse(
        houseId: Int,
        world: String,
    ): TibiaResponse<House?> {
        val response = this.request(HttpMethod.Get, getHouseUrl(world, houseId))
        return response.parse { HouseParser.fromContent(it) }
    }

    /**
     * Fetches the Tibia Drome leaderboards for a [world].
     *
     * If the world does not exist, the leaderboards will be null.
     * @param rotation The rotation number to see. Tibia.com only allows viewing the current and last rotations. Any other value takes you to the current leaderboard.
     */
    open suspend fun fetchLeaderboards(
        world: String,
        rotation: Int? = null,
        page: Int = 1,
    ): TibiaResponse<Leaderboards?> {
        val response = this.request(HttpMethod.Get, getLeaderboardUrl(world, rotation, page))
        return response.parse { LeaderboardsParser.fromContent(it) }
    }

    // endregion

    // region Forum Section

    /**
     * Fetches a forum section by its internal [sectionId].
     */
    open suspend fun fetchForumSection(sectionId: Int): TibiaResponse<ForumSection?> {
        val response = this.request(HttpMethod.Get, getForumSectionUrl(sectionId))
        return response.parse { ForumSectionParser.fromContent(it) }
    }

    /** Fetches a specific forum section. */
    open suspend fun fetchForumSection(section: AvailableForumSection): TibiaResponse<ForumSection?> {
        val response = this.request(HttpMethod.Get, getForumSectionUrl(section))
        return response.parse { ForumSectionParser.fromContent(it) }
    }

    /** Fetches a board from the Tibia.com forum.
     *
     * @param threadAge The maximum age of displayed threads. A value of -1 means no limit. Null means the server limit will be used.
     */
    open suspend fun fetchForumBoard(boardId: Int, page: Int = 1, threadAge: Int? = null): TibiaResponse<ForumBoard?> {
        val response = this.request(HttpMethod.Get, getForumBoardUrl(boardId, page, threadAge))
        return response.parse { ForumBoardParser.fromContent(it) }
    }

    /** Fetches a thread from the Tibia.com forum. */
    open suspend fun fetchForumAnnouncement(announcementId: Int): TibiaResponse<ForumAnnouncement?> {
        val response = this.request(HttpMethod.Get, getForumAnnouncementUrl(announcementId))
        return response.parse { ForumAnnouncementParser.fromContent(it, announcementId) }
    }

    /** Fetches a forum thread from Tibia.com */
    open suspend fun fetchForumThread(threadId: Int, page: Int = 1): TibiaResponse<ForumThread?> {
        val response = this.request(HttpMethod.Get, getForumThreadUrl(threadId, page))
        return response.parse { ForumThreadParser.fromContent(it) }
    }

    /** Fetches a forum thread containing the specified post.
     *
     * The thread will be fetched on the page containing the [ForumThread.anchoredPost]
     */
    open suspend fun fetchForumPost(postId: Int): TibiaResponse<ForumThread?> {
        val response = this.request(HttpMethod.Get, getForumPostUrl(postId))
        return response.parse {
            ForumThreadParser.fromContent(it)?.let { thread ->
                thread.copy(anchoredPost = thread.entries.first { post -> post.postId == postId })
            }
        }
    }


    /** Fetch CM posts between two dates. */
    open suspend fun fetchCMPostArchive(
        startDate: LocalDate,
        endDate: LocalDate,
        page: Int = 0,
    ): TibiaResponse<CMPostArchive> {
        val response = this.request(HttpMethod.Get, getCMPostArchiveUrl(startDate, endDate, page))
        return response.parse { CMPostArchiveParser.fromContent(it) }
    }

    /** Fetch CM posts from today to the last specified [days]. */
    open suspend fun fetchCMPostArchive(days: Int, page: Int = 0) =
        fetchCMPostArchive(LocalDate.now().minusDays(days.toLong()), LocalDate.now(), page)

    // endregion

    // region Char Bazaar Section
    /**
     * Fetch the character bazaar
     *
     * @param type Whether to show current auctions or the auction history.
     * @param filters The filtering parameters to use.
     * @param page The page to display.
     */
    open suspend fun fetchBazaar(
        type: BazaarType = BazaarType.CURRENT,
        filters: BazaarFilters? = null,
        page: Int = 1,
    ): TibiaResponse<CharacterBazaar> {
        val response = this.request(HttpMethod.Get, getBazaarUrl(type, filters, page))
        return response.parse { CharacterBazaarParser.fromContent(it) }
    }

    suspend fun fetchBazaar(
        type: BazaarType = BazaarType.CURRENT,
        page: Int = 1,
        filterBuilder: (BazaarFiltersBuilder.() -> Unit)? = null,
    ): TibiaResponse<CharacterBazaar> =
        fetchBazaar(type, filterBuilder?.let { BazaarFiltersBuilder().apply(it).build() }, page)

    /**
     * Fetch an auction from Tibia.com
     * @param auctionId The ID of the auction to fetch.
     * @param skipDetails Whether to only fetch the auction's header and skip details.
     * @param fetchItems Whether to fetch items from further pages if necessary. Cannot be done if [skipDetails] is true.
     * @param fetchOutfits Whether to fetch outfits from further pages if necessary. Cannot be done if [skipDetails] is true.
     * @param fetchMounts Whether to fetch mounts from further pages if necessary. Cannot be done if [skipDetails] is true.
     */
    open suspend fun fetchAuction(
        auctionId: Int,
        skipDetails: Boolean = false,
        fetchItems: Boolean = false,
        fetchOutfits: Boolean = false,
        fetchMounts: Boolean = false,
    ): TibiaResponse<Auction?> {
        val response = this.request(HttpMethod.Get, getAuctionUrl(auctionId))
        val tibiaResponse = response.parse { AuctionParser.fromContent(it, auctionId, !skipDetails) }
        return if (tibiaResponse.data?.details != null && (fetchItems || fetchMounts || fetchOutfits)) {
            fetchAuctionAdditionalPages(tibiaResponse, fetchItems, fetchMounts, fetchOutfits)
        } else {
            tibiaResponse
        }
    }

    private suspend fun fetchAuctionAdditionalPages(
        tibiaResponse: TibiaResponse<Auction?>,
        fetchItems: Boolean = false,
        fetchOutfits: Boolean = false,
        fetchMounts: Boolean = false,
    ): TibiaResponse<Auction?> {
        if (tibiaResponse.data?.details == null) return tibiaResponse
        val accumulator = Timing(tibiaResponse.fetchingTime, tibiaResponse.parsingTime)
        var itemEntries: List<ItemEntry>? = null
        var storeItemEntries: List<ItemEntry>? = null
        var outfitEntries: List<OutfitEntry>? = null
        var storeOutfitEntries: List<OutfitEntry>? = null
        var mountEntries: List<MountEntry>? = null
        var storeMountEntries: List<MountEntry>? = null
        if (fetchItems) {
            itemEntries = fetchAllPages(
                tibiaResponse.data.auctionId, AuctionPagesType.ITEMS, tibiaResponse.data.details!!.items
            ).accumulateTime(accumulator)
            storeItemEntries = fetchAllPages(
                tibiaResponse.data.auctionId, AuctionPagesType.ITEMS_STORE, tibiaResponse.data.details!!.storeItems
            ).accumulateTime(accumulator)
        }
        if (fetchMounts) {
            mountEntries = fetchAllPages(
                tibiaResponse.data.auctionId, AuctionPagesType.MOUNTS, tibiaResponse.data.details!!.mounts
            ).accumulateTime(accumulator)
            storeMountEntries = fetchAllPages(
                tibiaResponse.data.auctionId, AuctionPagesType.MOUNTS_STORE, tibiaResponse.data.details!!.storeMounts
            ).accumulateTime(accumulator)
        }
        if (fetchOutfits) {
            outfitEntries = fetchAllPages(
                tibiaResponse.data.auctionId, AuctionPagesType.OUTFITS, tibiaResponse.data.details!!.outfits
            ).accumulateTime(accumulator)
            storeOutfitEntries = fetchAllPages(
                tibiaResponse.data.auctionId, AuctionPagesType.OUTFITS_STORE, tibiaResponse.data.details!!.storeOutfits
            ).accumulateTime(accumulator)
        }
        return tibiaResponse.copy(
            fetchingTime = accumulator.fetching, parsingTime = accumulator.parsing, data = tibiaResponse.data.copy(
                details = tibiaResponse.data.details!!.copy(
                    items = tibiaResponse.data.details!!.items.copy(
                        entries = itemEntries ?: tibiaResponse.data.details!!.items.entries,
                        fullyFetched = fetchItems,
                    ),
                    storeItems = tibiaResponse.data.details!!.items.copy(
                        entries = storeItemEntries ?: tibiaResponse.data.details!!.items.entries,
                        fullyFetched = fetchItems,
                    ),
                    mounts = tibiaResponse.data.details!!.mounts.copy(
                        entries = mountEntries ?: tibiaResponse.data.details!!.mounts.entries,
                        fullyFetched = fetchMounts,
                    ),
                    storeMounts = tibiaResponse.data.details!!.storeMounts.copy(
                        entries = storeMountEntries ?: tibiaResponse.data.details!!.storeMounts.entries,
                        fullyFetched = fetchMounts
                    ),
                    outfits = tibiaResponse.data.details!!.outfits.copy(
                        entries = outfitEntries ?: tibiaResponse.data.details!!.outfits.entries,
                        fullyFetched = fetchOutfits,
                    ),
                    storeOutfits = tibiaResponse.data.details!!.storeOutfits.copy(
                        entries = storeOutfitEntries ?: tibiaResponse.data.details!!.storeOutfits.entries,
                        fullyFetched = fetchOutfits,
                    ),
                )
            )
        )
    }

    // endregion

    private val HttpResponse.fetchingTime get() = (responseTime.timestamp - requestTime.timestamp) / 1000.0
    private val HttpResponse.fetchingTimeMillis get() = (fetchingTime * 1000).toInt()

    /** Convert to a [TibiaResponse]. */
    private fun <T> HttpResponse.toTibiaResponse(parsingTime: Double, data: T): TibiaResponse<T> = TibiaResponse(
        timestamp = Instant.fromEpochMilliseconds(responseTime.timestamp),
        isCached = headers["CF-Cache-Status"] == "HIT",
        cacheAge = headers["Age"]?.toInt() ?: 0,
        fetchingTime = fetchingTime,
        parsingTime = parsingTime,
        data = data
    )

    /**
     * Parse the [HttpResponse] into a TibiaResponse.
     */
    private suspend fun <T> HttpResponse.parse(parser: (content: String) -> T): TibiaResponse<T> {
        val data: T
        val parsingTime = measureTimeMillis {
            val stringBody: String = body()
            data = parser(stringBody)
        } / 1000.0
        logger.info { "${this.request.url} | PARSE | ${(parsingTime * 1000).toInt()}ms" }
        return toTibiaResponse(parsingTime, data)
    }

    /**
     * Get the URL to the endpoint to get page items for auctions.
     */
    private fun getAuctionAjaxPaginationUrl(auctionId: Int, type: AuctionPagesType, page: Int): String {
        return "https://www.tibia.com/websiteservices/handle_charactertrades.php?auctionid=$auctionId&type=${type.typeId}&currentpage=$page"
    }

    /**
     * Fetch a single page from the auction pagination endpoint.
     */
    private suspend fun fetchAjaxPage(auctionId: Int, type: AuctionPagesType, page: Int): TimedResult<AjaxResponse> {
        val response = this.request(
            HttpMethod.Get, getAuctionAjaxPaginationUrl(auctionId, type, page),
            headers = listOf(Pair("x-requested-with", "XMLHttpRequest")),
        )
        return TimedResult(response.fetchingTime, Json.decodeFromString(AjaxResponse.serializer(), response.body()))
    }

    /**
     * Fetch all the pages, parse and collect the entries.
     * @param auctionId The id of the auction.
     * @param type The type of items.
     * @param paginator The paginator class that holds the collection
     */
    private suspend inline fun <reified E, T : AjaxPaginator<E>> fetchAllPages(
        auctionId: Int,
        type: AuctionPagesType,
        paginator: T,
    ): Pair<Timing, List<E>> {
        var currentPage = paginator.currentPage + 1
        val entries: MutableList<E> = paginator.entries.toMutableList()
        val timing = Timing()
        while (currentPage <= paginator.totalPages) {
            val (fetchingTime, ajaxResponse) = fetchAjaxPage(auctionId, type, currentPage)
            timing.fetching += fetchingTime
            val parsingTime = measureTimeMillis {
                entries.addAll(AuctionParser.parsePageItems(ajaxResponse.ajaxObjects.first().data))
            } / 1000.0
            timing.parsing += parsingTime
            logger.info {
                "${
                    getAuctionAjaxPaginationUrl(auctionId, type, currentPage)
                } | PARSE | ${(parsingTime * 1000).toInt()}ms"
            }
            currentPage++
        }
        return Pair(timing, entries)
    }

    /**
     * A data class to hold fetching and parsing times.
     */
    private data class Timing(var fetching: Double = 0.0, var parsing: Double = 0.0)

    /**
     * Extract the results of [fetchAllPages] and sum the timings into the [accumulator]
     */
    private fun <T> Pair<Timing, List<T>>.accumulateTime(accumulator: Timing): List<T> {
        accumulator.fetching += first.fetching
        accumulator.parsing += first.parsing
        return second
    }

    private data class TimedResult<T>(val time: Double, val result: T)

    internal companion object {
        internal val logger = KotlinLogging.logger { }
    }
}

