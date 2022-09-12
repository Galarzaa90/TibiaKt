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
public data class EventEntry(
    val title: String,
    val description: String,
    val startDate: LocalDate?,
    val endDate: LocalDate?,
) {
    val duration: Period?
        get() = if (startDate != null && endDate != null) Period.between(startDate, endDate) else null
}
