package com.galarzaa.tibiakt.client

import com.galarzaa.tibiakt.client.models.AjaxResponse
import com.galarzaa.tibiakt.client.models.TibiaResponse
import com.galarzaa.tibiakt.client.models.TimedResult
import com.galarzaa.tibiakt.core.enums.*
import com.galarzaa.tibiakt.core.models.Highscores
import com.galarzaa.tibiakt.core.models.KillStatistics
import com.galarzaa.tibiakt.core.models.bazaar.AjaxPaginator
import com.galarzaa.tibiakt.core.models.bazaar.Auction
import com.galarzaa.tibiakt.core.models.bazaar.BazaarFilters
import com.galarzaa.tibiakt.core.models.bazaar.CharacterBazaar
import com.galarzaa.tibiakt.core.models.character.Character
import com.galarzaa.tibiakt.core.models.guild.Guild
import com.galarzaa.tibiakt.core.models.guild.GuildsSection
import com.galarzaa.tibiakt.core.models.house.House
import com.galarzaa.tibiakt.core.models.house.HousesSection
import com.galarzaa.tibiakt.core.models.news.EventsSchedule
import com.galarzaa.tibiakt.core.models.news.News
import com.galarzaa.tibiakt.core.models.news.NewsArchive
import com.galarzaa.tibiakt.core.models.world.World
import com.galarzaa.tibiakt.core.models.world.WorldOverview
import com.galarzaa.tibiakt.core.parsers.*
import com.galarzaa.tibiakt.core.utils.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.*
import io.ktor.client.features.compression.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.slf4j.LoggerFactory
import java.time.Instant
import java.time.LocalDate
import java.time.YearMonth
import kotlin.system.measureTimeMillis

/**
 * A coroutine based client to fetch from Tibia.com
 */
open class TibiaKtClient {
    private val client = HttpClient(CIO) {
        ContentEncoding {
            gzip()
            deflate()
        }
        install(UserAgent) {
            agent = "TibiaKt/"
        }
    }

    suspend fun request(
        method: HttpMethod,
        url: String,
        data: List<Pair<String, String>> = emptyList(),
    ): HttpResponse {
        val response: HttpResponse = when (method) {
            HttpMethod.Get -> client.get(url)
            HttpMethod.Post -> client.submitForm(
                url,
                formParameters = Parameters.build {
                    data.forEach {
                        append(it.first, it.second)
                    }
                },
                encodeInQuery = false
            )
            else -> throw IllegalArgumentException("Unsupported method $method")
        }
        logger.info("$url | ${method.value.uppercase()} | ${response.status.value} ${response.status.description} | ${response.fetchingTimeMillis}ms")
        return response
    }

    @OptIn(ExperimentalSerializationApi::class)
    private suspend fun fetchAjaxPage(auctionId: Int, typeId: Int, page: Int): TimedResult<String?> {
        val response: HttpResponse = client.get("https://www.tibia.com/websiteservices/handle_charactertrades.php") {
            headers {
                header("x-requested-with", "XMLHttpRequest")
            }
            parameter("auctionid", auctionId)
            parameter("type", typeId)
            parameter("currentpage", page)
        }
        logger.info("${response.request.url} | GET | ${response.status.value} ${response.status.description} | ${response.fetchingTimeMillis}ms")
        val content: String = response.receive()
        return try {
            val responseData = Json.decodeFromString<AjaxResponse>(content)
            TimedResult(response.fetchingTime, responseData.ajaxObjects.first().data)
        } catch (e: NoSuchElementException) {
            TimedResult(response.fetchingTime, null)
        }
    }

    private suspend inline fun <reified E, T : AjaxPaginator<E>> fetchAllPages(
        auctionId: Int,
        itemType: Int,
        paginator: T,
    ): List<E> {
        var currentPage = 2
        val entries: MutableList<E> = paginator.entries.toMutableList()
        var fetchingTime = 0f
        var parsingTime = 0f
        while (currentPage <= paginator.totalPages) {
            val (time, result) = fetchAjaxPage(auctionId, itemType, currentPage)
            fetchingTime += time
            if (result != null) {
                parsingTime += measureTimeMillis {
                    entries.addAll(AuctionParser.parsePageItems(result))
                } * 1000f
            }
            currentPage++
        }
        return entries
    }

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

    suspend fun fetchNews(newsId: Int): TibiaResponse<News?> {
        val response = this.request(HttpMethod.Get, getNewsUrl(newsId))
        return response.parse { NewsParser.fromContent(it, newsId) }
    }

    suspend fun fetchKillStatistics(world: String): TibiaResponse<KillStatistics> {
        val response = this.request(HttpMethod.Get, getKillStatisticsUrl(world))
        return response.parse { KillStatisticsParser.fromContent(it) }
    }

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
     * Fetch the events schedule for a specific year and month
     */
    suspend fun fetchEventsSchedule(yearMonth: YearMonth): TibiaResponse<EventsSchedule> {
        val response =
            this.request(HttpMethod.Get, getEventsScheduleUrl(yearMonth))
        return response.parse { EventsScheduleParser.fromContent(it) }
    }

    /**
     * Fetch the events schedule for the current month.
     */
    suspend fun fetchEventsSchedule() = fetchEventsSchedule(YearMonth.now())

    /**
     * Fetch the events schedule for a specific year and month
     */
    suspend fun fetchEventsSchedule(year: Int, month: Int) = fetchEventsSchedule(YearMonth.of(year, month))


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
        var itemEntries = tibiaResponse.data.details!!.items.entries
        var storeItemEntries = tibiaResponse.data.details!!.storeItems.entries
        var outfitEntries = tibiaResponse.data.details!!.outfits.entries
        var storeOutfitEntries = tibiaResponse.data.details!!.storeOutfits.entries
        var mountEntries = tibiaResponse.data.details!!.mounts.entries
        var storeMountEntries = tibiaResponse.data.details!!.storeMounts.entries
        if (fetchItems) {
            itemEntries = fetchAllPages(tibiaResponse.data.auctionId, 0, tibiaResponse.data.details!!.items)
            storeItemEntries =
                fetchAllPages(tibiaResponse.data.auctionId, 1, tibiaResponse.data.details!!.storeItems)
        }
        if (fetchMounts) {
            mountEntries = fetchAllPages(tibiaResponse.data.auctionId, 2, tibiaResponse.data.details!!.mounts)
            storeMountEntries =
                fetchAllPages(tibiaResponse.data.auctionId, 3, tibiaResponse.data.details!!.storeMounts)
        }
        if (fetchOutfits) {
            outfitEntries = fetchAllPages(tibiaResponse.data.auctionId, 4, tibiaResponse.data.details!!.outfits)
            storeOutfitEntries =
                fetchAllPages(tibiaResponse.data.auctionId, 5, tibiaResponse.data.details!!.storeOutfits)
        }
        return tibiaResponse.copy(
            data = tibiaResponse.data.copy(
                details = tibiaResponse.data.details!!.copy(
                    items = tibiaResponse.data.details!!.items.copy(
                        entries = itemEntries,
                        fullyFetched = fetchItems,
                    ),
                    storeItems = tibiaResponse.data.details!!.items.copy(
                        entries = storeItemEntries,
                        fullyFetched = fetchItems,
                    ),
                    mounts = tibiaResponse.data.details!!.mounts.copy(
                        entries = mountEntries,
                        fullyFetched = fetchMounts,
                    ),
                    storeMounts = tibiaResponse.data.details!!.storeMounts.copy(
                        entries = storeMountEntries,
                        fullyFetched = fetchMounts,
                    ),
                    outfits = tibiaResponse.data.details!!.outfits.copy(
                        entries = outfitEntries,
                        fullyFetched = fetchOutfits,
                    ),
                    storeOutfits = tibiaResponse.data.details!!.storeOutfits.copy(
                        entries = storeOutfitEntries,
                        fullyFetched = fetchOutfits,
                    ),
                )
            )
        )
    }

    private val HttpResponse.fetchingTime get() = (responseTime.timestamp - requestTime.timestamp) / 1000f
    private val HttpResponse.fetchingTimeMillis get() = (fetchingTime * 1000).toInt()

    private fun <T> HttpResponse.toTibiaResponse(parsingTime: Float, data: T): TibiaResponse<T> = TibiaResponse(
        timestamp = Instant.ofEpochMilli(responseTime.timestamp),
        isCached = headers["CF-Cache-Status"] == "HIT",
        cacheAge = headers["Age"]?.toInt() ?: 0,
        fetchingTime = fetchingTime,
        parsingTime = parsingTime,
        data = data
    )

    private suspend fun <T> HttpResponse.parse(parser: (content: String) -> T): TibiaResponse<T> {
        val data: T
        val parsingTime = measureTimeMillis {
            val stringBody: String = receive()
            data = parser(stringBody)
        } / 1000f
        logger.info("${this.request.url} | PARSE | ${(parsingTime * 1000).toInt()}ms")
        return toTibiaResponse(parsingTime, data)
    }

    internal companion object {
        internal val logger: org.slf4j.Logger = LoggerFactory.getLogger(TibiaKtClient::class.java)
    }
}

