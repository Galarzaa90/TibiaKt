package com.galarzaa.tibiakt.client

import com.galarzaa.tibiakt.client.models.TibiaResponse
import com.galarzaa.tibiakt.client.models.TimedResponse
import com.galarzaa.tibiakt.enums.NewsCategory
import com.galarzaa.tibiakt.enums.NewsType
import com.galarzaa.tibiakt.models.*
import com.galarzaa.tibiakt.parsers.*
import com.galarzaa.tibiakt.utils.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.*
import io.ktor.client.features.compression.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.util.date.*
import java.time.LocalDate
import kotlin.system.measureTimeMillis


class Client {
    val client = HttpClient(CIO) {
        ContentEncoding {
            gzip()
            deflate()
        }
        install(UserAgent) {
            agent = "TibiaKt/"
        }
    }

    private suspend fun request(
        method: HttpMethod,
        url: String,
        data: List<Pair<String, String>> = emptyList()
    ): TimedResponse {
        val response: HttpResponse = when (method) {
            HttpMethod.Get -> client.get(url)
            HttpMethod.Post -> client.submitForm(
                url,
                formParameters = Parameters.build {
                    data.forEach { append(it.first, it.second) }
                },
                encodeInQuery = false
            )
            else -> throw IllegalArgumentException("Unsupported method")
        }
        return TimedResponse(response)
    }

    private suspend fun <T> parseResponse(response: TimedResponse, parser: (content: String) -> T): TibiaResponse<T> {
        val data: T
        val parsingTime = measureTimeMillis {
            val stringBody: String = response.original.receive()
            data = parser(stringBody)
        } / 1000f
        return response.toTibiaResponse(parsingTime, data)
    }


    suspend fun fetchCharacter(name: String): TibiaResponse<Character?> {
        val response = this.request(HttpMethod.Get, getCharacterUrl(name))
        return parseResponse(response) { CharacterParser.fromContent(it) }
    }

    suspend fun fetchWorldOverview(): TibiaResponse<WorldOverview> {
        val response = this.request(HttpMethod.Get, getWorldOverviewUrl())
        return parseResponse(response) { WorldOverviewParser.fromContent(it) }
    }

    suspend fun fetchWorld(name: String): TibiaResponse<World?> {
        val response = this.request(HttpMethod.Get, getWorldUrl(name))
        return parseResponse(response) { WorldParser.fromContent(it) }
    }

    suspend fun fetchGuild(name: String): TibiaResponse<Guild?> {
        val response = this.request(HttpMethod.Get, getGuildUrl(name))
        return parseResponse(response) { GuildParser.fromContent(it) }
    }

    suspend fun fetchWorldGuilds(name: String): TibiaResponse<GuildsSection?> {
        val response = this.request(HttpMethod.Get, getWorldGuildsUrl(name))
        return parseResponse(response) { GuildsSectionParser.fromContent(it) }
    }

    suspend fun fetchRecentNews(
        days: Int = 30,
        categories: Set<NewsCategory>? = null,
        types: Set<NewsType>? = null
    ): TibiaResponse<NewsArchive> {
        val data = NewsArchive.getFormData(LocalDate.now().minusDays(days.toLong()), LocalDate.now(), categories, types)
        val response = this.request(HttpMethod.Post, getNewsArchiveUrl(), data)
        return parseResponse(response) { NewsArchiveParser.fromContent(it) }
    }

    suspend fun fetchNews(newsId: Int): TibiaResponse<News?> {
        val response = this.request(HttpMethod.Get, getNewsUrl(newsId))
        return parseResponse(response) { NewsParser.fromContent(it, newsId) }
    }

    private fun <T> TimedResponse.toTibiaResponse(parsingTime: Float, data: T): TibiaResponse<T> {
        val isCached = original.headers["CF-Cache-Status"] == "HIT"
        val age = original.headers["Age"]?.toInt() ?: 0
        return TibiaResponse(
            timestamp = original.responseTime.toJvmDate().toInstant(),
            isCached = isCached,
            cacheAge = age,
            fetchingTime = fetchingTime,
            parsingTime = parsingTime,
            data = data
        )
    }
}

