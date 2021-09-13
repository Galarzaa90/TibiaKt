package com.galarzaa.tibiakt.core

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.parser.Parser
import java.net.URL
import java.net.URLEncoder
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

val queryStringRegex = Regex("([^&=]+)=([^&]*)")

fun getTibiaUrl(section: String, vararg params: Pair<String, String>, test: Boolean = false): String {
    val baseUrl = if (test) "www.test.tibia.com" else "www.tibia.com"
    return "https://$baseUrl/$section/?${
        params.joinToString("&") { (name, value) ->
            "$name=${URLEncoder.encode(value, Charsets.ISO_8859_1)}"
        }
    }"
}

fun parseTibiaDateTime(input: String): Instant {
    val timeString = input.replace("&#160;", " ").substring(0, input.length - 4).trim()
    val tzString = input.substring(input.length - 4)
    return LocalDateTime.parse(
        timeString,
        DateTimeFormatter.ofPattern("MMM dd yyyy, HH:mm:ss")
    ).atOffset(ZoneOffset.of(if (tzString == "CEST") "+02:00" else "+01:00")).toInstant()
}

fun parseTibiaDate(input: String): LocalDate {
    return LocalDate.parse(
        input,
        DateTimeFormatter.ofPattern("MMM dd yyyy")
    )
}

internal fun Element.getLinkInformation(): LinkInformation? {
    if (this.tagName() != "a")
        return null
    return LinkInformation(this.text(), this.attr("href"))
}

internal class LinkInformation(val title: String, targetUrl: String) {
    val targetUrl: URL

    init {
        this.targetUrl = URL(targetUrl)
    }

    val queryParams: HashMap<String, MutableList<String>>
        get() {
            val matches = queryStringRegex.findAll(this.targetUrl.query)
            val map = hashMapOf<String, MutableList<String>>()
            for (match: MatchResult in matches) {
                val (_, name, value) = match.groupValues
                if (!map.containsKey(name))
                    map[name] = mutableListOf()
                map[name]?.add(value)
            }
            return map
        }
}

internal fun parsePopup(content: String): Pair<String, Document> {
    val parts = content.split(",", limit = 3)
    val title = parts[1]
        .replace("'", "")
        .trim()
    val html = parts[parts.size - 1]
        .replace("\\'", "\"")
        .replace("'", "")
        .replace(",);", "")
        .replace(", );", "")
        .trim()
    val parsedHtml = Jsoup.parse(html, "", Parser.xmlParser())
    return Pair(title, parsedHtml)
}