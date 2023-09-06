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
import com.galarzaa.tibiakt.core.enums.AccountStatus
import com.galarzaa.tibiakt.core.enums.Sex
import com.galarzaa.tibiakt.core.enums.Vocation
import com.galarzaa.tibiakt.core.models.character.Character
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.FunSpec
import io.kotest.inspectors.forAtLeastOne
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldHaveAtLeastSize
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.types.shouldBeInstanceOf

class CharacterParserTests : FunSpec({
    isolationMode = IsolationMode.SingleInstance

    test("Regular character") {
        val content = getResource("character/character.txt")

        val character = CharacterParser.fromContent(content)

        character.shouldBeInstanceOf<Character>()
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
            lastLogin shouldNotBe null
            position shouldBe null
            accountStatus shouldBe AccountStatus.PREMIUM_ACCOUNT
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

        character.shouldBeInstanceOf<Character>()
        character.isRecentlyTraded shouldBe true
    }
    test("Character scheduled for deletion") {
        val character = CharacterParser.fromContent(getResource("character/characterScheduledForDeletion.txt"))

        character.shouldBeInstanceOf<Character>()
        character.deletionDate shouldNotBe null
        character.isScheduledForDeletion shouldBe true
    }
    test("Character with complex deaths"){

    }
    test("Character with former names") {
        val character = CharacterParser.fromContent(getResource("character/characterWithFormerNames.txt"))

        character.shouldBeInstanceOf<Character>()
        character.formerNames shouldHaveAtLeastSize 1
    }
    test("Character with former world") {
        val character = CharacterParser.fromContent(getResource("character/characterWithFormerNames.txt"))

        character.shouldBeInstanceOf<Character>()
        character.formerWorld shouldNotBe null
    }
    test("Character with multiple houses") {
        val character = CharacterParser.fromContent(getResource("character/characterWithMultipleHouses.txt"))

        character.shouldBeInstanceOf<Character>()
        character.houses shouldHaveAtLeastSize 2
    }
    test("Character with no badges selected") {
        val character = CharacterParser.fromContent(getResource("character/characterWithFormerNames.txt"))

        character.shouldBeInstanceOf<Character>()
        character.badges shouldHaveSize 0
    }
    test("Character with special position") {
        val character = CharacterParser.fromContent(getResource("character/characterWithSpecialPosition.txt"))

        character.shouldBeInstanceOf<Character>()
        character.position shouldNotBe null
        character.characters.forAtLeastOne {
            it.position shouldNotBe null
        }
    }
    test("Character with title and badges") {
        val character = CharacterParser.fromContent(getResource("character/characterWithTitleAndBadges.txt"))

        character.shouldBeInstanceOf<Character>()
        character.title shouldNotBe null
        character.unlockedTitles shouldNotBe 0
        character.badges shouldHaveAtLeastSize 0
    }
    //TODO: Need sample with entire HTML
    xtest("Character with truncated deaths") {
        val character = CharacterParser.fromContent("character/characterWithTruncatedDeaths.txt")

        character.shouldBeInstanceOf<Character>()
        character.deaths shouldHaveAtLeastSize 1
    }
})
