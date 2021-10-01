@file:UseSerializers(LocalDateSerializer::class)

package com.galarzaa.tibiakt.models

import com.galarzaa.tibiakt.core.LocalDateSerializer
import com.galarzaa.tibiakt.enums.NewsCategory
import com.galarzaa.tibiakt.enums.NewsType
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.time.LocalDate

@Serializable
data class NewsArchive(
    val startDate: LocalDate,
    val endDate: LocalDate,
    val types: Set<NewsType> = emptySet(),
    val categories: Set<NewsCategory> = emptySet(),
    val entries: List<NewsEntry> = emptyList(),
)