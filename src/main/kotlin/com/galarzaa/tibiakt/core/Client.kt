package com.galarzaa.tibiakt.core

import com.galarzaa.tibiakt.models.*
import com.galarzaa.tibiakt.parsers.CharacterParser
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

    suspend fun fetchCharacter(name: String): TibiaResponse<Character> {
        val response = this.request(HttpMethod.Get, Character.getUrl(name))
        val data: Character?
        val stringBody: String = response.original.receive()
        val parsingTime = measureTimeMillis {
            data = CharacterParser.fromContent(stringBody)
        } / 1000f
        return response.toTibiaResponse(parsingTime, data)
    }

    private val HttpResponse.fetchingTime: Float
        get() {
            return (responseTime.toJvmDate().toInstant().toEpochMilli() -
                    requestTime.toJvmDate().toInstant().toEpochMilli()) / 1000f
        }
}

