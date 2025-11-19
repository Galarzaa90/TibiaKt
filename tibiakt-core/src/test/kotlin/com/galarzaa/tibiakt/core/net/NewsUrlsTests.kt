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

import com.galarzaa.tibiakt.core.section.news.shared.model.NewsCategory
import com.galarzaa.tibiakt.core.section.news.shared.model.NewsType
import com.galarzaa.tibiakt.core.section.news.urls.eventScheduleUrl
import com.galarzaa.tibiakt.core.section.news.urls.newArchiveFormData
import com.galarzaa.tibiakt.core.section.news.urls.newsArchiveUrl
import com.galarzaa.tibiakt.core.section.news.urls.newsUrl
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.collections.shouldNotContainAll
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.string.shouldNotContain
import kotlinx.datetime.LocalDate
import kotlinx.datetime.YearMonth

class NewsUrlsTests : FunSpec({

    test("newsArchiveUrl") {
        val url = newsArchiveUrl()

        url shouldContain "/news"
        url shouldContain "subtopic=newsarchive"
    }
    context("newArchiveFormData") {
        test("with explicit categories and types") {
            val start = LocalDate(2025, 1, 2)
            val end = LocalDate(2025, 1, 31)

            // Pick a subset dynamically so the test doesn’t depend on specific enum constants.
            val pickedCategories = NewsCategory.entries.take(2).toSet()
            val pickedTypes = NewsType.entries.take(2).toSet()

            val form = newArchiveFormData(start, end, categories = pickedCategories, types = pickedTypes)

            // Date filters
            form shouldContainAll listOf(
                "filter_begin_day" to "2",
                "filter_begin_month" to "1",
                "filter_begin_year" to "2025",
                "filter_end_day" to "31",
                "filter_end_month" to "1",
                "filter_end_year" to "2025",
            )

            // Must include selected categories
            val expectedSelectedCategories = pickedCategories.map { it.filterName to it.value }
            form shouldContainAll expectedSelectedCategories

            // Must include selected types
            val expectedSelectedTypes = pickedTypes.map { it.filterName to it.filterValue }
            form shouldContainAll expectedSelectedTypes

            // Must not include the rest
            val excludedCategories = (NewsCategory.entries.toSet() - pickedCategories).map { it.filterName to it.value }
            val excludedTypes = (NewsType.entries.toSet() - pickedTypes).map { it.filterName to it.filterValue }
            form shouldNotContainAll excludedCategories
            form shouldNotContainAll excludedTypes
        }

        test("with null categories and types includes all") {
            val start = LocalDate(2024, 12, 25)
            val end = LocalDate(2025, 1, 5)

            val form = newArchiveFormData(start, end)

            // All categories present
            val allCategories = NewsCategory.entries.map { it.filterName to it.value }
            form shouldContainAll allCategories

            // All types present
            val allTypes = NewsType.entries.map { it.filterName to it.filterValue }
            form shouldContainAll allTypes
        }
    }

    test("newsUrl") {
        val url = newsUrl(123_456)

        url shouldContain "/news"
        url shouldContain "subtopic=newsarchive"
        url shouldContain "id=123456"
    }

    context("eventScheduleUrl") {


        test("with no yearMonth") {
            val url = eventScheduleUrl()

            url shouldContain "/news"
            url shouldContain "subtopic=eventcalendar"
            url shouldNotContain "calendarmonth="
            url shouldNotContain "calendaryear="
        }

        test("with YearMonth") {
            val ym = YearMonth(2025, 10)
            val url = eventScheduleUrl(ym)

            url shouldContain "/news"
            url shouldContain "subtopic=eventcalendar"
            url shouldContain "calendarmonth=10"
            url shouldContain "calendaryear=2025"
        }
    }
})
