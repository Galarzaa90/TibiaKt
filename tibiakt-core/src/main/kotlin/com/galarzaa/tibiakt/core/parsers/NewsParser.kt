package com.galarzaa.tibiakt.core.parsers

import com.galarzaa.tibiakt.core.builders.NewsBuilder
import com.galarzaa.tibiakt.core.models.News
import com.galarzaa.tibiakt.core.utils.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

object NewsParser : Parser<News?> {
    override fun fromContent(content: String): News? = fromContent(content, 0)

    fun fromContent(content: String, newsId: Int = 0): News? {
        val document: Document = Jsoup.parse(content)
        val boxContent =
            document.selectFirst("div.BoxContent") ?: throw ParsingException("BoxContent container not found")

        val titleContainer =
            boxContent.selectFirst("div.NewsHeadlineText") ?: throw ParsingException("News headline not found.")
        val icon = boxContent.selectFirst("img.NewsHeadlineIcon") ?: throw ParsingException("News icon not found.")
        val builder = NewsBuilder()
            .id(newsId)
            .title(titleContainer.cleanText())
            .category(icon.attr("src"))

        val newsDate = boxContent.selectFirst("div.NewsHeadlineDate")?.cleanText()?.remove("-")?.trim()
        builder.date(parseTibiaDate(newsDate ?: throw ParsingException("News date not found.")))

        val newsTable = boxContent.selectFirst("td.NewsTableContainer")?.children().toString()
        builder.content(newsTable)

        boxContent.selectFirst("div.NewsForumLink > a")?.apply {
            builder.threadId(getLinkInformation()?.queryParams?.get("threadid")?.get(0)?.toInt())
        }

        return builder.build()
    }
}