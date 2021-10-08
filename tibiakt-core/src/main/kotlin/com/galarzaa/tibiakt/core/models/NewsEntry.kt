@file:UseSerializers(LocalDateSerializer::class)

package com.galarzaa.tibiakt.core.models

import com.galarzaa.tibiakt.core.LocalDateSerializer
import com.galarzaa.tibiakt.core.enums.NewsCategory
import com.galarzaa.tibiakt.core.enums.NewsType
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.time.LocalDate

@Serializable
data class NewsEntry(
    override val id: Int,
    val title: String,
    val category: NewsCategory,
    val categoryIcon: String,
    val date: LocalDate,
    val type: NewsType,
) : BaseNews