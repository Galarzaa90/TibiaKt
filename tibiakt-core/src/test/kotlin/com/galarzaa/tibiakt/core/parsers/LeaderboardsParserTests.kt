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
import com.galarzaa.tibiakt.core.models.leaderboards.DeletedLeaderboardsEntry
import com.galarzaa.tibiakt.core.models.leaderboards.Leaderboards
import io.kotest.core.spec.style.FunSpec
import io.kotest.core.spec.style.StringSpec
import io.kotest.inspectors.forAtLeastOne
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.types.shouldBeInstanceOf

class LeaderboardsParserTests : FunSpec({
    test("Leaderboard of current rotation") {
        val leaderboard = LeaderboardsParser.fromContent(getResource("leaderboards/leaderboardCurrentRotation.txt"))

        leaderboard.shouldBeInstanceOf<Leaderboards>()
        with(leaderboard) {
            rotation.current shouldBe true
            lastUpdated shouldNotBe null
            entries.shouldNotBeEmpty()
        }
    }

    test("Leaderboard with a deleted character") {
        val leaderboard = LeaderboardsParser.fromContent(getResource("leaderboards/leaderboardDeletedCharacter.txt"))

        leaderboard.shouldBeInstanceOf<Leaderboards>()
        with(leaderboard) {
            entries.forAtLeastOne {
                it.shouldBeInstanceOf<DeletedLeaderboardsEntry>()
            }
        }
    }

    test("Leaderboard with no entries") {
        val leaderboard = LeaderboardsParser.fromContent(getResource("leaderboards/leaderboardEmpty.txt"))

        leaderboard.shouldBeInstanceOf<Leaderboards>()
        with(leaderboard) {
            totalPages shouldBe 1
            resultsCount shouldBe 0
            entries.shouldBeEmpty()
        }
    }
    test("Leaderboard not found") {
        val leaderboard = LeaderboardsParser.fromContent(getResource("leaderboards/leaderboardNotFound.txt"))

        leaderboard.shouldBeNull()
    }

})
