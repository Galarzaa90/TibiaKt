package com.galarzaa.tibiakt.core.builders

import com.galarzaa.tibiakt.core.models.news.EventEntry
import com.galarzaa.tibiakt.core.models.news.EventsSchedule
import com.galarzaa.tibiakt.core.utils.BuilderDsl
import java.time.LocalDate
import java.time.YearMonth

@BuilderDsl
public inline fun eventsSchedule(block: EventsScheduleBuilder.() -> Unit): EventsSchedule =
    EventsScheduleBuilder().apply(block).build()

@BuilderDsl
public inline fun eventsScheduleBuilder(block: EventsScheduleBuilder.() -> Unit): EventsScheduleBuilder =
    EventsScheduleBuilder().apply(block)

@BuilderDsl
internal inline fun eventEntry(block: EventsScheduleBuilder.EventEntryBuilder.() -> Unit): EventEntry =
    EventsScheduleBuilder.EventEntryBuilder()
        .apply(block).build()

@BuilderDsl
internal inline fun eventEntryBuilder(block: EventsScheduleBuilder.EventEntryBuilder.() -> Unit) =
    EventsScheduleBuilder.EventEntryBuilder().apply(block)

@BuilderDsl
public class EventsScheduleBuilder : TibiaKtBuilder<EventsSchedule>() {
    public lateinit var yearMonth: YearMonth
    public val entries: MutableList<EventEntry> = mutableListOf()


    public fun addEntry(
        title: String,
        description: String,
        startDate: LocalDate?,
        endDate: LocalDate?,
    ): EventsScheduleBuilder =
        apply { entries.add(EventEntry(title, description, startDate, endDate)) }

    public fun addEntry(entry: EventEntry): EventsScheduleBuilder = apply { entries.add(entry) }

    override fun build(): EventsSchedule =
        EventsSchedule(
            yearMonth = if (::yearMonth.isInitialized) yearMonth else throw IllegalStateException("yearMonth is required"),
            entries = entries
        )

    public class EventEntryBuilder {
        public var title: String? = null
        public var description: String? = null
        public var startDate: LocalDate? = null
        public var endDate: LocalDate? = null

        public fun build(): EventEntry = EventEntry(
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
