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

package com.galarzaa.tibiakt.core.parsers

import com.galarzaa.tibiakt.TestResources.getResource
import io.kotest.core.spec.style.FunSpec
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.maps.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class KillStatisticsParserTests : FunSpec({
    test("Kill statistics with results") {
        val killStatistics =
            KillStatisticsParser.fromContent(getResource("killStatistics/killStatisticsWithResults.txt"))
        killStatistics shouldNotBe null
        killStatistics!!.world shouldBe "Gladera"
        killStatistics.entries["(elemental forces)"]?.apply {
            lastDayKilledPlayers shouldBe 3
            lastDayKilled shouldBe 0
            lastWeekKilledPlayers shouldBe 19
            lastWeekKilled shouldBe 0
        } shouldNotBe null
    }
    test("Kill statistics for a world that doesn't exist") {
        val killStatistics =
            KillStatisticsParser.fromContent(getResource("killStatistics/killStatisticsNotFound.txt"))
        killStatistics shouldBe null
    }
})
