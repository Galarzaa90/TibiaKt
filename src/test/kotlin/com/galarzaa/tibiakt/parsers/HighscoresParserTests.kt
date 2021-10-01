package com.galarzaa.tibiakt.parsers

import com.galarzaa.tibiakt.TestResources.getResource
import com.galarzaa.tibiakt.enums.HighscoresBattlEyeType
import com.galarzaa.tibiakt.enums.HighscoresCategory
import com.galarzaa.tibiakt.enums.HighscoresProfession
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
        highscores.pageCurrent shouldBe 1
        highscores.pageTotal shouldBe 20
        highscores.resultsCount shouldBe 1000
        highscores.entries shouldHaveSize 30
    }
})