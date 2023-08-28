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
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlinx.datetime.Instant

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
                houseId shouldBe 20205
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
    test("Recently traded character") {
        val content = getResource("character/characterTraded.txt")

        val character = CharacterParser.fromContent(content)

        character.shouldBeInstanceOf<Character>()
        with(character) {
            isRecentlyTraded shouldBe true
            characters.forAtLeastOne {
                it.recentlyTraded shouldBe true
            }
        }
    }
    test("Character with complex deaths") {
        val content = getResource("character/characterWithComplexDeaths.txt")

        val character = CharacterParser.fromContent(content)

        character.shouldBeInstanceOf<Character>()
        with(character) {
            deaths.forAtLeastOne {
                it.killers.forAtLeastOne { k -> k.summon shouldNotBe null }
            }
            deaths.forAtLeastOne {
                it.assists.shouldNotBeEmpty()
            }
        }
    }
    test("Character scheduled for deletion") {
        val content = getResource("character/characterDeletionScheduled.txt")

        val character = CharacterParser.fromContent(content)

        character.shouldBeInstanceOf<Character>()
        with(character) {
            isScheduledForDeletion shouldBe true
            deletionDate.shouldBeInstanceOf<Instant>()
        }
    }
    test("Character with former names") {
        val content = getResource("character/characterFormerNames.txt")

        val character = CharacterParser.fromContent(content)

        character.shouldBeInstanceOf<Character>()
        with(character) {
            formerNames.shouldNotBeEmpty()
        }
    }

    test("Character not found") {
        val content = getResource("character/characterNotFound.txt")

        val character = CharacterParser.fromContent(content)

        character.shouldBeNull()
    }

    test("Character with title and badges") {
        val content = getResource("character/characterFormerNames.txt")

        val character = CharacterParser.fromContent(content)

        character.shouldBeInstanceOf<Character>()
        with(character) {
            badges.shouldNotBeEmpty()
            title.shouldNotBeNull()
        }
    }
    test("Character with no badges selected") {
        val content = getResource("character/characterNoBadgesSelected.txt")

        val character = CharacterParser.fromContent(content)

        character.shouldBeInstanceOf<Character>()
        with(character) {
            badges.shouldBeEmpty()
        }
    }
    test("Character with multiple houses") {
        val content = getResource("character/characterMultipleHouses.txt")

        val character = CharacterParser.fromContent(content)

        character.shouldBeInstanceOf<Character>()
        with(character) {
            houses shouldHaveAtLeastSize 2
        }
    }
    test("Character with special position") {
        val content = getResource("character/characterSpecialPosition.txt")

        val character = CharacterParser.fromContent(content)

        character.shouldBeInstanceOf<Character>()
        with(character) {
            position shouldNotBe null
        }
    }

})
