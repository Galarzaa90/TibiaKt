package com.galarzaa.tibiakt.core.utils

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

/**
 * Parses a string containing date and time from Tibia.com into an Instant instance.
 */
fun parseTibiaDateTime(input: String): Instant {
    val timeString = input.clean().substring(0, input.length - 4).trim()
    val tzString = input.substring(input.length - 4)
    return LocalDateTime.parse(
        timeString,
        DateTimeFormatter.ofPattern("MMM dd yyyy, HH:mm:ss")
    ).atOffset(ZoneOffset.of(if (tzString == "CEST") "+02:00" else "+01:00")).toInstant()
}

/**
 * Parses a string containing date from Tibia.com into an Instant instance.
 */
fun parseTibiaDate(input: String): LocalDate {
    return LocalDate.parse(
        input.clean(),
        DateTimeFormatter.ofPattern("MMM dd yyyy")
    )
}

/**
 * Parses a string containing date from Tibia.com into an Instant instance.
 */
fun parseTibiaFullDate(input: String): LocalDate {
    return LocalDate.parse(
        input.clean(),
        DateTimeFormatter.ofPattern("MMMM d, yyyy")
    )
}


