/*
 * Copyright © 2025 Allan Galarza
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

package com.galarzaa.tibiakt.core.net

import com.galarzaa.tibiakt.core.enums.NewsCategory
import com.galarzaa.tibiakt.core.enums.NewsType
import kotlinx.datetime.LocalDate
import kotlinx.datetime.YearMonth
import kotlinx.datetime.number

/**
 * URL to the News Archive.
 */
public fun newsArchiveUrl(): String = tibiaUrl("news", "newsarchive")

/**
 * POST parameters to filter news in the News Archive.
 */
public fun newArchiveFormData(
    startDate: LocalDate,
    endDate: LocalDate,
    categories: Set<NewsCategory>? = null,
    types: Set<NewsType>? = null,
): List<Pair<String, String>> = buildList {
    startDate.apply {
        add("filter_begin_day" to day.toString())
        add("filter_begin_month" to month.number.toString())
        add("filter_begin_year" to year.toString())
    }
    endDate.apply {
        add("filter_end_day" to day.toString())
        add("filter_end_month" to month.number.toString())
        add("filter_end_year" to year.toString())
    }
    for (category: NewsCategory in categories ?: NewsCategory.entries.toSet()) {
        add(category.filterName to category.value)
    }
    for (type: NewsType in types ?: NewsType.entries.toSet()) {
        add(type.filterName to type.filterValue)
    }
}

/**
 * URL to a specific news article.
 */
public fun newsUrl(newsId: Int): String = tibiaUrl("news", "newsarchive", "id" to newsId)

/**
 * URL to the events schedule.
 *
 * @param yearMonth The specific year and month to show the schedule for.
 *
 * Note that going past the allowed limits, will take you the current month and year.
 */
public fun eventScheduleUrl(yearMonth: YearMonth? = null): String {
    return tibiaUrl(
        "news",
        "eventcalendar",
        *yearMonth?.let { arrayOf("calendarmonth" to it.month.number, "calendaryear" to it.year) }.orEmpty()
    )
}
