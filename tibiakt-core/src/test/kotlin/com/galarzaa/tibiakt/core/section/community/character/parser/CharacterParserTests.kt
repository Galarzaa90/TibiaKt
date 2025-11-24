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

package com.galarzaa.tibiakt.core.section.community.character.parser

import com.galarzaa.tibiakt.TestUtilities.getResource
import com.galarzaa.tibiakt.core.domain.character.Sex
import com.galarzaa.tibiakt.core.domain.character.Vocation
import com.galarzaa.tibiakt.core.section.community.character.model.CharacterInfo
import com.galarzaa.tibiakt.core.section.community.character.model.DeathParticipant
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.FunSpec
import io.kotest.inspectors.forAtLeastOne
import io.kotest.inspectors.shouldForAny
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldHaveAtLeastSize
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.types.shouldBeInstanceOf

class CharacterParserTests : FunSpec({
    isolationMode = IsolationMode.SingleInstance

    test("Regular character") {
        val content = getResource("character/character.txt")

        val character = CharacterParser.fromContent(content)

        character.shouldBeInstanceOf<CharacterInfo>()
        with(character) {
            name shouldBe "Tschas"
            title shouldBe null
            formerNames.shouldBeEmpty()
            unlockedTitles shouldBe 23
            sex shouldBe Sex.FEMALE
            vocation shouldBe Vocation.ELDER_DRUID
            houses shouldHaveSize 1
            with(houses.first()) {
                name shouldBe "Park Lane 4"
                houseId shouldBe 20_205
                town shouldBe "Carlin"
                world shouldBe character.world
            }
            lastLoginAt shouldNotBe null
            position shouldBe null
            isPremium shouldBe true
            isRecentlyTraded shouldBe false
            badges shouldHaveSize 8
            isHidden shouldBe false
        }
    }
    test("Character not found") {
        val character = CharacterParser.fromContent(getResource("character/characterNotFound.txt"))

        character shouldBe null
    }
    test("Character recently traded") {
        val character = CharacterParser.fromContent(getResource("character/characterRecentlyTraded.txt"))

        character.shouldBeInstanceOf<CharacterInfo>()
        character.isRecentlyTraded shouldBe true
    }
    test("Character scheduled for deletion") {
        val character = CharacterParser.fromContent(getResource("character/characterScheduledForDeletion.txt"))

        character.shouldBeInstanceOf<CharacterInfo>()
        character.deletionScheduledAt shouldNotBe null
        character.isScheduledForDeletion shouldBe true
    }
    test("Character with complex deaths"){
        val character = CharacterParser.fromContent(getResource("character/characterWithComplexDeaths.txt"))

        character.shouldNotBeNull()
        val killers = character.deaths.flatMap { it.killers }
        killers.shouldForAny { it is DeathParticipant.Summon }
    }
    test("Character with former names") {
        val character = CharacterParser.fromContent(getResource("character/characterWithFormerNames.txt"))

        character.shouldBeInstanceOf<CharacterInfo>()
        character.formerNames shouldHaveAtLeastSize 1
    }
    test("Character with former world") {
        val character = CharacterParser.fromContent(getResource("character/characterWithFormerWorld.txt"))

        character.shouldBeInstanceOf<CharacterInfo>()
        character.formerWorld shouldNotBe null
    }
    test("Character with multiple houses") {
        val character = CharacterParser.fromContent(getResource("character/characterWithMultipleHouses.txt"))

        character.shouldBeInstanceOf<CharacterInfo>()
        character.houses shouldHaveAtLeastSize 2
    }
    test("Character with no badges selected") {
        val character = CharacterParser.fromContent(getResource("character/characterWithNoBadgesSelected.txt"))

        character.shouldBeInstanceOf<CharacterInfo>()
        character.badges shouldHaveSize 0
    }
    test("Character with special position") {
        val character = CharacterParser.fromContent(getResource("character/characterWithSpecialPosition.txt"))

        character.shouldBeInstanceOf<CharacterInfo>()
        character.position shouldNotBe null
        character.otherCharacters.forAtLeastOne {
            it.position shouldNotBe null
        }
    }
    test("Character with title and badges") {
        val character = CharacterParser.fromContent(getResource("character/characterWithTitleAndBadges.txt"))

        character.shouldBeInstanceOf<CharacterInfo>()
        character.title shouldNotBe null
        character.unlockedTitles shouldNotBe 0
        character.badges shouldHaveAtLeastSize 0
    }
    // TODO: Need sample with entire HTML
    xtest("Character with truncated deaths") {
        val character = CharacterParser.fromContent("character/characterWithTruncatedDeaths.txt")

        character.shouldBeInstanceOf<CharacterInfo>()
        character.deaths shouldHaveAtLeastSize 1
    }

    context("parseKiller") {
        test("Killer with ' of ' in name") {
            // language=html
            val html =
                """<a href="https://www.tibia.com/community/?subtopic=characters&name=Hand+of+Nightshadow" >Hand&#160;of&#160;Nightshadow</a>"""
            val killer = CharacterParser.parseKiller(html)
            killer.shouldNotBeNull()
            killer.shouldBeInstanceOf<DeathParticipant.Player>()
            killer.name shouldBe "Hand of Nightshadow"
        }
        test("Traded killer") {
            // language=html
            val html = """Nice&#160;bomba (traded)"""
            val killer = CharacterParser.parseKiller(html)
            killer.shouldBeInstanceOf<DeathParticipant.Player>()
            killer.name shouldBe "Nice bomba"
            killer.isTraded shouldBe true
        }
        test("Player summon") {
            // language=html
            val html =
                """sorcerer familiar of <a href="https://www.tibia.com/community/?subtopic=characters&name=Deatth" >Deatth</a>"""
            val killer = CharacterParser.parseKiller(html)
            killer.shouldBeInstanceOf<DeathParticipant.Summon>()
            killer.name shouldBe "sorcerer familiar"
            killer.summonerName shouldBe "Deatth"
            killer.summonerIsTraded shouldBe false
        }
        test("Creature") {
            // language=html
            val html = """cloak of terror"""
            val killer = CharacterParser.parseKiller(html)
            killer.shouldBeInstanceOf<DeathParticipant.Creature>()
            killer.name shouldBe "cloak of terror"
        }
    }
})
