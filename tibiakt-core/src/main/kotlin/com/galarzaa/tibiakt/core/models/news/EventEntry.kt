@file:UseSerializers(LocalDateSerializer::class)

package com.galarzaa.tibiakt.core.models.news

import com.galarzaa.tibiakt.core.serializers.LocalDateSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.time.LocalDate
import java.time.Period

/**
 * An event in the [EventsSchedule].
 *
 * @property title The title or name of the event.
 * @property description A brief description of the event.
 * @property startDate The date when the event starts. If null, it means the event started in a previous month and the date is unavailable.
 * @property endDate The date when the event ends. If null, it means the event ends in a following month and the date is unavailable.
 */
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
