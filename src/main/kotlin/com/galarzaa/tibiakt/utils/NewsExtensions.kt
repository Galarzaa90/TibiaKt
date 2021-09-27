package com.galarzaa.tibiakt.utils

import com.galarzaa.tibiakt.models.*
import java.time.LocalDate

val BaseNews.url
    get() = getNewsUrl(id)

val News.threadUrl
    get() = if (threadId != null) getThreadUrl(threadId) else null


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