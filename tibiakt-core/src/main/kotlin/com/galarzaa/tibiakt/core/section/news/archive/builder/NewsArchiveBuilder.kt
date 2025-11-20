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

package com.galarzaa.tibiakt.core.section.news.archive.builder

import com.galarzaa.tibiakt.core.builder.BuilderDsl
import com.galarzaa.tibiakt.core.builder.TibiaKtBuilder
import com.galarzaa.tibiakt.core.builder.requireField
import com.galarzaa.tibiakt.core.section.news.archive.model.NewsArchive
import com.galarzaa.tibiakt.core.section.news.archive.model.NewsArchiveFilters
import com.galarzaa.tibiakt.core.section.news.archive.model.NewsEntry
import com.galarzaa.tibiakt.core.section.news.shared.model.NewsCategory
import com.galarzaa.tibiakt.core.section.news.shared.model.NewsType
import kotlinx.datetime.LocalDate

@BuilderDsl
internal fun newsArchive(block: NewsArchiveBuilder.() -> Unit) = newsArchiveBuilder(block).build()

@BuilderDsl
internal fun newsArchiveBuilder(block: NewsArchiveBuilder.() -> Unit) =
    NewsArchiveBuilder().apply(block)

/** Builder for [NewsArchive] instances. */
@BuilderDsl
internal class NewsArchiveBuilder : TibiaKtBuilder<NewsArchive> {
    var filters: NewsArchiveFilters? = null
    val entries: MutableList<NewsEntry> = mutableListOf()


    fun addEntry(
        id: Int,
        title: String,
        category: NewsCategory,
        date: LocalDate,
        type: NewsType,
    ) = apply {
        entries.add(NewsEntry(id, title, category, date, type))
    }

    fun addEntry(builder: NewsEntryBuilder.() -> Unit) = apply {
        entries.add(NewsEntryBuilder().apply(builder).build())
    }


    override fun build(): NewsArchive = NewsArchive(
        filters = requireField(filters, "filters"),
        entries = entries,
    )

    class NewsEntryBuilder : TibiaKtBuilder<NewsEntry> {
        var id: Int = 0
        var title: String? = null
        var category: NewsCategory? = null
        var publishedOn: LocalDate? = null
        var type: NewsType? = null


        override fun build() = NewsEntry(
            id = id,
            title = requireField(title, "title"),
            category = requireField(category, "category"),
            publishedOn = requireField(publishedOn, "publishedOn"),
            type = requireField(type, "type"),
        )
    }

    class NewsArchiveFiltersBuilder : TibiaKtBuilder<NewsArchiveFilters> {
        var startOn: LocalDate? = null
        var endOn: LocalDate? = null
        val types: MutableSet<NewsType> = mutableSetOf()
        val categories: MutableSet<NewsCategory> = mutableSetOf()

        fun addCategory(category: NewsCategory) = apply { categories.add(category) }

        fun addType(type: NewsType) = apply { types.add(type) }

        override fun build() = NewsArchiveFilters(
            startOn = requireField(startOn, "startOn"),
            endOn = requireField(endOn, "endOn"),
            types = types,
            categories = categories,
        )
    }
}
