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

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Duration.Companion.days
import kotlin.time.Instant

/** The local time when server save happens (10:00 CET/CEST). */
public val SERVER_SAVE_TIME: LocalTime = LocalTime(10, 0)

/** Last server save instant relative to this instant. */
public fun Instant.lastServerSave(): Instant =
    LocalDateTime(toLocalDateTime(TIBIA_TIMEZONE).date, SERVER_SAVE_TIME).toInstant(TIBIA_TIMEZONE)
        .let { if (this < it) it - 1.days else it }


/** Next server save instant relative to this instant. */
public fun Instant.nextServerSave(): Instant = lastServerSave() + 1.days
