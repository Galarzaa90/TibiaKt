package com.galarzaa.tibiakt.core.builders

import com.galarzaa.tibiakt.core.models.news.EventEntry
import java.time.LocalDate

class EventEntryBuilder {
    private var title: String? = null
    private var description: String? = null
    private var startDate: LocalDate? = null
    private var endDate: LocalDate? = null

    fun title(title: String) = apply { this.title = title }
    fun description(description: String) = apply { this.description = description }
    fun startDate(startDate: LocalDate?) = apply { this.startDate = startDate }
    fun endDate(endDate: LocalDate?) = apply { this.endDate = endDate }

    fun build() = EventEntry(
        title ?: throw IllegalStateException("title is required"),
        description ?: throw IllegalStateException("description is required"),
        startDate,
        endDate
    )

    override fun equals(other: Any?): Boolean {
        return other is EventEntryBuilder && other.title == title && other.description == description
    }
}