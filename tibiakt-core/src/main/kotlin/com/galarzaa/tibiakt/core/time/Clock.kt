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

import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.Duration.Companion.hours

/**
 * The timezone Tibia's servers are based on.
 *
 * Corresponds to CET (+1) and CEST (+2) during daylight saving times.
 */
public val TIBIA_TIMEZONE: TimeZone = TimeZone.of("Europe/Berlin")

/**
 * Current local date and time on Tibia.com.
 */
public fun tibiaNow(clock: Clock = Clock.System): LocalDateTime = clock.now().toLocalDateTime(TIBIA_TIMEZONE)

/**
 * Current in-game day of the week.
 *
 * A new in-game day starts at [server save][SERVER_SAVE_TIME], not at midnight.
 */
public fun tibiaGameWeekDay(clock: Clock = Clock.System): DayOfWeek =
    (clock.now() - SERVER_SAVE_TIME.hour.hours).toLocalDateTime(TIBIA_TIMEZONE).dayOfWeek
