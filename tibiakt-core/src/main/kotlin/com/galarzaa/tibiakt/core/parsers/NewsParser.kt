/*
 * Copyright © 2022 Allan Galarza
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

package com.galarzaa.tibiakt.core.parsers

import com.galarzaa.tibiakt.core.builders.news
import com.galarzaa.tibiakt.core.enums.NewsCategory
import com.galarzaa.tibiakt.core.exceptions.ParsingException
import com.galarzaa.tibiakt.core.models.news.News
import com.galarzaa.tibiakt.core.utils.cleanText
import com.galarzaa.tibiakt.core.utils.getLinkInformation
import com.galarzaa.tibiakt.core.utils.parseTibiaDate
import com.galarzaa.tibiakt.core.utils.remove

public object NewsParser : Parser<News?> {
    override fun fromContent(content: String): News? = fromContent(content, 0)

    public fun fromContent(content: String, newsId: Int = 0): News? {
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
            date = parseTibiaDate(newsDate ?: throw ParsingException("News date not found."))

            this.content = boxContent.selectFirst("td.NewsTableContainer")?.html()
                ?: throw ParsingException("News content not found.")

            boxContent.selectFirst("div.NewsForumLink > a")?.apply {
                threadId = getLinkInformation()?.queryParams?.get("threadid")?.get(0)?.toInt()
            }
        }
    }
}
