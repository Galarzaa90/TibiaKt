@file:UseSerializers(YearMonthSerializer::class)

package com.galarzaa.tibiakt.core.models

import com.galarzaa.tibiakt.core.utils.YearMonthSerializer
import com.galarzaa.tibiakt.core.utils.getEventsScheduleUrl
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.time.LocalDate
import java.time.YearMonth

@Serializable
data class EventsSchedule(
    val yearMonth: YearMonth,
    val entries: List<EventEntry> = emptyList(),
) {
    val url get() = getEventsScheduleUrl(yearMonth)
}

fun EventsSchedule.getEventsOn(date: LocalDate): List<EventEntry> {
    return entries.filter {
        (it.startDate ?: LocalDate.MIN) <= date &&
                date <= (it.endDate ?: LocalDate.MAX)
    }
}