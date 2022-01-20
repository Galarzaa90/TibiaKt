package com.galarzaa.tibiakt.client

import com.galarzaa.tibiakt.client.models.AjaxResponse
import com.galarzaa.tibiakt.client.models.AuctionPagesType
import com.galarzaa.tibiakt.client.models.ForbiddenException
import com.galarzaa.tibiakt.client.models.NetworkException
import com.galarzaa.tibiakt.client.models.TibiaResponse
import com.galarzaa.tibiakt.core.enums.BazaarType
import com.galarzaa.tibiakt.core.enums.HighscoresBattlEyeType
import com.galarzaa.tibiakt.core.enums.HighscoresCategory
import com.galarzaa.tibiakt.core.enums.HighscoresProfession
import com.galarzaa.tibiakt.core.enums.HighscoresPvpType
import com.galarzaa.tibiakt.core.enums.HouseOrder
import com.galarzaa.tibiakt.core.enums.HouseStatus
import com.galarzaa.tibiakt.core.enums.HouseType
import com.galarzaa.tibiakt.core.enums.NewsCategory
import com.galarzaa.tibiakt.core.enums.NewsType
import com.galarzaa.tibiakt.core.models.KillStatistics
import com.galarzaa.tibiakt.core.models.bazaar.AjaxPaginator
import com.galarzaa.tibiakt.core.models.bazaar.Auction
import com.galarzaa.tibiakt.core.models.bazaar.BazaarFilters
import com.galarzaa.tibiakt.core.models.bazaar.CharacterBazaar
import com.galarzaa.tibiakt.core.models.bazaar.DisplayItem
import com.galarzaa.tibiakt.core.models.bazaar.DisplayMount
import com.galarzaa.tibiakt.core.models.bazaar.DisplayOutfit
import com.galarzaa.tibiakt.core.models.character.Character
import com.galarzaa.tibiakt.core.models.forums.CMPostArchive
import com.galarzaa.tibiakt.core.models.guild.Guild
import com.galarzaa.tibiakt.core.models.guild.GuildsSection
import com.galarzaa.tibiakt.core.models.highscores.Highscores
import com.galarzaa.tibiakt.core.models.house.House
import com.galarzaa.tibiakt.core.models.house.HousesSection
import com.galarzaa.tibiakt.core.models.news.EventsSchedule
import com.galarzaa.tibiakt.core.models.news.News
import com.galarzaa.tibiakt.core.models.news.NewsArchive
import com.galarzaa.tibiakt.core.models.world.World
import com.galarzaa.tibiakt.core.models.world.WorldOverview
import com.galarzaa.tibiakt.core.parsers.AuctionParser
import com.galarzaa.tibiakt.core.parsers.CMPostArchiveParser
import com.galarzaa.tibiakt.core.parsers.CharacterBazaarParser
import com.galarzaa.tibiakt.core.parsers.CharacterParser
import com.galarzaa.tibiakt.core.parsers.EventsScheduleParser
import com.galarzaa.tibiakt.core.parsers.GuildParser
import com.galarzaa.tibiakt.core.parsers.GuildsSectionParser
import com.galarzaa.tibiakt.core.parsers.HighscoresParser
import com.galarzaa.tibiakt.core.parsers.HouseParser
import com.galarzaa.tibiakt.core.parsers.HousesSectionParser
import com.galarzaa.tibiakt.core.parsers.KillStatisticsParser
import com.galarzaa.tibiakt.core.parsers.NewsArchiveParser
import com.galarzaa.tibiakt.core.parsers.NewsParser
import com.galarzaa.tibiakt.core.parsers.WorldOverviewParser
import com.galarzaa.tibiakt.core.parsers.WorldParser
import com.galarzaa.tibiakt.core.utils.getAuctionUrl
import com.galarzaa.tibiakt.core.utils.getBazaarUrl
import com.galarzaa.tibiakt.core.utils.getCMPostArchiveUrl
import com.galarzaa.tibiakt.core.utils.getCharacterUrl
import com.galarzaa.tibiakt.core.utils.getEventsScheduleUrl
import com.galarzaa.tibiakt.core.utils.getGuildUrl
import com.galarzaa.tibiakt.core.utils.getHighscoresUrl
import com.galarzaa.tibiakt.core.utils.getHouseUrl
import com.galarzaa.tibiakt.core.utils.getHousesSectionUrl
import com.galarzaa.tibiakt.core.utils.getKillStatisticsUrl
import com.galarzaa.tibiakt.core.utils.getNewArchiveFormData
import com.galarzaa.tibiakt.core.utils.getNewsArchiveUrl
import com.galarzaa.tibiakt.core.utils.getNewsUrl
import com.galarzaa.tibiakt.core.utils.getWorldGuildsUrl
import com.galarzaa.tibiakt.core.utils.getWorldOverviewUrl
import com.galarzaa.tibiakt.core.utils.getWorldUrl
import io.ktor.client.HttpClient
import io.ktor.client.call.receive
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.cio.CIO
import io.ktor.client.features.ResponseException
import io.ktor.client.features.UserAgent
import io.ktor.client.features.compression.ContentEncoding
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.headers
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.request
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.Parameters
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import mu.KotlinLogging
import java.time.Instant
import java.time.LocalDate
import java.time.YearMonth
import kotlin.system.measureTimeMillis

/**
 * A coroutine based client to fetch from Tibia.com
 */
open class TibiaKtClient internal constructor(engine: HttpClientEngine) {
    constructor() : this(CIO.create())

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
                    if (headers.isNotEmpty()) {
                        headers { headers.forEach { header(it.first, it.second) } }
                    }
                }
                HttpMethod.Post -> client.submitForm(
                    url,
                    formParameters = Parameters.build {
                        data.forEach { append(it.first, it.second.toString()) }
                    },
                    encodeInQuery = false
                )
                else -> throw IllegalArgumentException("Unsupported method $method")
            }
        } catch (re: ResponseException) {
            if (re.response.status == HttpStatusCode.Forbidden) {
                throw ForbiddenException("403 Forbidden: Might be getting rate-limited", re)
            }
            throw NetworkException("${re.response.status.value} ${re.response.status.description}", re)
        }
        logger.info { "$url | ${method.value.uppercase()} | ${response.status.value} ${response.status.description} | ${response.fetchingTimeMillis}ms" }
        return response
    }

    private val client = HttpClient(engine) {
        ContentEncoding {
            gzip()
            deflate()
        }
        install(UserAgent) {
            agent = "TibiaKtClient"
        }
    }

    // region News Section

    /**
     * Fetch the news for a given interval.
     */
    suspend fun fetchRecentNews(
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
    suspend fun fetchRecentNews(
        days: Int = 30,
        categories: Set<NewsCategory>? = null,
        types: Set<NewsType>? = null,
    ) = fetchRecentNews(LocalDate.now().minusDays(days.toLong()), LocalDate.now(), categories, types)

    /**
     * Fetch a specific news article by its [newsId]
     */
    suspend fun fetchNews(newsId: Int): TibiaResponse<News?> {
        val response = this.request(HttpMethod.Get, getNewsUrl(newsId))
        return response.parse { NewsParser.fromContent(it, newsId) }
    }

    /**
     * Fetch the events schedule for a specific year and month
     */
    suspend fun fetchEventsSchedule(yearMonth: YearMonth): TibiaResponse<EventsSchedule> {
        val response =
            this.request(HttpMethod.Get, getEventsScheduleUrl(yearMonth))
        return response.parse { EventsScheduleParser.fromContent(it) }
    }

    /**
     * Fetch the events schedule for a specific year and month
     */
    suspend fun fetchEventsSchedule(year: Int, month: Int) = fetchEventsSchedule(YearMonth.of(year, month))

    /**
     * Fetch the events schedule for the current month.
     */
    suspend fun fetchEventsSchedule() = fetchEventsSchedule(YearMonth.now())

    // endregion

    // region Community Section

    /**
     * Fetch a character
     * @param name The name of the character.
     */
    suspend fun fetchCharacter(name: String): TibiaResponse<Character?> {
        val response = this.request(HttpMethod.Get, getCharacterUrl(name))
        return response.parse { CharacterParser.fromContent(it) }
    }

    /**
     * Fetch the world overview, containing the list of worlds.
     */
    suspend fun fetchWorldOverview(): TibiaResponse<WorldOverview> {
        val response = this.request(HttpMethod.Get, getWorldOverviewUrl())
        return response.parse { WorldOverviewParser.fromContent(it) }
    }

    /**
     * Fetch a world's information.
     */
    suspend fun fetchWorld(name: String): TibiaResponse<World?> {
        val response = this.request(HttpMethod.Get, getWorldUrl(name))
        return response.parse { WorldParser.fromContent(it) }
    }

    suspend fun fetchGuild(name: String): TibiaResponse<Guild?> {
        val response = this.request(HttpMethod.Get, getGuildUrl(name))
        return response.parse { GuildParser.fromContent(it) }
    }

    suspend fun fetchWorldGuilds(name: String): TibiaResponse<GuildsSection?> {
        val response = this.request(HttpMethod.Get, getWorldGuildsUrl(name))
        return response.parse { GuildsSectionParser.fromContent(it) }
    }

    /**
     * Fetch the kill statistics for a [world]
     */
    suspend fun fetchKillStatistics(world: String): TibiaResponse<KillStatistics> {
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
    suspend fun fetchHighscoresPage(
        world: String?,
        category: HighscoresCategory,
        vocation: HighscoresProfession = HighscoresProfession.ALL,
        page: Int = 1,
        battlEyeType: HighscoresBattlEyeType = HighscoresBattlEyeType.ANY_WORLD,
        pvpTypes: Set<HighscoresPvpType>? = null,
    ): TibiaResponse<Highscores> {
        val response =
            this.request(HttpMethod.Get, getHighscoresUrl(world, category, vocation, page, battlEyeType, pvpTypes))
        return response.parse { HighscoresParser.fromContent(it) }
    }

    /**
     * Fetch the houses section for a [world] and [town].
     */
    suspend fun fetchHousesSection(
        world: String,
        town: String,
        type: HouseType? = null,
        status: HouseStatus? = null,
        order: HouseOrder? = null,
    ): TibiaResponse<HousesSection?> {
        val response =
            this.request(HttpMethod.Get, getHousesSectionUrl(world, town, type, status, order))
        return response.parse { HousesSectionParser.fromContent(it) }
    }

    /** Fetch a house by its [houseId] in a specific world. */
    suspend fun fetchHouse(
        houseId: Int,
        world: String,
    ): TibiaResponse<House?> {
        val response = this.request(HttpMethod.Get, getHouseUrl(world, houseId))
        return response.parse { HouseParser.fromContent(it) }
    }

    // endregion

    // region Forum Section

    /** Fetch CM posts between two dates. */
    suspend fun fetchCMPostArchive(
        startDate: LocalDate,
        endDate: LocalDate,
        page: Int = 0,
    ): TibiaResponse<CMPostArchive> {
        val response = this.request(HttpMethod.Get, getCMPostArchiveUrl(startDate, endDate, page))
        return response.parse { CMPostArchiveParser.fromContent(it) }
    }

    /** Fetch CM posts from today to the last specified [days]. */
    suspend fun fetchCMPostArchive(days: Int, page: Int = 0) =
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
    suspend fun fetchBazaar(
        type: BazaarType = BazaarType.CURRENT,
        filters: BazaarFilters? = null,
        page: Int = 1,
    ): TibiaResponse<CharacterBazaar> {
        val response = this.request(HttpMethod.Get, getBazaarUrl(type, filters, page))
        return response.parse { CharacterBazaarParser.fromContent(it) }
    }

    /**
     * Fetch an auction from Tibia.com
     * @param auctionId The ID of the auction to fetch.
     * @param skipDetails Whether to only fetch the auction's header and skip details.
     * @param fetchItems Whether to fetch items from further pages if necessary. Cannot be done if [skipDetails] is true.
     * @param fetchOutfits Whether to fetch outfits from further pages if necessary. Cannot be done if [skipDetails] is true.
     * @param fetchMounts Whether to fetch mounts from further pages if necessary. Cannot be done if [skipDetails] is true.
     */
    suspend fun fetchAuction(
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
        if (tibiaResponse.data?.details == null)
            return tibiaResponse
        val accumulator = Timing(tibiaResponse.fetchingTime, tibiaResponse.parsingTime)
        var itemEntries: List<DisplayItem>? = null
        var storeItemEntries: List<DisplayItem>? = null
        var outfitEntries: List<DisplayOutfit>? = null
        var storeOutfitEntries: List<DisplayOutfit>? = null
        var mountEntries: List<DisplayMount>? = null
        var storeMountEntries: List<DisplayMount>? = null
        if (fetchItems) {
            itemEntries = fetchAllPages(tibiaResponse.data.auctionId,
                AuctionPagesType.ITEMS,
                tibiaResponse.data.details!!.items).accumulateTime(accumulator)
            storeItemEntries =
                fetchAllPages(tibiaResponse.data.auctionId,
                    AuctionPagesType.ITEMS_STORE,
                    tibiaResponse.data.details!!.storeItems).accumulateTime(accumulator)
        }
        if (fetchMounts) {
            mountEntries = fetchAllPages(tibiaResponse.data.auctionId,
                AuctionPagesType.MOUNTS,
                tibiaResponse.data.details!!.mounts).accumulateTime(accumulator)
            storeMountEntries =
                fetchAllPages(tibiaResponse.data.auctionId,
                    AuctionPagesType.MOUNTS_STORE,
                    tibiaResponse.data.details!!.storeMounts).accumulateTime(accumulator)
        }
        if (fetchOutfits) {
            outfitEntries = fetchAllPages(tibiaResponse.data.auctionId,
                AuctionPagesType.OUTFITS,
                tibiaResponse.data.details!!.outfits).accumulateTime(accumulator)
            storeOutfitEntries =
                fetchAllPages(tibiaResponse.data.auctionId,
                    AuctionPagesType.OUTFITS_STORE,
                    tibiaResponse.data.details!!.storeOutfits).accumulateTime(accumulator)
        }
        return tibiaResponse.copy(
            fetchingTime = accumulator.fetching,
            parsingTime = accumulator.parsing,
            data = tibiaResponse.data.copy(
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

    private val HttpResponse.fetchingTime get() = (responseTime.timestamp - requestTime.timestamp) / 1000f
    private val HttpResponse.fetchingTimeMillis get() = (fetchingTime * 1000).toInt()

    /** Convert to a [TibiaResponse]. */
    private fun <T> HttpResponse.toTibiaResponse(parsingTime: Float, data: T): TibiaResponse<T> = TibiaResponse(
        timestamp = Instant.ofEpochMilli(responseTime.timestamp),
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
            val stringBody: String = receive()
            data = parser(stringBody)
        } / 1000f
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
    @OptIn(ExperimentalSerializationApi::class)
    private suspend fun fetchAjaxPage(auctionId: Int, type: AuctionPagesType, page: Int): TimedResult<AjaxResponse> {
        val response = this.request(
            HttpMethod.Get, getAuctionAjaxPaginationUrl(auctionId, type, page),
            headers = listOf(Pair("x-requested-with", "XMLHttpRequest")),
        )
        return TimedResult(response.fetchingTime, Json.decodeFromString(response.receive()))
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
            } / 1000f
            timing.parsing += parsingTime
            logger.info {
                "${
                    getAuctionAjaxPaginationUrl(auctionId,
                        type,
                        currentPage)
                } | PARSE | ${(parsingTime * 1000).toInt()}ms"
            }
            currentPage++
        }
        return Pair(timing, entries)
    }

    /**
     * A data class to hold fetching and parsing times.
     */
    private data class Timing(var fetching: Float = 0f, var parsing: Float = 0f)

    /**
     * Extract the results of [fetchAllPages] and sum the timings into the [accumulator]
     */
    private fun <T> Pair<Timing, List<T>>.accumulateTime(accumulator: Timing): List<T> {
        accumulator.fetching += first.fetching
        accumulator.parsing += first.parsing
        return second
    }

    private data class TimedResult<T>(val time: Float, val result: T)

    internal companion object {
        internal val logger = KotlinLogging.logger { }
    }
}

