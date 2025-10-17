/*
 * Copyright © 2024 Allan Galarza
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

package com.galarzaa.tibiakt.core.models.news

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
 * Base type for all entries that can appear in an [EventsSchedule].
 *
 * @property title The title or name of the event.
 * @property description Brief description of the event.
 * @property startsOn Start date of the event, or null if the start is open/unknown.
 * @property endsOn End date of the event, or null if the end is open/unknown.
 */
@Serializable
public sealed class BaseEventEntry {
    public abstract val title: String
    public abstract val description: String
    public abstract val startsOn: LocalDate?
    public abstract val endsOn: LocalDate?

    /** The exact moment when the event starts. Based on the event's start date and server save time. */
    public abstract val startsAt: Instant?

    /** The exact moment when the event starts. Based on the event's start date and server save time. */
    public abstract val endsAt: Instant?

    public companion object {
        /**
         * Create an instance of one of the subclasses of [BaseEventEntry] depending on the provided values.
         */
        public fun of(
            title: String,
            description: String,
            startsOn: LocalDate?,
            endsOn: LocalDate?,
        ): BaseEventEntry {
            require(startsOn != null || endsOn != null) {
                "At least one of startsOn or endsOn must be non-null."
            }
            if (startsOn != null && endsOn != null) {
                require(startsOn <= endsOn) { "startsOn must be ≤ endsOn." }
                return EventEntry(title, description, startsOn, endsOn)
            }
            return if (startsOn != null) EventEntryOpenEnd(title, description, startsOn)
            else EventEntryOpenStart(title, description, endsOn!!)
        }
    }
}

/**
 * Event with both start and end moments known.
 *
 * @property startsOn The date when the event starts.
 * @property endsOn The date when the event ends.
 */
@Serializable
@SerialName("eventEntry")
public data class EventEntry(
    override val title: String,
    override val description: String,
    override val startsOn: LocalDate,
    override val endsOn: LocalDate,
) : BaseEventEntry() {

    public override val startsAt: Instant get() = startsOn.atTime(SERVER_SAVE_TIME).toInstant(TIBIA_TIMEZONE)
    public override val endsAt: Instant get() = endsOn.atTime(SERVER_SAVE_TIME).toInstant(TIBIA_TIMEZONE)

    /**
     * The duration of the event, calculated from [startsOn] to [endsOn].
     */
    val duration: DatePeriod
        get() = (endsOn - startsOn)
}

/**
 * Event with an unknown/open start (typically began earlier and continues until [endsOn]).
 *
 * @property endsOn The date when the event ends.
 */
@Serializable
@SerialName("eventEntryOpenStart")
public data class EventEntryOpenStart(
    override val title: String,
    override val description: String,
    override val endsOn: LocalDate,
) : BaseEventEntry() {
    override val startsOn: LocalDate? get()  = null
    public override val startsAt: Instant? get() = null
    public override val endsAt: Instant get() = endsOn.atTime(SERVER_SAVE_TIME).toInstant(TIBIA_TIMEZONE)
}

/**
 * Event with an unknown/open end (starts at [startsOn] and continues afterward).
 *
 * @property startsOn The date when the event starts.
 */
@Serializable
@SerialName("eventEntryOpenEnd")
public data class EventEntryOpenEnd(
    override val title: String,
    override val description: String,
    override val startsOn: LocalDate,
) : BaseEventEntry() {
    override val endsOn: LocalDate?  get()  = null
    public override val startsAt: Instant get() = startsOn.atTime(SERVER_SAVE_TIME).toInstant(TIBIA_TIMEZONE)
    public override val endsAt: Instant? get() = null
}
