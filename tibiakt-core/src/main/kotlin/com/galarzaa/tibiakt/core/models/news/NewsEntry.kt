@file:UseSerializers(LocalDateSerializer::class)

package com.galarzaa.tibiakt.core.models.news

import com.galarzaa.tibiakt.core.enums.NewsCategory
import com.galarzaa.tibiakt.core.enums.NewsType
import com.galarzaa.tibiakt.core.serializers.LocalDateSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.time.LocalDate

/**
 * A news entry listed in the [NewsArchive]
 *
 * @property title The title of the entry.
 * @property date The date when the entry was published.
 * @property type The type of the entry.
 */
@Serializable
data class NewsEntry(
    override val id: Int,
    val title: String,
    override val category: NewsCategory,
    val categoryIcon: String,
    val date: LocalDate,
    val type: NewsType,
) : BaseNews