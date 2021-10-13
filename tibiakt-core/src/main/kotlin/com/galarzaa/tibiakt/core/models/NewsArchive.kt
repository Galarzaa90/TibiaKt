@file:UseSerializers(LocalDateSerializer::class)

package com.galarzaa.tibiakt.core.models

import com.galarzaa.tibiakt.core.enums.NewsCategory
import com.galarzaa.tibiakt.core.enums.NewsType
import com.galarzaa.tibiakt.core.utils.LocalDateSerializer
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