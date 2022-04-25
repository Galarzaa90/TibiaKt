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
internal inline fun eventEntry(block: EventsScheduleBuilder.EventEntryBuilder.() -> Unit) =
    EventsScheduleBuilder.EventEntryBuilder()
        .apply(block).build()

@BuilderDsl
internal inline fun eventEntryBuilder(block: EventsScheduleBuilder.EventEntryBuilder.() -> Unit) =
    EventsScheduleBuilder.EventEntryBuilder().apply(block)

@BuilderDsl
class EventsScheduleBuilder : TibiaKtBuilder<EventsSchedule>() {
    lateinit var yearMonth: YearMonth
    val entries: MutableList<EventEntry> = mutableListOf()


    fun addEntry(title: String, description: String, startDate: LocalDate?, endDate: LocalDate?) =
        apply { entries.add(EventEntry(title, description, startDate, endDate)) }

    fun addEntry(entry: EventEntry) = apply { entries.add(entry) }

    override fun build() =
        EventsSchedule(yearMonth = if (::yearMonth.isInitialized) yearMonth else throw IllegalStateException("yearMonth is required"),
            entries = entries)

    class EventEntryBuilder {
        var title: String? = null
        var description: String? = null
        var startDate: LocalDate? = null
        var endDate: LocalDate? = null

        fun build() = EventEntry(
            title ?: throw IllegalStateException("title is required"),
            description ?: throw IllegalStateException("description is required"),
            startDate,
            endDate
        )

        override fun equals(other: Any?): Boolean {
            return other is EventEntryBuilder && other.title == title && other.description == description
        }

        override fun toString(): String {
            return title ?: "<undefined title>"
        }
    }
}