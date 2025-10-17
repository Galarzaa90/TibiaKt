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
import io.kotest.matchers.shouldBe
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toInstant
import kotlin.time.Instant

class ParsersTests : FunSpec({

    fun asInstant(local: LocalDateTime): Instant = local.toInstant(TIBIA_TIMEZONE)

    context("parseTibiaDateTime") {
        test("parses 'MMM d yyyy, HH:mm' (CET)") {
            val input = "Jan 10 2025, 10:05"
            parseTibiaDateTime(input) shouldBe asInstant(LocalDateTime(2025, 1, 10, 10, 5))
        }

        test("parses 'MMM d yyyy, HH:mm:ss' (CEST)") {
            val input = "Jul 10 2025, 14:05:33"
            parseTibiaDateTime(input) shouldBe asInstant(LocalDateTime(2025, 7, 10, 14, 5, 33))
        }

        test("ignores trailing ' CEST' suffix") {
            val input = "Jul 10 2025, 14:05 CEST"
            parseTibiaDateTime(input) shouldBe asInstant(LocalDateTime(2025, 7, 10, 14, 5))
        }

        test("ignores trailing ' CET' suffix") {
            val input = "Jan 10 2025, 08:00:59 CET"
            parseTibiaDateTime(input) shouldBe asInstant(LocalDateTime(2025, 1, 10, 8, 0, 59))
        }

        test("trims excess whitespace around content") {
            val input = "   Jan 2 2025, 09:07   "
            parseTibiaDateTime(input) shouldBe asInstant(LocalDateTime(2025, 1, 2, 9, 7))
        }

        test("handles non-breaking spaces") {
            val nbsp = '\u00A0'
            val input = "Jul${nbsp}10${nbsp}2025,${nbsp}14:05:33"
            parseTibiaDateTime(input) shouldBe asInstant(LocalDateTime(2025, 7, 10, 14, 5, 33))
        }
    }

    context("parseTibiaDate") {
        test("parses 'MMM d yyyy'") {
            parseTibiaDate("Aug 12 2025") shouldBe LocalDate(2025, 8, 12)
        }

        test("handles extra whitespace") {
            parseTibiaDate("  Feb 1 2025 ") shouldBe LocalDate(2025, 2, 1)
        }

        test("handles NBSPs") {
            val nbsp = '\u00A0'
            parseTibiaDate("Dec${nbsp}31${nbsp}2025") shouldBe LocalDate(2025, 12, 31)
        }
    }

    context("parseTibiaFullDate") {
        test("parses 'MMMM d, yyyy' single-digit day") {
            parseTibiaFullDate("August 3, 2025") shouldBe LocalDate(2025, 8, 3)
        }

        test("parses 'MMMM d, yyyy' double-digit day") {
            parseTibiaFullDate("December 12, 2025") shouldBe LocalDate(2025, 12, 12)
        }
    }

    context("parseTibiaForumDateTime") {
        test("parses 'dd.MM.yyyy HH:mm:ss' zero-padded") {
            val input = "09.03.2025 07:01:02"
            parseTibiaForumDateTime(input) shouldBe asInstant(LocalDateTime(2025, 3, 9, 7, 1, 2))
        }
    }
})
