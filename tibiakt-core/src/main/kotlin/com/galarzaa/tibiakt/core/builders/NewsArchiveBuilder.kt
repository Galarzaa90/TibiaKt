/*
 * Copyright © 2024 Allan Galarza
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

package com.galarzaa.tibiakt.core.builders

import com.galarzaa.tibiakt.core.enums.NewsCategory
import com.galarzaa.tibiakt.core.enums.NewsType
import com.galarzaa.tibiakt.core.models.news.NewsArchive
import com.galarzaa.tibiakt.core.models.news.NewsEntry
import kotlinx.datetime.LocalDate

@BuilderDsl
public fun newsArchive(block: NewsArchiveBuilder.() -> Unit): NewsArchive = newsArchiveBuilder(block).build()

@BuilderDsl
public fun newsArchiveBuilder(block: NewsArchiveBuilder.() -> Unit): NewsArchiveBuilder =
    NewsArchiveBuilder().apply(block)

/** Builder for [NewsArchive] instances. */
@BuilderDsl
public class NewsArchiveBuilder : TibiaKtBuilder<NewsArchive> {
    public var startOn: LocalDate? = null
    public var endOn: LocalDate? = null
    public val types: MutableSet<NewsType> = mutableSetOf()
    public val categories: MutableSet<NewsCategory> = mutableSetOf()
    public val entries: MutableList<NewsEntry> = mutableListOf()
    public fun addCategory(category: NewsCategory): NewsArchiveBuilder = apply { categories.add(category) }

    public fun addType(type: NewsType): NewsArchiveBuilder = apply { types.add(type) }

    public fun addEntry(
        id: Int,
        title: String,
        category: NewsCategory,
        date: LocalDate,
        type: NewsType,
    ): NewsArchiveBuilder = apply {
        entries.add(NewsEntry(id, title, category, date, type))
    }

    public fun addEntry(builder: NewsEntryBuilder.() -> Unit): NewsArchiveBuilder = apply {
        entries.add(NewsEntryBuilder().apply(builder).build())
    }


    override fun build(): NewsArchive = NewsArchive(
        startOn = startOn ?: error("startOn is required"),
        endOn = endOn ?: error("endOn is required"),
        types = types,
        categories = categories,
        entries = entries,
    )

    public class NewsEntryBuilder : TibiaKtBuilder<NewsEntry> {
        public var id: Int = 0
        public var title: String? = null
        public var category: NewsCategory? = null
        public var publishedOn: LocalDate? = null
        public var type: NewsType? = null

        override fun build(): NewsEntry = NewsEntry(
            id = id,
            title = title ?: error("title is required"),
            category = category ?: error("category is required"),
            publishedOn = publishedOn ?: error("date is required"),
            type = type ?: error("type is required"),
        )
    }
}
