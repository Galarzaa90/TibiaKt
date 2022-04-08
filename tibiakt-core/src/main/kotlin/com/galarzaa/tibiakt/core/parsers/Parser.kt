package com.galarzaa.tibiakt.core.parsers

import com.galarzaa.tibiakt.core.utils.boxContent
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.parser.Parser

internal interface Parser<T> {
    fun fromContent(content: String): T

    fun boxContent(content: String, parser: Parser? = null): Element {
        val document: Document = if(parser == null) Jsoup.parse(content) else Jsoup.parse(content, "", parser)
        return document.boxContent()
    }
}