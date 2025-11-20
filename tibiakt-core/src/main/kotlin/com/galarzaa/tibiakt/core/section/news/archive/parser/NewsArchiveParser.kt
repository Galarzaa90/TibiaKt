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

package com.galarzaa.tibiakt.core.section.news.archive.parser

import com.galarzaa.tibiakt.core.enums.StringEnum
import com.galarzaa.tibiakt.core.exceptions.ParsingException
import com.galarzaa.tibiakt.core.html.cells
import com.galarzaa.tibiakt.core.html.cleanText
import com.galarzaa.tibiakt.core.html.formData
import com.galarzaa.tibiakt.core.html.getLinkInformation
import com.galarzaa.tibiakt.core.html.parseTablesMap
import com.galarzaa.tibiakt.core.html.rows
import com.galarzaa.tibiakt.core.parser.Parser
import com.galarzaa.tibiakt.core.section.news.archive.builder.NewsArchiveBuilder
import com.galarzaa.tibiakt.core.section.news.archive.builder.newsArchive
import com.galarzaa.tibiakt.core.section.news.archive.model.NewsArchive
import com.galarzaa.tibiakt.core.section.news.shared.model.NewsCategory
import com.galarzaa.tibiakt.core.section.news.shared.model.NewsType
import com.galarzaa.tibiakt.core.time.parseTibiaDate
import kotlinx.datetime.LocalDate
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

/** Parses content from the news archive. */
public object NewsArchiveParser : Parser<NewsArchive> {
    override fun fromContent(content: String): NewsArchive {
        val document: Document = Jsoup.parse(content)
        val boxContent =
            document.selectFirst("div.BoxContent") ?: throw ParsingException("BoxContent container not found")
        val tables = boxContent.parseTablesMap()
        if (!tables.containsKey("News Archive Search")) throw ParsingException("news search not found in page")
        return newsArchive {
            boxContent.selectFirst("form")?.apply {
                parseFilterTable(this)
            }
            tables["Search Results"]?.let { parseResultsTable(it) }
        }
    }

    private fun NewsArchiveBuilder.parseResultsTable(table: Element) {
        for (row in table.rows()) {
            val rows = row.cells()
            if (rows.size != 3)
                continue
            val (iconColumn, dateColumn, titleColumn) = rows
            val iconUrl =
                iconColumn.selectFirst("img")?.attr("src") ?: throw ParsingException("category icon not found")
            val category =
                NewsCategory.Companion.fromIcon(iconUrl)
                    ?: throw ParsingException("unknown category icon found: $iconUrl")
            val newsId = titleColumn.selectFirst("a")?.getLinkInformation()?.queryParams?.get("id")?.get(0)?.toInt()
                ?: throw ParsingException("Could not find link")

            val typeLabel = dateColumn.selectFirst("small") ?: throw ParsingException("could not find type label")
            val newsType = typeLabel.cleanText()
            typeLabel.remove()
            addEntry(
                newsId,
                titleColumn.cleanText(),
                category,
                parseTibiaDate(dateColumn.cleanText()),
                StringEnum.Companion.fromValue(newsType)
                    ?: throw ParsingException("unexpected news type found: $newsType")
            )
        }
    }

    private fun NewsArchiveBuilder.parseFilterTable(element: Element) {
        val formData = element.formData()
        val builder = NewsArchiveBuilder.NewsArchiveFiltersBuilder()
        builder.startOn = LocalDate(
            formData.values["filter_begin_year"]?.toInt()
                ?: throw ParsingException("could not find filter_begin_year in form"),
            formData.values["filter_begin_month"]?.toInt()
                ?: throw ParsingException("could not find filter_begin_month in form"),
            formData.values["filter_begin_day"]?.toInt()
                ?: throw ParsingException("could not find filter_begin_day in form"),
        )
        builder.endOn = LocalDate(
            formData.values["filter_end_year"]?.toInt()
                ?: throw ParsingException("could not find filter_end_year in form"),
            formData.values["filter_end_month"]?.toInt()
                ?: throw ParsingException("could not find filter_end_month in form"),
            formData.values["filter_end_day"]?.toInt()
                ?: throw ParsingException("could not find filter_end_day in form"),
        )

        for (value in NewsCategory.entries) {
            if (!formData.valuesMultiple[value.filterName].isNullOrEmpty()) builder.addCategory(value)
        }
        for (value in NewsType.entries) {
            if (!formData.valuesMultiple[value.filterName].isNullOrEmpty()) builder.addType(value)
        }
        filters = builder.build()
    }
}
