@file:UseSerializers(LocalDateSerializer::class)

package com.galarzaa.tibiakt.core.models

import com.galarzaa.tibiakt.core.enums.NewsCategory
import com.galarzaa.tibiakt.core.enums.NewsType
import com.galarzaa.tibiakt.core.utils.LocalDateSerializer
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

fun NewsArchive.Companion.getFormData(
    startDate: LocalDate,
    endDate: LocalDate,
    categories: Set<NewsCategory>? = null,
    types: Set<NewsType>? = null
): List<Pair<String, String>> {
    val data: MutableList<Pair<String, String>> = mutableListOf()
    startDate.apply {
        data.add(Pair("filter_begin_day", dayOfMonth.toString()))
        data.add(Pair("filter_begin_month", monthValue.toString()))
        data.add(Pair("filter_begin_year", year.toString()))
    }
    endDate.apply {
        data.add(Pair("filter_end_day", dayOfMonth.toString()))
        data.add(Pair("filter_end_month", monthValue.toString()))
        data.add(Pair("filter_end_year", year.toString()))
    }
    for (category: NewsCategory in categories ?: NewsCategory.values().toSet()) {
        data.add(Pair(category.filterName, category.value))
    }
    for (type: NewsType in types ?: NewsType.values().toSet()) {
        data.add(Pair(type.filterName, type.filterValue))
    }
    return data
}