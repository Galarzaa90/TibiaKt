package com.galarzaa.tibiakt.utils

import com.galarzaa.tibiakt.core.ParsingException
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

internal fun Element.parseTablesMap(): Map<String, Element> {
    val tables = select("div.TableContainer")
    val output = mutableMapOf<String, Element>()
    for (table: Element in tables) {
        val caption: String =
            table.selectFirst("div.Text")?.cleanText() ?: throw ParsingException("table has no caption")
        val contentTable = table.selectFirst("div.TableContentContainer")
        output[caption] = contentTable ?: continue
    }
    return output
}

internal fun Element.rows(): Elements = select("tr")
internal fun Element.columns(): Elements = select("td")
internal fun Element.columnsText(): List<String> = columns().map { it.cleanText() }

internal fun Element.cleanText() = text().clean()
internal fun Element.wholeCleanText() = wholeText().clean()

internal fun Element.formData(): FormData {
    val data = mutableMapOf<String, String?>()
    val dataMultiple = mutableMapOf<String, MutableList<String>>()
    val availableOptions = mutableMapOf<String, MutableList<String>>()
    select("input[type=text]").forEach { data[it.attr("name")] = it.attr("value") }
    select("select").forEach {
        it.select("option").forEach { opt ->
            val value = opt.attr("value")
            val name = it.attr("name")
            availableOptions.getOrPut(name) { mutableListOf() }.add(value)
            if (opt.hasAttr("selected"))
                data[name] = value
        }
    }
    select("input[type=checkbox]").forEach {
        val name = it.attr("name")
        val value = it.attr("value")
        availableOptions.getOrPut(name) { mutableListOf() }.add(value)
        if (it.hasAttr("checked"))
            dataMultiple.getOrPut(name) { mutableListOf() }.add(value)
    }
    return FormData(data, dataMultiple, availableOptions)
}

data class FormData(
    val data: Map<String, String?> = emptyMap(),
    val dataMultiple: Map<String, List<String>> = emptyMap(),
    val availableOptions: Map<String, List<String>> = emptyMap()
)