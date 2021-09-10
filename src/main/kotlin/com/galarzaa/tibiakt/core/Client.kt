package com.galarzaa.tibiakt.core

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.*
import io.ktor.client.features.compression.ContentEncoding
import io.ktor.client.request.*
import io.ktor.client.statement.*
import com.galarzaa.tibiakt.models.*

class Client {
    val client = HttpClient(CIO){
        ContentEncoding {
            gzip()
            deflate()
        }
        defaultRequest {
            header("Accept-Encoding", "deflate, gzip")
            header("User-Agent", "TibiaKt")
        }
    }

    suspend fun fetchCharacter(name: String): Character?{
        val response: HttpResponse = client.get("https://www.tibia.com/community/?subtopic=characters&name=$name")
        val stringBody: String = response.receive()
        return Character.parseFromContent(stringBody)
    }
}