/*
 * Copyright © 2022 Allan Galarza
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
public class EventsScheduleBuilder : TibiaKtBuilder<EventsSchedule> {
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
            yearMonth = if (::yearMonth.isInitialized) yearMonth else error("yearMonth is required"),
            entries = entries
        )

    public class EventEntryBuilder {
        public var title: String? = null
        public var description: String? = null
        public var startDate: LocalDate? = null
        public var endDate: LocalDate? = null

        public fun build(): EventEntry = EventEntry(
            title ?: error("title is required"),
            description ?: error("description is required"),
            startDate,
            endDate
        )

        override fun equals(other: Any?): Boolean =
            other is EventEntryBuilder && other.title == title && other.description == description

        override fun hashCode(): Int = 31 * (title?.hashCode() ?: 0) + (description?.hashCode() ?: 0)

        override fun toString(): String = title ?: "<undefined title>"


    }
}
