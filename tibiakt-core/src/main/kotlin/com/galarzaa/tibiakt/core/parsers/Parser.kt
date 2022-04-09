package com.galarzaa.tibiakt.core.parsers

import com.galarzaa.tibiakt.core.utils.boxContent
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.parser.Parser

internal interface Parser<T> {
    fun fromContent(content: String): T

    fun boxContent(content: String, parser: Parser? = null): Element {
        val document: Document = content.replaceFirst("ISO-8859-1", "utf-8")
            .let { if (parser == null) Jsoup.parse(it) else Jsoup.parse(it, "", parser) }
        return document.boxContent()
    }
}