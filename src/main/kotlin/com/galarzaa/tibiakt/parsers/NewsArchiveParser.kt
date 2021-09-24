package com.galarzaa.tibiakt.parsers

import com.galarzaa.tibiakt.ParsingException
import com.galarzaa.tibiakt.builders.NewsArchiveBuilder
import com.galarzaa.tibiakt.core.getLinkInformation
import com.galarzaa.tibiakt.core.parseTibiaDate
import com.galarzaa.tibiakt.models.NewsArchive
import com.galarzaa.tibiakt.models.NewsCategory
import com.galarzaa.tibiakt.models.NewsType
import com.galarzaa.tibiakt.utils.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import java.time.LocalDate

object NewsArchiveParser : Parser<NewsArchive> {
    override fun fromContent(content: String): NewsArchive {
        val document: Document = Jsoup.parse(content)
        val boxContent =
            document.selectFirst("div.BoxContent") ?: throw ParsingException("BoxContent container not found")
        val tables = boxContent.parseTablesMap()
        if (!tables.containsKey("News Archive Search"))
            throw ParsingException("news search not found in page")
        val builder = NewsArchiveBuilder()
        boxContent.selectFirst("form")?.apply {
            parseFilterTable(this, builder)
        }
        parseResultsTable(tables["Search Results"] ?: throw ParsingException("search resulst table not found"), builder)
        return builder.build()
    }

    private fun parseResultsTable(table: Element, builder: NewsArchiveBuilder) {
        for (row in table.rows()) {
            val (iconColumn, dateColumn, titleColumn) = row.columns()
            val iconUrl =
                iconColumn.selectFirst("img")?.attr("src") ?: throw ParsingException("category icon not found")
            val category =
                NewsCategory.fromIcon(iconUrl) ?: throw ParsingException("unknown category icon found: $iconUrl")
            val newsId = titleColumn.selectFirst("a")?.getLinkInformation()?.queryParams?.get("id")?.get(0)?.toInt()
                ?: throw ParsingException("Could not find link")

            val typeLabel = dateColumn.selectFirst("small")
            val newsType = typeLabel!!.cleanText()
            typeLabel.remove()
            builder.addEntry(
                newsId,
                titleColumn.cleanText(),
                category,
                iconUrl,
                parseTibiaDate(dateColumn.cleanText()),
                NewsType.fromValue(newsType)!!
            )
        }
    }

    private fun parseFilterTable(element: Element, builder: NewsArchiveBuilder) {
        val formData = element.formData()
        builder
            .startDate(
                LocalDate.of(
                    formData.data["filter_begin_year"]!!.toInt(),
                    formData.data["filter_begin_month"]!!.toInt(),
                    formData.data["filter_begin_day"]!!.toInt(),
                )
            )
            .endDate(
                LocalDate.of(
                    formData.data["filter_end_year"]!!.toInt(),
                    formData.data["filter_end_month"]!!.toInt(),
                    formData.data["filter_end_day"]!!.toInt(),
                )
            )
        for (value in NewsCategory.values()) {
            if (!formData.dataMultiple[value.filterName].isNullOrEmpty())
                builder.addCategory(value)
        }
        for (value in NewsType.values()) {
            if (!formData.dataMultiple[value.filterName].isNullOrEmpty())
                builder.addType(value)
        }
    }
}