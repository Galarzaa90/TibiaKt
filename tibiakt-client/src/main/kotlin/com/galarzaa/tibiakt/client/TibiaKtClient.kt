package com.galarzaa.tibiakt.client

import com.galarzaa.tibiakt.client.models.TibiaResponse
import com.galarzaa.tibiakt.core.enums.*
import com.galarzaa.tibiakt.core.models.*
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
import org.slf4j.LoggerFactory
import java.time.Instant
import java.time.LocalDate
import java.time.YearMonth
import kotlin.system.measureTimeMillis


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

    suspend fun fetchCharacter(name: String): TibiaResponse<Character?> {
        val response = this.request(HttpMethod.Get, getCharacterUrl(name))
        return response.parse { CharacterParser.fromContent(it) }
    }

    suspend fun fetchWorldOverview(): TibiaResponse<WorldOverview> {
        val response = this.request(HttpMethod.Get, getWorldOverviewUrl())
        return response.parse { WorldOverviewParser.fromContent(it) }
    }

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
        val data = NewsArchive.getFormData(startDate, endDate, categories, types)
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

    suspend fun fetchHouse(
        houseId: Int,
        world: String,
    ): TibiaResponse<House?> {
        val response = this.request(HttpMethod.Get, getHouseUrl(world, houseId))
        return response.parse { HouseParser.fromContent(it) }
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

    companion object {
        val logger: org.slf4j.Logger = LoggerFactory.getLogger(TibiaKtClient::class.java)
    }
}

