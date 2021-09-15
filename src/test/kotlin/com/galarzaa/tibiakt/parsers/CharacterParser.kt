package com.galarzaa.tibiakt.parsers

import com.galarzaa.tibiakt.TestResources.getResource
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe


class CharacterParserTest : StringSpec({
    isolationMode = IsolationMode.InstancePerTest
    "Parsing a character" {
        val char = CharacterParser.fromContent(getResource("character.txt"))
        char shouldNotBe null
        char?.run {
            name shouldBe "Galarzaa Fidera"
            level shouldBe 287
        }
    }

    "Parsing a tutor"{
        val char = CharacterParser.fromContent(getResource("characters/characterTutor.txt"))
        char?.accountInformation shouldNotBe null
        char?.accountInformation?.run {
            position shouldBe "Tutor"
            tutorStars shouldBe 4
            loyaltyTitle shouldBe "Guardian of Tibia"
        }
    }
})
