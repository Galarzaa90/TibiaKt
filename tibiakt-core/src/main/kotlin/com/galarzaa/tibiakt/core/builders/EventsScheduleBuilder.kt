package com.galarzaa.tibiakt.core.builders

import com.galarzaa.tibiakt.core.models.news.EventEntry
import com.galarzaa.tibiakt.core.models.news.EventsSchedule
import com.galarzaa.tibiakt.core.utils.BuilderDsl
import java.time.LocalDate
import java.time.YearMonth

@BuilderDsl
inline fun eventsSchedule(block: EventsScheduleBuilder.() -> Unit) = EventsScheduleBuilder().apply(block).build()

@BuilderDsl
inline fun eventsScheduleBuilder(block: EventsScheduleBuilder.() -> Unit) = EventsScheduleBuilder().apply(block)

@BuilderDsl
class EventsScheduleBuilder {
    lateinit var yearMonth: YearMonth
    val entries: MutableList<EventEntry> = mutableListOf()


    fun addEntry(title: String, description: String, startDate: LocalDate?, endDate: LocalDate?) =
        apply { entries.add(EventEntry(title, description, startDate, endDate)) }

    fun addEntry(entry: EventEntry) = apply { entries.add(entry) }

    fun build() =
        EventsSchedule(yearMonth = if (::yearMonth.isInitialized) yearMonth else throw IllegalStateException("yearMonth is required"),
            entries = entries)
}