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

package com.galarzaa.tibiakt.core.time

import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Instant

/** The local time when server save happens (10:00 CET/CEST). */
public val SERVER_SAVE_TIME: LocalTime = LocalTime(10, 0)

/** Last server save relative to this moment. */
public fun Instant.lastServerSave(): Instant {
    val local = toLocalDateTime(TIBIA_TIMEZONE)
    val date = if (local.time < SERVER_SAVE_TIME) {
        local.date - DatePeriod(days = 1)
    } else {
        local.date
    }
    return LocalDateTime(date, SERVER_SAVE_TIME).toInstant(TIBIA_TIMEZONE)
}


/** Next server save instant relative to this instant. */
public fun Instant.nextServerSave(): Instant {
    val local = toLocalDateTime(TIBIA_TIMEZONE)
    val date = if (local.time < SERVER_SAVE_TIME) {
        local.date
    } else {
        local.date + DatePeriod(days = 1)
    }
    return LocalDateTime(date, SERVER_SAVE_TIME).toInstant(TIBIA_TIMEZONE)
}
