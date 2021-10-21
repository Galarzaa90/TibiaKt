package com.galarzaa.tibiakt.server.plugins

import io.ktor.application.*
import io.ktor.content.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*

fun Application.configureStatusPages() {
    install(StatusPages) {
        exception<ParameterConversionException> { it ->
            call.respond(TextContent("Parameter '${it.parameterName}' could not be converted:\n${it.cause}",
                ContentType.Text.Plain.withCharset(Charsets.UTF_8),
                HttpStatusCode.BadRequest))
        }
    }
}