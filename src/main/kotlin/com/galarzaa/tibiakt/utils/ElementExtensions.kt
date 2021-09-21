package com.galarzaa.tibiakt.utils

import com.galarzaa.tibiakt.ParsingException
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

internal fun Element.parseTables(contentTableSelector: String = "table.TableContent"): Map<String, Elements> {
    val tables = select("div.TableContainer")
    val output = mutableMapOf<String, Elements>()
    for (table: Element in tables) {
        val captionContainer = table.selectFirst("div.CaptionContainer")
        val contentTable = table.selectFirst(contentTableSelector)
        val caption = captionContainer?.text() ?: throw ParsingException("table has no caption container")
        if (contentTable == null)
            continue
        val rows = contentTable.select("tr")
        output[caption] = rows
    }
    return output
}