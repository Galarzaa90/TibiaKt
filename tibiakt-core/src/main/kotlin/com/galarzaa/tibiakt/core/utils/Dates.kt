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

import kotlinx.datetime.Instant
import kotlinx.datetime.toJavaInstant
import kotlinx.datetime.toKotlinInstant
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Locale
import kotlin.time.Duration.Companion.hours
import kotlin.time.toJavaDuration

/** The timezone Tibia time is based on. */
val TIBIA_TIMEZONE: ZoneId = ZoneId.of("Europe/Berlin")

/** The local time when server save happens. */
val SERVER_SAVE_TIME: LocalTime = LocalTime.of(10, 0)

/** Get the current day of the week in Tibia
 *
 * A new day starts at 10:00 local time.
 */
fun getTibiaWeekDay(): DayOfWeek = (getTibiaDateTime() - 10.hours.toJavaDuration()).dayOfWeek

/** Get the local datetime in Tibia's servers. */
fun getTibiaDateTime(): LocalDateTime = LocalDateTime.now(TIBIA_TIMEZONE)

/** Get the time of the last server save from a [currentTime]. */
fun getLastServerSaveTime(currentTime: ZonedDateTime = ZonedDateTime.now(TIBIA_TIMEZONE)): ZonedDateTime {
    val serverSaveTime = ZonedDateTime.of(
        currentTime.toLocalDate(), SERVER_SAVE_TIME, TIBIA_TIMEZONE
    )
    return if (currentTime < serverSaveTime) serverSaveTime.minusDays(1) else serverSaveTime
}

/** Get the time of the last server save from a [currentTime]. */
fun getLastServerSaveTime(currentTime: Instant): Instant {
    return getLastServerSaveTime(ZonedDateTime.ofInstant(currentTime.toJavaInstant(), TIBIA_TIMEZONE)).toInstant()
        .toKotlinInstant()
}

/** Get the time of the next server save from a [currentTime]. */
fun getNextServerSaveTime(currentTime: ZonedDateTime = ZonedDateTime.now(TIBIA_TIMEZONE)): ZonedDateTime {
    return getLastServerSaveTime(currentTime).plusDays(1)
}

/** Get the time of the next server save from a [currentTime]. */
fun getNextServerSaveTime(currentTime: Instant): Instant {
    return getNextServerSaveTime(ZonedDateTime.ofInstant(currentTime.toJavaInstant(), TIBIA_TIMEZONE)).toInstant()
        .toKotlinInstant()
}


/**
 * Parses a string containing date and time from Tibia.com into an [Instant] instance.
 */
fun parseTibiaDateTime(input: String): Instant {
    val timeString = input.clean().let { it.substring(0, it.length - 4).trim() }
    return try {
        LocalDateTime.parse(timeString, DateTimeFormatter.ofPattern("MMM dd yyyy, HH:mm:ss", Locale.US))
    } catch (dfe: DateTimeParseException) {
        LocalDateTime.parse(timeString, DateTimeFormatter.ofPattern("MMM dd yyyy, HH:mm", Locale.US))
    }.atZone(TIBIA_TIMEZONE).toInstant().toKotlinInstant()
}

/**
 * Parses a string containing date from Tibia.com into an [LocalDate] instance.
 */
fun parseTibiaDate(input: String): LocalDate {
    return LocalDate.parse(input.clean(), DateTimeFormatter.ofPattern("MMM dd yyyy", Locale.US))
}

/**
 * Parses a string containing date from Tibia.com into an [LocalDate] instance.
 */
fun parseTibiaFullDate(input: String): LocalDate {
    return LocalDate.parse(input.clean(), DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.US))
}

/**
 * Parses a string containing a date time from Tibia.com forums into an [Instant] instance.
 */
fun parseTibiaForumDateTime(input: String): Instant {
    return LocalDateTime.parse(input, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss", Locale.US))
        .atZone(TIBIA_TIMEZONE).toInstant().toKotlinInstant()
}
