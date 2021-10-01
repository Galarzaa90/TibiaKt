package com.galarzaa.tibiakt.parsers

import com.galarzaa.tibiakt.TestResources.getResource
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldNotBe

class HighscoresParserTests : StringSpec({
    "Parse highscores for all worlds" {
        val highscores = HighscoresParser.fromContent(getResource("highscores/highscoresAllWorlds.txt"))
        highscores shouldNotBe null
    }
}) {
}