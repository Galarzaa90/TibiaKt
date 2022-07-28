package com.galarzaa.tibiakt.core.utils

import java.time.DayOfWeek
import java.time.Instant
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

/**
 * The timezone Tibia time is based on.
 */
val tibiaTimezone: ZoneId = ZoneId.of("Europe/Berlin")

/**
 * The time when server save happens.
 */
val serverSaveTime: LocalTime = LocalTime.of(10, 0)

/** Get the current day of the week in Tibia
 *
 * A new day starts at 10:00 local time.
 */
fun getTibiaWeekDay(): DayOfWeek = (getTibiaTime() - 10.hours.toJavaDuration()).dayOfWeek

/**
 * Get the local datetime in Tibia's servers.
 */
fun getTibiaTime(): LocalDateTime = LocalDateTime.now(tibiaTimezone)

/**
 * Get the time of the last server save from a [currentTime].
 */
fun getServerSaveTime(currentTime: ZonedDateTime = ZonedDateTime.now(tibiaTimezone)): ZonedDateTime {
    val serverSaveTime = ZonedDateTime.of(
        currentTime.toLocalDate(), LocalTime.of(10, 0, 0), tibiaTimezone
    )
    return if (currentTime < serverSaveTime) serverSaveTime.minusDays(1) else serverSaveTime
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
    }.atZone(tibiaTimezone).toInstant()
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

fun parseTibiaForumDate(input: String): Instant {
    return LocalDateTime.parse(input, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss", Locale.US))
        .atZone(tibiaTimezone).toInstant()
}
