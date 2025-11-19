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

package com.galarzaa.tibiakt.core.parser

import com.galarzaa.tibiakt.TestUtilities.getResource
import com.galarzaa.tibiakt.core.section.community.leaderboard.model.DeletedLeaderboardEntry
import com.galarzaa.tibiakt.core.section.community.leaderboard.model.Leaderboard
import com.galarzaa.tibiakt.core.section.community.leaderboard.parser.LeaderboardParser
import io.kotest.core.spec.style.FunSpec
import io.kotest.inspectors.forAtLeastOne
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.types.shouldBeInstanceOf

class LeaderboardParserTests : FunSpec({
    test("Leaderboard of current rotation") {
        val leaderboard = LeaderboardParser.fromContent(getResource("leaderboard/leaderboardCurrentRotation.txt"))

        leaderboard.shouldBeInstanceOf<Leaderboard>()
        with(leaderboard) {
            rotation.isCurrent shouldBe true
            lastUpdatedAt shouldNotBe null
            entries.shouldNotBeEmpty()
        }
    }

    test("Leaderboard with a deleted character") {
        val leaderboard = LeaderboardParser.fromContent(getResource("leaderboard/leaderboardDeletedCharacter.txt"))

        leaderboard.shouldBeInstanceOf<Leaderboard>()
        with(leaderboard) {
            entries.forAtLeastOne {
                it.shouldBeInstanceOf<DeletedLeaderboardEntry>()
            }
        }
    }

    test("Leaderboard with no entries") {
        val leaderboard = LeaderboardParser.fromContent(getResource("leaderboard/leaderboardEmpty.txt"))

        leaderboard.shouldBeInstanceOf<Leaderboard>()
        with(leaderboard) {
            totalPages shouldBe 1
            resultsCount shouldBe 0
            entries.shouldBeEmpty()
        }
    }
    test("Leaderboard not found") {
        val leaderboard = LeaderboardParser.fromContent(getResource("leaderboard/leaderboardNotFound.txt"))

        leaderboard.shouldBeNull()
    }

})
