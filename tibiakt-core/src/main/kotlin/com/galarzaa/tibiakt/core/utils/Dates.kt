/*
 * Copyright © 2022 Allan Galarza
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

package com.galarzaa.tibiakt.core.utils


import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.YearMonth
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.Padding
import kotlinx.datetime.format.char
import kotlinx.datetime.format.optional
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.hours
import kotlin.time.Instant

public val yearMonthFormat: DateTimeFormat<YearMonth> = YearMonth.Format {
    monthName(MonthNames.ENGLISH_FULL)
    char(' ')
    year()
}

private val tibiaDateTimeFormat = LocalDateTime.Format {
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

private val tibiaDateFormat = LocalDate.Format {
    monthName(MonthNames.ENGLISH_ABBREVIATED)
    char(' ')
    day()
    char(' ')
    year()
}

private val tibiaFullDateFormat = LocalDate.Format {
    monthName(MonthNames.ENGLISH_FULL) // "MMMM"
    char(' ')
    day(Padding.NONE)
    chars(", ")
    year()
}

private val tibiaForumDateTimeFormat = LocalDateTime.Format {
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

/** The timezone Tibia time is based on. */
public val TIBIA_TIMEZONE: TimeZone = TimeZone.of("Europe/Berlin")

/** The local time when server save happens. */
public val SERVER_SAVE_TIME: LocalTime = LocalTime(10, 0)


/** Get the local datetime in Tibia's servers. */
public fun getTibiaDateTime(clock: Clock = Clock.System): LocalDateTime = clock.now().toLocalDateTime(TIBIA_TIMEZONE)

/** Get the current day of the week in Tibia
 *
 * A new day starts every server save, at 10:00 CET/CEST.
 */
public fun getTibiaWeekDay(clock: Clock = Clock.System): DayOfWeek = (clock.now() - 10.hours).toLocalDateTime(TIBIA_TIMEZONE).dayOfWeek


public fun Instant.getLastServerSaveTime(): Instant =
    LocalDateTime(toLocalDateTime(TIBIA_TIMEZONE).date, SERVER_SAVE_TIME).toInstant(TIBIA_TIMEZONE)
        .let { if (this < it) it - 1.days else it }


public fun Instant.getNextServerSaveTime(): Instant = getLastServerSaveTime() + 1.days


/**
 * Parses a string containing date and time from Tibia.com into an [Instant] instance.
 */
public fun parseTibiaDateTime(input: String): Instant =
    tibiaDateTimeFormat.parse(input.clean().removeSuffix(" CET").removeSuffix(" CEST")).toInstant(TIBIA_TIMEZONE)

/**
 * Parses a string containing date from Tibia.com into an [LocalDate] instance.
 */
public fun parseTibiaDate(input: String): LocalDate =
    tibiaDateFormat.parse(input.clean())

/**
 * Parses a string containing date from Tibia.com into an [LocalDate] instance.
 */
public fun parseTibiaFullDate(input: String): LocalDate =
    tibiaFullDateFormat.parse(input.clean())

/**
 * Parses a string containing a date time from Tibia.com forums into an [Instant] instance.
 */
public fun parseTibiaForumDateTime(input: String): Instant = tibiaForumDateTimeFormat.parse(input).toInstant(TIBIA_TIMEZONE)
