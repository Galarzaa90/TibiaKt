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

import com.galarzaa.tibiakt.TestUtilities.FakeClock
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toInstant
import kotlin.time.Instant

class ClockTests : FunSpec({

    context("tibiaNow") {

        test("converts to Berlin local time in CET (+1)") {
            // 2025-03-15T12:34:56Z -> 13:34:56 in CET
            val instant = Instant.parse("2025-03-15T12:34:56Z")
            val clock = FakeClock(instant)

            tibiaNow(clock) shouldBe LocalDateTime(2025, 3, 15, 13, 34, 56)
        }

        test("converts to Berlin local time in CEST (+2)") {
            // 2025-07-01T00:00:00Z -> 02:00:00 in CEST
            val instant = Instant.parse("2025-07-01T00:00:00Z")
            val clock = FakeClock(instant)

            tibiaNow(clock) shouldBe LocalDateTime(2025, 7, 1, 2, 0, 0)
        }
    }

    context("tibiaGameWeekDay") {

        val saveHour = SERVER_SAVE_TIME.hour

        test("before server save uses previous calendar day (CET)") {
            // Pick a Monday in winter time; one minute before save hour is still Sunday game-day.
            val beforeSaveLocal =
                LocalDateTime(2025, 2, 10, saveHour - 1, 59)
            val clock = FakeClock(beforeSaveLocal.toInstant(TIBIA_TIMEZONE))

            tibiaGameWeekDay(clock) shouldBe DayOfWeek.SUNDAY
        }

        test("at server save starts new game day (CET)") {
            val atSaveLocal = LocalDateTime(2025, 2, 10, saveHour, 0)
            val clock = FakeClock(atSaveLocal.toInstant(TIBIA_TIMEZONE))

            tibiaGameWeekDay(clock) shouldBe DayOfWeek.MONDAY
        }

        test("before server save uses previous calendar day (CEST)") {
            // Summer time; ensure DST offset doesn't change the rule.
            // 2025-07-02 is Wednesday; before save is Tuesday game-day.
            val beforeSaveLocal =
                LocalDateTime(2025, 7, 2, saveHour - 1, 59)
            val clock = FakeClock(beforeSaveLocal.toInstant(TIBIA_TIMEZONE))

            tibiaGameWeekDay(clock) shouldBe DayOfWeek.TUESDAY
        }

        test("at server save starts new game day (CEST)") {
            val atSaveLocal = LocalDateTime(2025, 7, 2, saveHour, 0)
            val clock = FakeClock(atSaveLocal.toInstant(TIBIA_TIMEZONE))

            tibiaGameWeekDay(clock) shouldBe DayOfWeek.WEDNESDAY
        }
    }
})
