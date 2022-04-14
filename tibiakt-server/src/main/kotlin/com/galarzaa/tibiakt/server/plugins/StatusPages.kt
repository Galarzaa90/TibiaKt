package com.galarzaa.tibiakt.server.plugins

import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.TextContent
import io.ktor.http.withCharset
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.ParameterConversionException
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond


fun Application.configureStatusPages() {
    install(StatusPages) {
        exception<ParameterConversionException> { call, cause ->
            call.respond(TextContent("Parameter '${cause.parameterName}' could not be converted:\n${cause.cause}",
                ContentType.Text.Plain.withCharset(Charsets.UTF_8),
                HttpStatusCode.BadRequest))
        }
    }
}