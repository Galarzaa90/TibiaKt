package com.galarzaa.tibiakt.core

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.parser.Parser
import java.net.URL
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

val queryStringRegex = Regex("([^&=]+)=([^&]*)")

fun parseTibiaDateTime(input: String): Instant {
    val timeString = input.clean().substring(0, input.length - 4).trim()
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

fun String.splitList(separator: String = ",", lastSeparator: String = " and "): List<String> {
    val items = this.split(separator).toMutableList()
    val lastItem: String = items.last()
    val lastSplit: List<String> = lastItem.split(lastSeparator)
    if (lastSplit.size > 1) {
        items[items.lastIndex] = lastSplit.subList(0, lastSplit.lastIndex).joinToString(lastSeparator)
        items.add(lastSplit.last())
    }
    return items.map { it.trim() }
}

/**
 * Cleans the string of non-breaking spaces and trims whitespace.
 */
fun String.clean(): String {
    return this
        .replace("\u00A0", " ")
        .replace("&#xa0;", " ")
        .trim()
}