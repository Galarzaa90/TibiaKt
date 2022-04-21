package com.galarzaa.tibiakt.core.parsers

import com.galarzaa.tibiakt.core.builders.news
import com.galarzaa.tibiakt.core.enums.NewsCategory
import com.galarzaa.tibiakt.core.exceptions.ParsingException
import com.galarzaa.tibiakt.core.models.news.News
import com.galarzaa.tibiakt.core.utils.cleanText
import com.galarzaa.tibiakt.core.utils.getLinkInformation
import com.galarzaa.tibiakt.core.utils.parseTibiaDate
import com.galarzaa.tibiakt.core.utils.remove

object NewsParser : Parser<News?> {
    override fun fromContent(content: String): News? = fromContent(content, 0)

    fun fromContent(content: String, newsId: Int = 0): News? {
        val boxContent = boxContent(content)

        val titleContainer = boxContent.selectFirst("div.NewsHeadlineText")
            ?: if ("not found" in (boxContent.selectFirst("div.CaptionContainer")?.cleanText() ?: "")) return null
            else throw ParsingException("News headline not found.")
        val icon = boxContent.selectFirst("img.NewsHeadlineIcon") ?: throw ParsingException("News icon not found.")
        return news {
            id = newsId
            title = titleContainer.cleanText()
            category = NewsCategory.fromIcon(icon.attr("src"))
                ?: throw ParsingException("Unexpected news icon found: ${icon.attr("src")}")

            val newsDate = boxContent.selectFirst("div.NewsHeadlineDate")?.cleanText()?.remove("-")?.trim()
            date = parseTibiaDate(newsDate ?: throw ParsingException("News date not found."))

            this.content = boxContent.selectFirst("td.NewsTableContainer")?.html()
                ?: throw ParsingException("News content not found.")

            boxContent.selectFirst("div.NewsForumLink > a")?.apply {
                threadId = getLinkInformation()?.queryParams?.get("threadid")?.get(0)?.toInt()
            }
        }
    }
}