package com.galarzaa.tibiakt.core.parsers

import com.galarzaa.tibiakt.core.builders.NewsArchiveBuilder
import com.galarzaa.tibiakt.core.builders.newsArchive
import com.galarzaa.tibiakt.core.enums.NewsCategory
import com.galarzaa.tibiakt.core.enums.NewsType
import com.galarzaa.tibiakt.core.enums.StringEnum
import com.galarzaa.tibiakt.core.exceptions.ParsingException
import com.galarzaa.tibiakt.core.models.news.NewsArchive
import com.galarzaa.tibiakt.core.utils.cells
import com.galarzaa.tibiakt.core.utils.cleanText
import com.galarzaa.tibiakt.core.utils.formData
import com.galarzaa.tibiakt.core.utils.getLinkInformation
import com.galarzaa.tibiakt.core.utils.parseTablesMap
import com.galarzaa.tibiakt.core.utils.parseTibiaDate
import com.galarzaa.tibiakt.core.utils.rows
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import java.time.LocalDate

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
            val (iconColumn, dateColumn, titleColumn) = row.cells()
            val iconUrl =
                iconColumn.selectFirst("img")?.attr("src") ?: throw ParsingException("category icon not found")
            val category =
                NewsCategory.fromIcon(iconUrl) ?: throw ParsingException("unknown category icon found: $iconUrl")
            val newsId = titleColumn.selectFirst("a")?.getLinkInformation()?.queryParams?.get("id")?.get(0)?.toInt()
                ?: throw ParsingException("Could not find link")

            val typeLabel = dateColumn.selectFirst("small") ?: throw ParsingException("could not find type label")
            val newsType = typeLabel.cleanText()
            typeLabel.remove()
            addEntry(newsId,
                titleColumn.cleanText(),
                category,
                iconUrl,
                parseTibiaDate(dateColumn.cleanText()),
                StringEnum.fromValue(newsType) ?: throw ParsingException("unexpected news type found: $newsType"))
        }
    }

    private fun NewsArchiveBuilder.parseFilterTable(element: Element) {
        val formData = element.formData()
        startDate = LocalDate.of(
            formData.values["filter_begin_year"]?.toInt()
                ?: throw ParsingException("could not find filter_begin_year in form"),
            formData.values["filter_begin_month"]?.toInt()
                ?: throw ParsingException("could not find filter_begin_month in form"),
            formData.values["filter_begin_day"]?.toInt()
                ?: throw ParsingException("could not find filter_begin_day in form"),
        )
        endDate = LocalDate.of(
            formData.values["filter_end_year"]?.toInt()
                ?: throw ParsingException("could not find filter_end_year in form"),
            formData.values["filter_end_month"]?.toInt()
                ?: throw ParsingException("could not find filter_end_month in form"),
            formData.values["filter_end_day"]?.toInt()
                ?: throw ParsingException("could not find filter_end_day in form"),
        )

        for (value in NewsCategory.values()) {
            if (!formData.valuesMultiple[value.filterName].isNullOrEmpty()) addCategory(value)
        }
        for (value in NewsType.values()) {
            if (!formData.valuesMultiple[value.filterName].isNullOrEmpty()) addType(value)
        }
    }
}
