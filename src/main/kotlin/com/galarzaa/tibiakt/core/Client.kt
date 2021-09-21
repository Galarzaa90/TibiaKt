package com.galarzaa.tibiakt.core

import com.galarzaa.tibiakt.models.*
import com.galarzaa.tibiakt.parsers.CharacterParser
import com.galarzaa.tibiakt.parsers.WorldOverviewParser
import com.galarzaa.tibiakt.utils.getCharacterUrl
import com.galarzaa.tibiakt.utils.getWorldOverviewUrl
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.*
import io.ktor.client.features.compression.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.util.date.*
import kotlin.system.measureTimeMillis


class Client {
    val client = HttpClient(CIO) {
        ContentEncoding {
            gzip()
            deflate()
        }
        install(UserAgent) {
            agent = "TibiaKt"
        }
    }

    private suspend fun request(method: HttpMethod, url: String): TimedResponse {
        return when (method) {
            HttpMethod.Get -> {
                val response: HttpResponse = client.get(url)
                TimedResponse(response, response.fetchingTime)
            }
            HttpMethod.Post -> {
                val response: HttpResponse = client.post(url)
                TimedResponse(response, response.fetchingTime)
            }
            else -> throw IllegalArgumentException("Unsupported method")
        }
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

    private val HttpResponse.fetchingTime: Float
        get() {
            return (responseTime.toJvmDate().toInstant().toEpochMilli() -
                    requestTime.toJvmDate().toInstant().toEpochMilli()) / 1000f
        }
}

