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

package com.galarzaa.tibiakt.core.utils

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.date.shouldBeBefore
import io.kotest.matchers.ints.shouldBeLessThan
import io.kotest.matchers.shouldBe
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime

class DateTests : FunSpec({
    val testDate = LocalDate.of(2020, 1, 20)
    val arizonaZone = ZoneId.of("US/Arizona")

    context("getLastServerSaveTime") {
        test("Time after server save hour") {
            val time = ZonedDateTime.of(testDate, LocalTime.of(11, 0), TIBIA_TIMEZONE)
            val serverSaveTime = getLastServerSaveTime(time)
            serverSaveTime shouldBeBefore time
            serverSaveTime.dayOfMonth shouldBe time.dayOfMonth
        }
        test("Time after server hour in another timezone") {
            val time = ZonedDateTime.of(testDate, LocalTime.of(12, 0), arizonaZone)
            val serverSaveTime = getLastServerSaveTime(time)
            val convertedTime = serverSaveTime.withZoneSameInstant(arizonaZone)
            serverSaveTime shouldBeBefore time
            serverSaveTime.dayOfMonth shouldBe time.dayOfMonth
            convertedTime.hour shouldBe 2
        }
        test("Time before server save hour") {
            val time = ZonedDateTime.of(testDate, LocalTime.of(7, 0), TIBIA_TIMEZONE)
            val serverSaveTime = getLastServerSaveTime(time)
            serverSaveTime shouldBeBefore time
            serverSaveTime.dayOfMonth shouldBeLessThan time.dayOfMonth
        }
        test("Localizing time considers DST") {
            val serverSaveTime =
                getLastServerSaveTime(ZonedDateTime.of(LocalDate.of(2020, 4, 1), LocalTime.of(4, 0), TIBIA_TIMEZONE))
            val convertedTime = serverSaveTime.withZoneSameInstant(arizonaZone)
            convertedTime.hour shouldBe 1
        }
    }
})
