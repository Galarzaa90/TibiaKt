package com.galarzaa.tibiakt.core.utils

import com.galarzaa.tibiakt.core.models.EventEntry
import com.galarzaa.tibiakt.core.models.EventsSchedule
import java.time.LocalDate
import java.time.Period

val EventsSchedule.url get() = getEventsScheduleUrl(yearMonth)

fun EventsSchedule.getEventsOn(date: LocalDate): List<EventEntry> {
    return entries.filter {
        (it.startDate ?: LocalDate.MIN) <= date &&
                date <= (it.endDate ?: LocalDate.MAX)
    }
}

val EventEntry.duration: Period?
    get() = if (startDate != null && endDate != null) Period.between(startDate, endDate) else null