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

package com.galarzaa.tibiakt.core.section.news.event.builder

import com.galarzaa.tibiakt.core.builder.BuilderDsl
import com.galarzaa.tibiakt.core.builder.TibiaKtBuilder
import com.galarzaa.tibiakt.core.builder.requireField
import com.galarzaa.tibiakt.core.section.news.event.model.BaseEventEntry
import com.galarzaa.tibiakt.core.section.news.event.model.EventsSchedule
import kotlinx.datetime.LocalDate
import kotlinx.datetime.YearMonth

@BuilderDsl
internal inline fun eventsSchedule(block: EventsScheduleBuilder.() -> Unit): EventsSchedule =
    EventsScheduleBuilder().apply(block).build()

@BuilderDsl
internal inline fun eventsScheduleBuilder(block: EventsScheduleBuilder.() -> Unit): EventsScheduleBuilder =
    EventsScheduleBuilder().apply(block)

@BuilderDsl
internal inline fun eventEntry(block: EventsScheduleBuilder.EventEntryBuilder.() -> Unit): BaseEventEntry =
    EventsScheduleBuilder.EventEntryBuilder().apply(block).build()

@BuilderDsl
internal inline fun eventEntryBuilder(block: EventsScheduleBuilder.EventEntryBuilder.() -> Unit) =
    EventsScheduleBuilder.EventEntryBuilder().apply(block)

/** Builder for [EventsSchedule] instances. */
@BuilderDsl
internal class EventsScheduleBuilder : TibiaKtBuilder<EventsSchedule> {
    lateinit var yearMonth: YearMonth
    val entries: MutableList<BaseEventEntry> = mutableListOf()


    fun addEntry(
        title: String,
        description: String,
        startsOn: LocalDate?,
        endsOn: LocalDate?,
    ): EventsScheduleBuilder = apply {
        entries.add(
            BaseEventEntry.of(title, description, startsOn, endsOn)
        )
    }

    fun addEntry(entry: BaseEventEntry): EventsScheduleBuilder = apply { entries.add(entry) }

    override fun build(): EventsSchedule = EventsSchedule(
        month = requireField(::yearMonth.isInitialized, "yearMonth") { yearMonth },
        entries = entries
    )

    internal class EventEntryBuilder {
        var title: String? = null
        var description: String? = null
        var startsOn: LocalDate? = null
        var endsOn: LocalDate? = null

        fun build(): BaseEventEntry = BaseEventEntry.of(
            requireField(title, "title"),
            requireField(description, "description"),
            startsOn,
            endsOn
        )

        override fun equals(other: Any?): Boolean =
            other is EventEntryBuilder && other.title == title && other.description == description

        override fun hashCode(): Int = 31 * (title?.hashCode() ?: 0) + (description?.hashCode() ?: 0)

        override fun toString(): String = title ?: "<undefined title>"


    }
}
