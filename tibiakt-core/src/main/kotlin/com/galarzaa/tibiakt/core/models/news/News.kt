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


package com.galarzaa.tibiakt.core.models.news

import com.galarzaa.tibiakt.core.enums.NewsCategory
import com.galarzaa.tibiakt.core.net.forumThreadUrl
import kotlinx.serialization.Serializable
import kotlinx.datetime.LocalDate

/**
 * A news article, featured article or news ticker.
 *
 * @property id The internal ID of the entry.
 * @property title The title of the news.
 * @property category The category of the entry.
 * @property publishedOn The date when this entry was published.
 * @property content The HTML content of the article.
 * @property threadId The discussion thread specific for this entry, if any.
 */
@Serializable
public data class News(
    override val id: Int,
    val title: String,
    override val category: NewsCategory,
    val publishedOn: LocalDate,
    val content: String,
    val threadId: Int?,
) : BaseNews {

    /**
     * The URL to the discussion thread of this article, if any.
     */
    val threadUrl: String? get() = threadId?.let { forumThreadUrl(threadId) }
}
