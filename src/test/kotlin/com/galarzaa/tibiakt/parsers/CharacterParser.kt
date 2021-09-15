package com.galarzaa.tibiakt.parsers

import com.galarzaa.tibiakt.TestResources.getResource
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe


class CharacterParserTest : StringSpec({
    isolationMode = IsolationMode.InstancePerTest
    "Parsing a character" {
        val char = CharacterParser.fromContent(getResource("characters/character.txt"))
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

    "Parsing a character with PvP deaths"{
        val char = CharacterParser.fromContent(getResource("characters/characterPvpDeaths.txt"))
        char shouldNotBe null
        char!!.deaths shouldHaveSize 5
        char.deaths.first().run {
            level shouldBe 804
            killers shouldHaveSize 23
            assists shouldHaveSize 1
        }
        char.deaths[2].run {
            level shouldBe 802
            killers.last().run {
                name shouldBe "Alloy Hat"
                recentlyTraded shouldBe true
                isPlayer shouldBe true
                summon shouldBe "a paladin familiar"
            }
        }
    }
})
