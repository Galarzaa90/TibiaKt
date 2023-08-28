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
import com.galarzaa.tibiakt.core.enums.HighscoresBattlEyeType
import com.galarzaa.tibiakt.core.enums.HighscoresCategory
import com.galarzaa.tibiakt.core.enums.HighscoresProfession
import com.galarzaa.tibiakt.core.models.highscores.Highscores
import io.kotest.core.spec.style.FunSpec
import io.kotest.core.spec.style.StringSpec
import io.kotest.inspectors.forAll
import io.kotest.matchers.collections.shouldHaveAtLeastSize
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.ints.shouldNotBeGreaterThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.types.shouldBeInstanceOf

class HighscoresParserTests : FunSpec({
    test("Highscores"){
        val highscores = HighscoresParser.fromContent(getResource("highscores/highscores.txt"))
        highscores.shouldBeInstanceOf<Highscores>()

        with(highscores){
            highscores.world shouldBe "Gladera"
            entries shouldHaveAtLeastSize 1
            resultsCount shouldNotBeGreaterThan 0
            entries.forAll {
                it.world shouldBe highscores.world
            }
        }
    }
    test("Highscores empty"){
        val highscores = HighscoresParser.fromContent(getResource("highscores/highscoresEmpty.txt"))
        highscores.shouldBeInstanceOf<Highscores>()

        with(highscores){
            entries shouldHaveSize 0
            totalPages shouldBe 1
            resultsCount shouldBe 0
            currentPage shouldBe 0
        }
    }
})
