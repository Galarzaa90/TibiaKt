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
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class HighscoresParserTests : StringSpec({
    "Parse highscores for all worlds" {
        val highscores = HighscoresParser.fromContent(getResource("highscores/highscoresAllWorlds.txt"))
        highscores shouldNotBe null
        highscores!!.world shouldBe null
        highscores.category shouldBe HighscoresCategory.EXPERIENCE_POINTS
        highscores.vocation shouldBe HighscoresProfession.ALL
        highscores.worldTypes shouldHaveSize 0
        highscores.battlEyeType shouldBe HighscoresBattlEyeType.ANY_WORLD
        highscores.currentPage shouldBe 1
        highscores.totalPages shouldBe 20
        highscores.resultsCount shouldBe 1000
        highscores.entries shouldHaveSize 50
    }

    "Parse highscores with PvP types filters selected" {
        val highscores = HighscoresParser.fromContent(getResource("highscores/highscoresPvpTypesSelected.txt"))
        highscores shouldNotBe null
        highscores!!.world shouldBe null
        highscores.category shouldBe HighscoresCategory.MAGIC_LEVEL
        highscores.vocation shouldBe HighscoresProfession.SORCERERS
        highscores.worldTypes shouldHaveSize 2
        highscores.battlEyeType shouldBe HighscoresBattlEyeType.ANY_WORLD
        highscores.currentPage shouldBe 1
        highscores.totalPages shouldBe 23
        highscores.resultsCount shouldBe 1114
        highscores.entries shouldHaveSize 50
    }

    "Parse highscores with no results" {
        val highscores = HighscoresParser.fromContent(getResource("highscores/highscoresLastPageLoyalty.txt"))
        highscores shouldNotBe null
        highscores!!.world shouldBe "Antica"
        highscores.category shouldBe HighscoresCategory.LOYALTY_POINTS
        highscores.vocation shouldBe HighscoresProfession.ALL
        highscores.worldTypes shouldHaveSize 0
        highscores.battlEyeType shouldBe HighscoresBattlEyeType.ANY_WORLD
        highscores.currentPage shouldBe 21
        highscores.totalPages shouldBe 21
        highscores.resultsCount shouldBe 1002
        highscores.entries shouldHaveSize 2
    }

    "Parse highscores for world that doesn't exist" {
        val highscores = HighscoresParser.fromContent(getResource("highscores/highscoresWorldDoesntExist.txt"))
        highscores shouldBe null
    }
})