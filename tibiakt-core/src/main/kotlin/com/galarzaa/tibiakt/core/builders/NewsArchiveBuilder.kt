package com.galarzaa.tibiakt.core.builders

import com.galarzaa.tibiakt.core.enums.NewsCategory
import com.galarzaa.tibiakt.core.enums.NewsType
import com.galarzaa.tibiakt.core.models.news.NewsArchive
import com.galarzaa.tibiakt.core.models.news.NewsEntry
import com.galarzaa.tibiakt.core.utils.BuilderDsl
import java.time.LocalDate

@BuilderDsl
public fun newsArchive(block: NewsArchiveBuilder.() -> Unit): NewsArchive = newsArchiveBuilder(block).build()

@BuilderDsl
public fun newsArchiveBuilder(block: NewsArchiveBuilder.() -> Unit): NewsArchiveBuilder =
    NewsArchiveBuilder().apply(block)

@BuilderDsl
public class NewsArchiveBuilder : TibiaKtBuilder<NewsArchive>() {
    public var startDate: LocalDate? = null
    public var endDate: LocalDate? = null
    public val types: MutableSet<NewsType> = mutableSetOf()
    public val categories: MutableSet<NewsCategory> = mutableSetOf()
    public val entries: MutableList<NewsEntry> = mutableListOf()
    public fun addCategory(category: NewsCategory): NewsArchiveBuilder = apply { categories.add(category) }

    public fun addType(type: NewsType): NewsArchiveBuilder = apply { types.add(type) }

    public fun addEntry(
        id: Int,
        title: String,
        category: NewsCategory,
        categoryIcon: String,
        date: LocalDate,
        type: NewsType,
    ): NewsArchiveBuilder = apply {
        entries.add(NewsEntry(id, title, category, categoryIcon, date, type))
    }

    public fun addEntry(builder: NewsEntryBuilder.() -> Unit): NewsArchiveBuilder = apply {
        entries.add(NewsEntryBuilder().apply(builder).build())
    }


    override fun build(): NewsArchive = NewsArchive(
        startDate = startDate ?: throw IllegalStateException("startDate is required"),
        endDate = endDate ?: throw IllegalStateException("endDate is required"),
        types = types,
        categories = categories,
        entries = entries,
    )

    public class NewsEntryBuilder : TibiaKtBuilder<NewsEntry>() {
        public val id: Int = 0
        public val title: String? = null
        public val category: NewsCategory? = null
        public val categoryIcon: String? = null
        public val date: LocalDate? = null
        public val type: NewsType? = null

        override fun build(): NewsEntry = NewsEntry(
            id = id,
            title = title ?: throw IllegalStateException("title is required"),
            category = category ?: throw IllegalStateException("category is required"),
            categoryIcon = categoryIcon ?: throw IllegalStateException("categoryIcon is required"),
            date = date ?: throw IllegalStateException("date is required"),
            type = type ?: throw IllegalStateException("type is required"),
        )
    }
}
