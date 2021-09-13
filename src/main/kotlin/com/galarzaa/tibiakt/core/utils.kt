package com.galarzaa.tibiakt.core

import java.net.URLEncoder
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

fun getTibiaUrl(section: String, vararg params: Pair<String, String>, test: Boolean = false): String {
    val baseUrl = if (test) "www.test.tibia.com" else "www.tibia.com"
    return "https://$baseUrl/$section/?${
        params.joinToString("&") { (name, value) ->
            "$name=${URLEncoder.encode(value, Charsets.ISO_8859_1)}"
        }
    }"
}

fun parseTibiaTime(input: String): Instant {
    val timeString = input.replace("&#160;", " ").substring(0, input.length - 4).trim()
    val tzString = input.substring(input.length - 4)
    return LocalDateTime.parse(
        timeString,
        DateTimeFormatter.ofPattern("MMM dd yyyy, HH:mm:ss")
    ).atOffset(ZoneOffset.of(if (tzString == "CEST") "+02:00" else "+01:00")).toInstant()
}

