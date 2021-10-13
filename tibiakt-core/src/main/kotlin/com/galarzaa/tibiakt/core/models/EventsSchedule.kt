@file:UseSerializers(YearMonthSerializer::class)

package com.galarzaa.tibiakt.core.models

import com.galarzaa.tibiakt.core.utils.YearMonthSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.time.YearMonth

@Serializable
data class EventsSchedule(
    val yearMonth: YearMonth,
    val entries: List<EventEntry> = emptyList()
)

