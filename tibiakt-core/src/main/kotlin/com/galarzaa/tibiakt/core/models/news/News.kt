@file:UseSerializers(LocalDateSerializer::class)

package com.galarzaa.tibiakt.core.models.news

import com.galarzaa.tibiakt.core.enums.NewsCategory
import com.galarzaa.tibiakt.core.serializers.LocalDateSerializer
import com.galarzaa.tibiakt.core.utils.getForumThreadUrl
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.time.LocalDate

/**
 * A news article, featured article or news ticker.
 *
 * @property id The internal ID of the entry.
 * @property title The title of the news.
 * @property category The category of the entry.
 * @property date The date when this entry was published.
 * @property content The HTML content of the article.
 * @property threadId The discussion thread specific for this entry, if any.
 */
@Serializable
data class News(
    override val id: Int,
    val title: String,
    override val category: NewsCategory,
    val date: LocalDate,
    val content: String,
    val threadId: Int?,
) : BaseNews {

    /**
     * The URL to the discussion thread of this article, if any.
     */
    val threadUrl: String? get() = threadId?.let { getForumThreadUrl(threadId) }
}