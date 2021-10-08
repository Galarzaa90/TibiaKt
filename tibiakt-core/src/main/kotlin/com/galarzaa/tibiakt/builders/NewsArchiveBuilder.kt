package com.galarzaa.tibiakt.builders

import com.galarzaa.tibiakt.enums.NewsCategory
import com.galarzaa.tibiakt.enums.NewsType
import com.galarzaa.tibiakt.models.NewsArchive
import com.galarzaa.tibiakt.models.NewsEntry
import java.time.LocalDate

class NewsArchiveBuilder {
    private var startDate: LocalDate? = null
    private var endDate: LocalDate? = null
    private val types: MutableSet<NewsType> = mutableSetOf()
    private val categories: MutableSet<NewsCategory> = mutableSetOf()
    private val entries: MutableList<NewsEntry> = mutableListOf()

    fun startDate(startDate: LocalDate) = apply { this.startDate = startDate }
    fun endDate(endDate: LocalDate) = apply { this.endDate = endDate }
    fun addType(type: NewsType) = apply { types.add(type) }
    fun addCategory(category: NewsCategory) = apply { categories.add(category) }
    fun addEntry(
        id: Int,
        title: String,
        category: NewsCategory,
        categoryIcon: String,
        date: LocalDate,
        type: NewsType
    ) = apply {
        entries.add(
            NewsEntry(
                id, title, category, categoryIcon, date, type
            )
        )
    }

    fun build() = NewsArchive(
        startDate = startDate ?: throw IllegalStateException("startDate is required"),
        endDate = endDate ?: throw IllegalStateException("endDate is required"),
        types = types,
        categories = categories,
        entries = entries,
    )
}