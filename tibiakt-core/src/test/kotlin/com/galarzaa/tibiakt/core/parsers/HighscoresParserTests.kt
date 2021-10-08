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
        highscores.world shouldBe null
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
        highscores.world shouldBe null
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
        highscores.world shouldBe "Antica"
        highscores.category shouldBe HighscoresCategory.LOYALTY_POINTS
        highscores.vocation shouldBe HighscoresProfession.ALL
        highscores.worldTypes shouldHaveSize 0
        highscores.battlEyeType shouldBe HighscoresBattlEyeType.ANY_WORLD
        highscores.currentPage shouldBe 21
        highscores.totalPages shouldBe 21
        highscores.resultsCount shouldBe 1002
        highscores.entries shouldHaveSize 2
    }
})