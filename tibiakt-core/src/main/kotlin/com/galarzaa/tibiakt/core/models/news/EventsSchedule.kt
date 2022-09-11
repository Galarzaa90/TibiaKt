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
