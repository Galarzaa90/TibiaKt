@file:UseSerializers(YearMonthSerializer::class)

package com.galarzaa.tibiakt.core.models.news

import com.galarzaa.tibiakt.core.utils.YearMonthSerializer
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
data class EventsSchedule(
    val yearMonth: YearMonth,
    val entries: List<EventEntry> = emptyList(),
) {
    val url get() = getEventsScheduleUrl(yearMonth)
}

/**
 * Get all the events that are active in a specific day of the month.
 */
fun EventsSchedule.getEventsOn(date: LocalDate): List<EventEntry> {
    return entries.filter {
        (it.startDate ?: LocalDate.MIN) <= date &&
                date <= (it.endDate ?: LocalDate.MAX)
    }
}