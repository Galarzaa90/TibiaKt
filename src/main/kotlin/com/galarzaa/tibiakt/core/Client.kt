package com.galarzaa.tibiakt.core

import com.galarzaa.tibiakt.models.Character
import com.galarzaa.tibiakt.parsers.CharacterParser
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.*
import io.ktor.client.features.compression.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

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
        val url = getTibiaUrl("community", Pair("subtopic", "characters"), Pair("name", name))
        val response: HttpResponse = client.get(url)
        val stringBody: String = response.receive()
        return CharacterParser.fromContent(stringBody)
    }
}