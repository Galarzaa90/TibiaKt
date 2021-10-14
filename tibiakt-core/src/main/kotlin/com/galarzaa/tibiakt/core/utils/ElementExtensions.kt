package com.galarzaa.tibiakt.core.utils

import org.jsoup.nodes.Element
import org.jsoup.nodes.TextNode
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

internal fun Element.parseTablesMap(contentSelector: String = "div.TableContentContainer"): Map<String, Element> {
    val tables = select("div.TableContainer")
    val output = mutableMapOf<String, Element>()
    for (table: Element in tables) {
        val caption: String =
            table.selectFirst("div.Text")?.cleanText() ?: throw ParsingException("table has no caption")
        val contentTable = table.selectFirst(contentSelector)
        output[caption] = contentTable ?: continue
    }
    return output
}

internal fun Element?.rows(): Elements = this?.select("tr") ?: Elements()
internal fun Element?.columns(): Elements = this?.select("td") ?: Elements()
internal fun Element.columnsText(): List<String> = columns().map { it.cleanText() }

internal fun Element.cleanText() = text().clean()
internal fun Element.wholeCleanText() = wholeText().clean()

internal fun ArrayList<Element>.replaceBr() {
    forEach { it.replaceBrs() }
}

internal fun Element.replaceBrs() = apply {
    select("br").forEach { it.replaceWith(TextNode("\n")) }
}

internal fun Element.formData(): FormData {
    if (this.tagName() != "form")
        throw IllegalArgumentException("receiver must be a form element")
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
    select("input[type=radio]").forEach {
        val name = it.attr("name")
        val value = it.attr("value")
        availableOptions.getOrPut(name) { mutableListOf() }.add(value)
        if (it.hasAttr("checked"))
            data[name] = value
    }
    return FormData(data, dataMultiple, availableOptions, action = attr("action"), method = attr("method"))
}

/**
 * Contains the data extracted from a form.
 * @property data Mapping of form fields to their selected value.
 * @property dataMultiple Mapping of form fields that might have multiple values.
 * @property availableOptions Mapping of the available options for selection in the form.
 * @property action Where the form would be submitted to.
 * @property method The HTTP method used
 */
internal data class FormData(
    val data: Map<String, String?> = emptyMap(),
    val dataMultiple: Map<String, List<String>> = emptyMap(),
    val availableOptions: Map<String, List<String>> = emptyMap(),
    val action: String? = null,
    val method: String? = null,
)

private val pageRegex = Regex("""page=(\d+)""")
private val resultsRegex = Regex("""Results: ([\d,]+)""")

internal fun Element.parsePagination(): PaginationData {
    val (pagesDiv, resultsDiv) = select("div")
    val currentPageLink =
        pagesDiv.selectFirst(".CurrentPageLink") ?: throw ParsingException("Could not parse pagination info")
    val pageLinks = pagesDiv.select(".PageLink")
    val firstOrLastPages = pagesDiv.select(".FirstOrLastElement")
    val totalPages = if (!firstOrLastPages.isEmpty()) {
        val lastPageLink = firstOrLastPages.last()?.selectFirst("a")
        if (lastPageLink != null) {
            pageRegex.find(lastPageLink.attr("href"))?.let {
                it.groups[1]!!.value.toInt()
            } ?: throw ParsingException("Could not parse pagination info")
        } else {
            pageLinks[pageLinks.size - 2].text().toInt()
        }
    } else {
        pageLinks.last()!!.text().toInt()
    }
    val page = try {
        currentPageLink.text().toInt()
    } catch (nfe: NumberFormatException) {
        if (currentPageLink.text().contains("First"))
            1
        else
            totalPages
    }
    val resultsCount: Int = resultsRegex.find(resultsDiv.text())?.let {
        it.groups[1]!!.value.parseInteger()
    } ?: 0
    return PaginationData(page, totalPages, resultsCount)
}

internal data class PaginationData(
    val currentPage: Int,
    val totalPages: Int,
    val resultsCount: Int
)