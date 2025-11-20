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

package com.galarzaa.tibiakt.core.section.news.article.parser

import com.galarzaa.tibiakt.core.exceptions.ParsingException
import com.galarzaa.tibiakt.core.html.cleanText
import com.galarzaa.tibiakt.core.html.getLinkInformation
import com.galarzaa.tibiakt.core.parser.Parser
import com.galarzaa.tibiakt.core.section.news.article.builder.news
import com.galarzaa.tibiakt.core.section.news.article.model.NewsArticle
import com.galarzaa.tibiakt.core.section.news.shared.model.NewsCategory
import com.galarzaa.tibiakt.core.text.remove
import com.galarzaa.tibiakt.core.time.parseTibiaDate

/** Parser for news articles. */
public object NewsParser : Parser<NewsArticle?> {
    override fun fromContent(content: String): NewsArticle? = fromContent(content, 0)

    /**
     * Parse the content from Tibia.com into a news article.
     *
     * @param newsId The news ID to assign to the article, since it is not possible to know this only from the HTML content.
     */
    public fun fromContent(content: String, newsId: Int = 0): NewsArticle? {
        val boxContent = boxContent(content)

        val titleContainer = boxContent.selectFirst("div.NewsHeadlineText")
            ?: if ("not found" in (boxContent.selectFirst("div.CaptionContainer")?.cleanText().orEmpty())) return null
            else throw ParsingException("News headline not found.")
        val icon = boxContent.selectFirst("img.NewsHeadlineIcon") ?: throw ParsingException("News icon not found.")
        return news {
            id = newsId
            title = titleContainer.cleanText()
            category = NewsCategory.fromIcon(icon.attr("src"))
                ?: throw ParsingException("Unexpected news icon found: ${icon.attr("src")}")

            val newsDate = boxContent.selectFirst("div.NewsHeadlineDate")?.cleanText()?.remove("-")?.trim()
            publishedOn = parseTibiaDate(newsDate ?: throw ParsingException("News date not found."))

            this.content = boxContent.selectFirst("td.NewsTableContainer")?.html()
                ?: throw ParsingException("News content not found.")

            boxContent.selectFirst("div.NewsForumLink > a")?.apply {
                threadId = getLinkInformation()?.queryParams?.get("threadid")?.get(0)?.toInt()
            }
        }
    }
}
