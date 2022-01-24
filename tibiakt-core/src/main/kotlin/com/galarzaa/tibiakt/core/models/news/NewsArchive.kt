@file:UseSerializers(LocalDateSerializer::class)

package com.galarzaa.tibiakt.core.models.news

import com.galarzaa.tibiakt.core.enums.NewsCategory
import com.galarzaa.tibiakt.core.enums.NewsType
import com.galarzaa.tibiakt.core.serializers.LocalDateSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.time.LocalDate

/**
 * The News Archive section in Tibia.com
 *
 * @property startDate The start date of the articles displayed.
 * @property endDate The end date of the articles displayed.
 * @property types The types of articles to show.
 * @property categories The categories of articles to show.
 * @property entries The entries matching the filters.
 */
@Serializable
data class NewsArchive(
    val startDate: LocalDate,
    val endDate: LocalDate,
    val types: Set<NewsType> = emptySet(),
    val categories: Set<NewsCategory> = emptySet(),
    val entries: List<NewsEntry> = emptyList(),
)