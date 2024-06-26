/*
 * Copyright © 2024 Allan Galarza
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.galarzaa.tibiakt.server.plugins

import com.galarzaa.tibiakt.core.exceptions.TibiaKtException
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.TextContent
import io.ktor.http.withCharset
import io.ktor.resources.ResourceSerializationException
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.BadRequestException
import io.ktor.server.plugins.ParameterConversionException
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond


fun Application.configureStatusPages() {
    install(StatusPages) {
        exception<ParameterConversionException> { call, cause ->
            call.respond(
                TextContent(
                    "Parameter '${cause.parameterName}' could not be converted:\n${cause.cause}",
                    ContentType.Text.Plain.withCharset(Charsets.UTF_8),
                    HttpStatusCode.BadRequest
                )
            )
        }
        exception<TibiaKtException> { call, cause ->
            call.respond(
                TextContent(
                    cause.toString(),
                    ContentType.Text.Plain.withCharset(Charsets.UTF_8),
                    HttpStatusCode.InternalServerError
                )
            )
        }
        exception<BadRequestException> { call, cause ->
            when (cause.cause) {
                is ResourceSerializationException -> call.respond(
                    TextContent(
                        "$cause: ${cause.cause}", ContentType.Text.Plain.withCharset(Charsets.UTF_8),
                        HttpStatusCode.BadRequest
                    )
                )
            }
        }
        exception<Exception> { call, cause ->
            println(call)
            println(cause)

        }
    }
}
