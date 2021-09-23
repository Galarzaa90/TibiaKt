@file:UseSerializers(YearMonthSerializer::class)

package com.galarzaa.tibiakt.models

import com.galarzaa.tibiakt.YearMonthSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.time.YearMonth

@Serializable
data class EventsSchedule(
    val yearMonth: YearMonth,
    val entries: List<EventEntry> = emptyList()
)

