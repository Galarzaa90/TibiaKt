package com.galarzaa.tibiakt.core.builders

import com.galarzaa.tibiakt.core.enums.NewsCategory
import com.galarzaa.tibiakt.core.enums.NewsType
import com.galarzaa.tibiakt.core.models.news.NewsArchive
import com.galarzaa.tibiakt.core.models.news.NewsEntry
import java.time.LocalDate

fun newsArchive(block: NewsArchiveBuilder.() -> Unit) = newsArchiveBuilder(block).build()
fun newsArchiveBuilder(block: NewsArchiveBuilder.() -> Unit) = NewsArchiveBuilder().apply(block)

class NewsArchiveBuilder {
    var startDate: LocalDate? = null
    var endDate: LocalDate? = null
    val types: MutableSet<NewsType> = mutableSetOf()
    val categories: MutableSet<NewsCategory> = mutableSetOf()
    val entries: MutableList<NewsEntry> = mutableListOf()

    fun addCategory(category: NewsCategory) = apply { categories.add(category) }

    fun addType(type: NewsType) = apply { types.add(type) }

    fun addEntry(
        id: Int,
        title: String,
        category: NewsCategory,
        categoryIcon: String,
        date: LocalDate,
        type: NewsType,
    ) = apply {
        entries.add(NewsEntry(id, title, category, categoryIcon, date, type)

        )
    }

    fun addEntry(builder: NewsEntryBuilder.() -> Unit) = apply {
        entries.add(NewsEntryBuilder().apply(builder).build())
    }


    fun build() = NewsArchive(
        startDate = startDate ?: throw IllegalStateException("startDate is required"),
        endDate = endDate ?: throw IllegalStateException("endDate is required"),
        types = types,
        categories = categories,
        entries = entries,
    )
}

class NewsEntryBuilder {
    val id: Int = 0
    val title: String? = null
    val category: NewsCategory? = null
    val categoryIcon: String? = null
    val date: LocalDate? = null
    val type: NewsType? = null

    fun build() = NewsEntry(
        id = id,
        title = title ?: throw IllegalStateException("title is required"),
        category = category ?: throw IllegalStateException("category is required"),
        categoryIcon = categoryIcon ?: throw IllegalStateException("categoryIcon is required"),
        date = date ?: throw IllegalStateException("date is required"),
        type = type ?: throw IllegalStateException("type is required"),
    )
}