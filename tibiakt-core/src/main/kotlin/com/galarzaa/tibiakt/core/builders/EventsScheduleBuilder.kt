package com.galarzaa.tibiakt.core.builders

import com.galarzaa.tibiakt.core.models.news.EventEntry
import com.galarzaa.tibiakt.core.models.news.EventsSchedule
import java.time.LocalDate
import java.time.YearMonth

class EventsScheduleBuilder {
    private var yearMonth: YearMonth? = null
    private val entries: MutableList<EventEntry> = mutableListOf()

    fun yearMonth(yearMonth: YearMonth) = apply { this.yearMonth = yearMonth }
    fun yearMonth(year: Int, month: Int) = apply { yearMonth = YearMonth.of(year, month) }
    fun addEntry(title: String, description: String, startDate: LocalDate?, endDate: LocalDate?) =
        apply { entries.add(EventEntry(title, description, startDate, endDate)) }

    fun addEntry(entry: EventEntry) = apply { entries.add(entry) }

    fun build() = EventsSchedule(
        yearMonth ?: throw IllegalStateException("yearMonth is required"),
        entries
    )
}