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

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.date.shouldBeBefore
import io.kotest.matchers.ints.shouldBeLessThan
import io.kotest.matchers.shouldBe
import kotlinx.datetime.*
import kotlin.time.toJavaInstant

class ServerSaveTests : FunSpec({

    val testDate = LocalDate(2020, 1, 20)
    val arizonaZone = TimeZone.of("US/Arizona")

    context("lastServerSave") {

        test("Time after server save hour") {
            val time = LocalDateTime(testDate.year, testDate.monthNumber, testDate.day, 11, 0)
                .toInstant(TIBIA_TIMEZONE)
            val serverSaveTime = time.lastServerSave()
            serverSaveTime.toJavaInstant() shouldBeBefore time.toJavaInstant()
            serverSaveTime.toLocalDateTime(TIBIA_TIMEZONE).day shouldBe
                time.toLocalDateTime(TIBIA_TIMEZONE).day
        }

        test("Time after server hour in another timezone") {
            val time = LocalDateTime(testDate.year, testDate.monthNumber, testDate.day, 12, 0)
                .toInstant(arizonaZone)
            val serverSaveTime = time.lastServerSave()
            val convertedTime = serverSaveTime.toLocalDateTime(arizonaZone)

            serverSaveTime.toJavaInstant() shouldBeBefore time.toJavaInstant()
            serverSaveTime.toLocalDateTime(TIBIA_TIMEZONE).day shouldBe
                time.toLocalDateTime(TIBIA_TIMEZONE).day
            convertedTime.hour shouldBe 2
        }

        test("Time before server save hour") {
            val time = LocalDateTime(testDate.year, testDate.monthNumber, testDate.day, 7, 0)
                .toInstant(TIBIA_TIMEZONE)
            val serverSaveTime = time.lastServerSave()

            serverSaveTime.toJavaInstant() shouldBeBefore time.toJavaInstant()
            serverSaveTime.toLocalDateTime(TIBIA_TIMEZONE).day shouldBeLessThan
                time.toLocalDateTime(TIBIA_TIMEZONE).day
        }

        test("Localizing time considers DST") {
            val time = LocalDateTime(2020, 4, 1, 4, 0).toInstant(TIBIA_TIMEZONE)
            val serverSaveTime = time.lastServerSave()
            val convertedTime = serverSaveTime.toLocalDateTime(arizonaZone)

            convertedTime.hour shouldBe 1
        }
    }
})
