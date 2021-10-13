@file:UseSerializers(LocalDateSerializer::class)

package com.galarzaa.tibiakt.core.models

import com.galarzaa.tibiakt.core.enums.NewsCategory
import com.galarzaa.tibiakt.core.utils.LocalDateSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.time.LocalDate

/**
 * A news article, featured article or news ticker.
 *
 * @property id The internal ID of the entry.
 * @property title The title of the news.
 * @property category The category of the entry.
 * @property categoryIcon The category icon of the entry.
 * @property date The date when this entry was published.
 * @property content The HTML content of the article.
 * @property threadId The discussion thread specific for this entry, if any.
 */
@Serializable
data class News(
    override val id: Int,
    val title: String,
    val category: NewsCategory,
    val categoryIcon: String,
    val date: LocalDate,
    val content: String,
    val threadId: Int?,
) : BaseNews
