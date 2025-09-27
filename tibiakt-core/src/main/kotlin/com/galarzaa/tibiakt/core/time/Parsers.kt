/*
 * Copyright © 2025 Allan Galarza
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

package com.galarzaa.tibiakt.core.time

import com.galarzaa.tibiakt.core.utils.clean
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.YearMonth
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.Padding
import kotlinx.datetime.format.char
import kotlinx.datetime.format.optional
import kotlinx.datetime.toInstant
import kotlin.time.Instant

internal val FORMAT_YEAR_MONTH: DateTimeFormat<YearMonth> = YearMonth.Format {
    monthName(MonthNames.ENGLISH_FULL)
    char(' ')
    year()
}

private val FORMAT_TIBIA_DATE_TIME = LocalDateTime.Format {
    monthName(MonthNames.ENGLISH_ABBREVIATED)
    char(' ')
    day()
    char(' ')
    year()
    chars(", ")
    hour()
    char(':')
    minute()
    optional {
        char(':')
        second()
    }
}

private val FORMAT_TIBIA_DATE = LocalDate.Format {
    monthName(MonthNames.ENGLISH_ABBREVIATED)
    char(' ')
    day()
    char(' ')
    year()
}

private val FORMAT_TIBIA_FULL_DATE = LocalDate.Format {
    monthName(MonthNames.ENGLISH_FULL) // "MMMM"
    char(' ')
    day(Padding.NONE)
    chars(", ")
    year()
}

private val FORMAT_TIBIA_FORUM_DATE = LocalDateTime.Format {
    day()
    char('.')
    monthNumber()
    char('.')
    year()
    char(' ')
    hour()
    char(':')
    minute()
    char(':')
    second()
}


/**
 * Parses a string containing date and time from Tibia.com into an [Instant] instance.
 */
public fun parseTibiaDateTime(input: String): Instant =
    FORMAT_TIBIA_DATE_TIME.parse(input.clean().removeSuffix(" CET").removeSuffix(" CEST")).toInstant(TIBIA_TIMEZONE)

/**
 * Parses a string containing date from Tibia.com into an [LocalDate] instance.
 */
public fun parseTibiaDate(input: String): LocalDate =
    FORMAT_TIBIA_DATE.parse(input.clean())

/**
 * Parses a string containing date from Tibia.com into an [LocalDate] instance.
 */
public fun parseTibiaFullDate(input: String): LocalDate =
    FORMAT_TIBIA_FULL_DATE.parse(input.clean())

/**
 * Parses a string containing a date time from Tibia.com forums into an [Instant] instance.
 */
public fun parseTibiaForumDateTime(input: String): Instant =
    FORMAT_TIBIA_FORUM_DATE.parse(input).toInstant(TIBIA_TIMEZONE)
