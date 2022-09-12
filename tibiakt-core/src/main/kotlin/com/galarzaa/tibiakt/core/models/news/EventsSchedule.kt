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

@file:UseSerializers(YearMonthSerializer::class)

package com.galarzaa.tibiakt.core.models.news

import com.galarzaa.tibiakt.core.serializers.YearMonthSerializer
import com.galarzaa.tibiakt.core.utils.getEventsScheduleUrl
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.time.LocalDate
import java.time.YearMonth

/**
 * The event schedule, containing the events of a given month.
 *
 * @property yearMonth The month and year the schedule is for.
 * @property entries The list of events in this month.
 */
@Serializable
public data class EventsSchedule(
    val yearMonth: YearMonth,
    val entries: List<EventEntry>,
) {
    /**
     * The URL of the events schedule of the month.
     */
    val url: String get() = getEventsScheduleUrl(yearMonth)

    /**
     * Get all the events that are active in a specific day of the month.
     */
    public fun getEventsOn(date: LocalDate): List<EventEntry> {
        return entries.filter {
            (it.startDate ?: LocalDate.MIN) <= date &&
                    date <= (it.endDate ?: LocalDate.MAX)
        }
    }
}
