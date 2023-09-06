/*
 * Copyright © 2023 Allan Galarza
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
import com.galarzaa.tibiakt.core.enums.HighscoresBattlEyeType
import com.galarzaa.tibiakt.core.enums.HighscoresCategory
import com.galarzaa.tibiakt.core.models.highscores.Highscores
import io.kotest.core.spec.style.FunSpec
import io.kotest.inspectors.forAll
import io.kotest.matchers.collections.shouldHaveAtLeastSize
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.types.shouldBeInstanceOf

class HighscoresParserTests : FunSpec({
    test("Highscores") {
        val highscores = HighscoresParser.fromContent(getResource("highscores/highscores.txt"))
        highscores.shouldBeInstanceOf<Highscores>()

        with(highscores) {
            highscores.world shouldBe "Gladera"
            entries shouldHaveAtLeastSize 1
            resultsCount shouldBeGreaterThan 0
            entries.forAll {
                it.world shouldBe highscores.world
            }
        }
    }
    test("Highscores with BattlEye PvP Filters"){
        val highscores = HighscoresParser.fromContent(getResource("highscores/highscoresBattleEyePvpFilters.txt"))

        highscores.shouldBeInstanceOf<Highscores>()
        with(highscores){
            world shouldBe null
            battlEyeType shouldNotBe HighscoresBattlEyeType.ANY_WORLD
            worldTypes shouldHaveAtLeastSize 1
        }
    }
    test("Global highscores") {
        val highscores = HighscoresParser.fromContent(getResource("highscores/highscoresGlobal.txt"))

        highscores.shouldBeInstanceOf<Highscores>()
        with(highscores) {
            world shouldBe null
            entries shouldHaveAtLeastSize 1
        }
    }
    test("Experience highscores") {
        val highscores = HighscoresParser.fromContent(getResource("highscores/highscoresExperience.txt"))

        highscores.shouldBeInstanceOf<Highscores>()
        with(highscores) {
            world shouldNotBe null
            category shouldBe HighscoresCategory.EXPERIENCE_POINTS
        }
    }
    test("Loyalty highscores") {
        val highscores = HighscoresParser.fromContent(getResource("highscores/highscoresLoyalty.txt"))

        highscores.shouldBeInstanceOf<Highscores>()
        with(highscores) {
            world shouldNotBe null
            category shouldBe HighscoresCategory.LOYALTY_POINTS
            entries.forAll {
                it.additionalValue shouldNotBe null
            }
        }
    }
    test("Highscores empty") {
        val highscores = HighscoresParser.fromContent(getResource("highscores/highscoresNoResults.txt"))

        highscores.shouldBeInstanceOf<Highscores>()
        with(highscores) {
            entries shouldHaveSize 0
            totalPages shouldBe 1
            resultsCount shouldBe 0
            currentPage shouldBe 1
        }
    }
    test("Highscores not found") {
        val highscores = HighscoresParser.fromContent(getResource("highscores/highscoresNotFound.txt"))

        highscores shouldBe null
    }
})
