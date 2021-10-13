package com.galarzaa.tibiakt.core.parsers

import com.galarzaa.tibiakt.core.utils.ParsingException
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

internal interface Parser<T> {
    fun fromContent(content: String): T

    fun boxContent(content: String): Element {
        val document: Document = Jsoup.parse(content)
        return document.selectFirst("div.BoxContent") ?: throw ParsingException("BoxContent container not found")
    }
}