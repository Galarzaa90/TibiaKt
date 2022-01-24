package com.galarzaa.tibiakt.client

import com.galarzaa.tibiakt.client.exceptions.ForbiddenException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf

class TibiaKtClientTest : FunSpec({
    context("getCharacter") {
        test("forbidden") {
            val engine = MockEngine {
                respond(
                    content = TestResources.getResource("forbidden.txt"),
                    status = HttpStatusCode.Forbidden,
                    headers = headersOf(HttpHeaders.ContentType, "text/html")
                )
            }
            val client = TibiaKtClient(engine)
            shouldThrow<ForbiddenException> {
                client.fetchCharacter("Cachero")
            }
        }
    }
})
