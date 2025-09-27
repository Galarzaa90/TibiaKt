/*
 * Copyright © 2025 Allan Galarza
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.galarzaa.tibiakt.core.html

import com.galarzaa.tibiakt.core.exceptions.ParsingException
import com.galarzaa.tibiakt.core.text.clean
import com.galarzaa.tibiakt.core.text.parseInteger
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.nodes.TextNode
import org.jsoup.parser.Parser
import org.jsoup.select.Elements
import java.net.URL

internal const val TABLE_SELECTOR = "table.TableContent"

internal fun Element.boxContent(): Element =
    selectFirst("div.BoxContent") ?: throw ParsingException("BoxContent container not found")


internal fun Element.parseTables(contentTableSelector: String = TABLE_SELECTOR): Map<String, Elements> {
    val tables = select("div.TableContainer")
    val output = mutableMapOf<String, Elements>()
    for (table: Element in tables) {
        val captionContainer = table.selectFirst("div.CaptionContainer")
        val contentTable = table.selectFirst(contentTableSelector)
        val caption = captionContainer?.text() ?: throw ParsingException("table has no caption container")
        if (contentTable == null) continue
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

/** Get a list of rows in the element. */
internal fun Element?.rows(): Elements = this?.select("tr") ?: Elements()
internal fun Elements?.rows(): Elements = this?.select("tr") ?: Elements()

/** Get a list of cells found in the elmenet. */
internal fun Element?.cells(): Elements = this?.select("td") ?: Elements()

/** Get a list of the text contained in all cells in the element.*/
internal fun Element.cellsText(): List<String> = cells().map { it.cleanText() }

/** Get the text contained in an element, cleaned out of extraneous characters. */
internal fun Element.cleanText() = text().clean()
internal fun TextNode.cleanText() = text().clean()

/** Get the text contained in a list of element, cleaned out of extraneous characters. */
internal fun Elements.cleanText() = text().clean()

/** Get the text contained in an element, cleaned out of extraneous characters. */
internal fun Element.wholeCleanText() = wholeText().clean()


/** Replace all br tags in an element with line jumps. */
internal fun Element.replaceBrs() = apply {
    select("br").forEach { it.replaceWith(TextNode("\n")) }
}

/** Replace all br tags in an array of elements with line jumps. */
internal fun ArrayList<Element>.replaceBr() = forEach { it.replaceBrs() }

/** Get all the field's values and available options of a form element.
 *
 * @receiver An element with the form tag.
 */
internal fun Element.formData(): FormData {
    require(this.tagName().lowercase() == "form") {
        "expected element with 'form' tag, got element with '${this.tagName()}' tag"
    }
    val data = mutableMapOf<String, String>()
    val dataMultiple = mutableMapOf<String, MutableList<String>>()
    val availableOptions = mutableMapOf<String, MutableList<String>>()
    select("input[type=text], input[type=hidden]").forEach { data[it.attr("name")] = it.attr("value") }
    select("select").forEach {
        it.select("option").forEach { opt ->
            val value = opt.attr("value")
            val name = it.attr("name")
            availableOptions.getOrPut(name) { mutableListOf() }.add(value)
            if (opt.hasAttr("selected")) data[name] = value
        }
    }
    select("input[type=checkbox]").forEach {
        val name = it.attr("name")
        val value = it.attr("value")
        availableOptions.getOrPut(name) { mutableListOf() }.add(value)
        if (it.hasAttr("checked")) dataMultiple.getOrPut(name) { mutableListOf() }.add(value)
    }
    select("input[type=radio]").forEach {
        val name = it.attr("name")
        val value = it.attr("value")
        availableOptions.getOrPut(name) { mutableListOf() }.add(value)
        if (it.hasAttr("checked")) data[name] = value
    }
    return FormData(data, dataMultiple, availableOptions, action = attr("action"), method = attr("method"))
}

private val pageRegex = Regex("""(?:page|pagenumber)=(\d+)""")
private val resultsRegex = Regex("""Results: ([\d,]+)""")

/** Parse the pagination block present in many Tibia.com sections. */
internal fun Element.parsePagination(): PaginationData {
    val (pagesDiv, resultsDiv) = select("small > div")
    val currentPageLink = pagesDiv.selectFirst(".CurrentPageLink")
    val pageLinks = pagesDiv.select(".PageLink")
    val firstOrLastPages = pagesDiv.select(".FirstOrLastElement")
    val totalPages = if (firstOrLastPages.isNotEmpty()) {
        val lastPageLink = firstOrLastPages.last()?.selectFirst("a")
        if (lastPageLink != null) {
            pageRegex.find(lastPageLink.attr("href"))?.let {
                it.groups[1]!!.value.toInt()
            } ?: throw ParsingException("Could not parse pagination info")
        } else {
            pageLinks[pageLinks.size - 2].text().toInt()
        }
    } else {
        pageLinks.last()?.text()?.toInt() ?: throw ParsingException("could not find last page link")
    }
    val page = try {
        currentPageLink?.text()?.toInt() ?: totalPages
    } catch (nfe: NumberFormatException) {
        if (currentPageLink?.text()?.contains("First") == true) 1
        else totalPages
    }
    val resultsCount: Int = resultsRegex.find(resultsDiv.text())?.let {
        it.groups[1]!!.value.parseInteger()
    } ?: 0
    return PaginationData(page, totalPages, resultsCount)
}

internal fun parsePopup(content: String): Pair<String, Document> {
    val parts = content.split(",", limit = 3)
    val title = parts[1].replace("'", "").trim()
    val html = parts[parts.size - 1].replace("\\'", "\"").replace("'", "").replace(",);", "").replace(", );", "").trim()
    val parsedHtml = Jsoup.parse(html, "", Parser.xmlParser())
    return title to parsedHtml
}

private val queryStringRegex = Regex("([^&=]+)=([^&]*)")
internal fun Element.getLinkInformation(): LinkInformation? {
    if (this.tagName() != "a") return null
    return LinkInformation(this.text(), this.attr("href"))
}

internal fun URL.queryParams(): HashMap<String, MutableList<String>> {
    val matches = queryStringRegex.findAll(this.query)
    val map: HashMap<String, MutableList<String>> = hashMapOf()
    for (match: MatchResult in matches) {
        val (_, name, value) = match.groupValues
        map.getOrPut(name) { mutableListOf() }.add(value)
    }
    return map
}
