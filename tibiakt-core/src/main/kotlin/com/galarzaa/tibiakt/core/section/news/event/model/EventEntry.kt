/*
 * Copyright © 2025 Allan Galarza
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

package com.galarzaa.tibiakt.core.section.news.event.model

import com.galarzaa.tibiakt.core.time.SERVER_SAVE_TIME
import com.galarzaa.tibiakt.core.time.TIBIA_TIMEZONE
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.atTime
import kotlinx.datetime.minus
import kotlinx.datetime.toInstant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Instant

/**
 * Entry type for events that can appear in an [EventsSchedule].
 *
 * @property title The title or name of the event.
 * @property description Brief description of the event.
 * @property startsOn Start date of the event, or null if the start is open/unknown.
 * @property endsOn End date of the event, or null if the end is open/unknown.
 */
@Serializable
public sealed class EventEntry {
    public abstract val title: String
    public abstract val description: String
    public abstract val startsOn: LocalDate?
    public abstract val endsOn: LocalDate?

    /** The exact moment when the event starts. Based on the event's start date and server save time. */
    public abstract val startsAt: Instant?

    /** The exact moment when the event starts. Based on the event's start date and server save time. */
    public abstract val endsAt: Instant?

    /**
     * Event with both start and end moments known.
     *
     * @property startsOn The date when the event starts.
     * @property endsOn The date when the event ends.
     */
    @Serializable
    @SerialName("bounded")
    public data class Bounded(
        override val title: String,
        override val description: String,
        override val startsOn: LocalDate,
        override val endsOn: LocalDate,
    ) : EventEntry() {

        public override val startsAt: Instant get() = startsOn.atTime(SERVER_SAVE_TIME).toInstant(TIBIA_TIMEZONE)
        public override val endsAt: Instant get() = endsOn.atTime(SERVER_SAVE_TIME).toInstant(TIBIA_TIMEZONE)

        /**
         * The duration of the event, calculated from [startsOn] to [endsOn].
         */
        val duration: DatePeriod
            get() = (endsOn - startsOn)
    }

    /**
     * Event with an unknown start date.
     *
     * Typically, an event that started in the previous month.
     *
     * @property endsOn The date when the event ends.
     */
    @Serializable
    @SerialName("openStart")
    public data class OpenStart(
        override val title: String,
        override val description: String,
        override val endsOn: LocalDate,
    ) : EventEntry() {
        override val startsOn: LocalDate? get() = null
        public override val startsAt: Instant? get() = null
        public override val endsAt: Instant get() = endsOn.atTime(SERVER_SAVE_TIME).toInstant(TIBIA_TIMEZONE)
    }

    /**
     * Event with an unknown end date.
     *
     * Typically, an event that ends in the next month.
     *
     * @property startsOn The date when the event starts.
     */
    @Serializable
    @SerialName("openEnd")
    public data class OpenEnd(
        override val title: String,
        override val description: String,
        override val startsOn: LocalDate,
    ) : EventEntry() {
        override val endsOn: LocalDate? get() = null
        public override val startsAt: Instant get() = startsOn.atTime(SERVER_SAVE_TIME).toInstant(TIBIA_TIMEZONE)
        public override val endsAt: Instant? get() = null
    }

    public companion object {
        /**
         * Create an instance of one of the subclasses of [EventEntry] depending on the provided values.
         */
        public fun of(
            title: String,
            description: String,
            startsOn: LocalDate?,
            endsOn: LocalDate?,
        ): EventEntry {
            require(startsOn != null || endsOn != null) {
                "At least one of startsOn or endsOn must be non-null."
            }
            if (startsOn != null && endsOn != null) {
                require(startsOn <= endsOn) { "startsOn must be <= endsOn." }
                return Bounded(title, description, startsOn, endsOn)
            }
            return if (startsOn != null) OpenEnd(title, description, startsOn)
            else OpenStart(title, description, endsOn!!)
        }
    }
}
