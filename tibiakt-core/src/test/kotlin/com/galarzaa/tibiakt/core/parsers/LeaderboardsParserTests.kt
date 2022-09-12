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
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe

class LeaderboardsParserTests : StringSpec({
    "Parse page with results" {
        val leaderboards = LeaderboardsParser.fromContent(getResource("leaderboards/leaderbordsResults.txt"))
        leaderboards!!.world shouldBe "Adra"
        leaderboards.rotation.rotationId shouldBe 14
        leaderboards.currentPage shouldBe 1
        leaderboards.totalPages shouldBe 1
        leaderboards.resultsCount shouldBe 45
        leaderboards.entries shouldHaveSize 45
    }
})
