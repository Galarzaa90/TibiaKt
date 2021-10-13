package com.galarzaa.tibiakt.server.plugins

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.util.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

internal fun Application.configureDataConversion() {
    install(DataConversion) {
        convert<LocalDate> {
            val format = DateTimeFormatter.ISO_DATE
            decode { values, _ ->
                values.singleOrNull()?.let { LocalDate.parse(it, format) }
            }
            encode { value ->
                when (value) {
                    null -> listOf()
                    is LocalDate -> listOf(format.format(value))
                    else -> throw DataConversionException("Cannot convert $value as LocalDate")
                }
            }
        }
    }
}