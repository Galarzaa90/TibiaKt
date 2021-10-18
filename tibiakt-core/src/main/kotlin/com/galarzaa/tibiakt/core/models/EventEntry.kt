@file:UseSerializers(LocalDateSerializer::class)

package com.galarzaa.tibiakt.core.models

import com.galarzaa.tibiakt.core.utils.LocalDateSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.time.LocalDate
import java.time.Period

@Serializable
data class EventEntry(
    val title: String,
    val description: String,
    val startDate: LocalDate? = null,
    val endDate: LocalDate? = null,
) {
    val duration: Period?
        get() = if (startDate != null && endDate != null) Period.between(startDate, endDate) else null
}
