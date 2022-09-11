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

import com.galarzaa.tibiakt.TestResources
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.StringSpec
import io.kotest.inspectors.forAtLeast
import io.kotest.inspectors.forExactly
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import kotlinx.datetime.Instant

class WorldOverviewParserTests : StringSpec({
    isolationMode = IsolationMode.InstancePerTest
    "Parse the world overview" {
        val worldOverview = WorldOverviewParser.fromContent(TestResources.getResource("worlds/worldsList.txt"))
        worldOverview.overallMaximumCount shouldBe 64_028
        worldOverview.totalOnline shouldBe 10_669
        worldOverview.overallMaximumCountDateTime shouldBe Instant.fromEpochSeconds(1_196_274_360)
        worldOverview.worlds shouldHaveSize 84
        worldOverview.worlds.forAtLeast(1) { it.isPremiumRestricted shouldBe true }
        worldOverview.worlds.forExactly(2) { it.isExperimental shouldBe true }
    }

    "Parse world overview with some offline worlds" {
        val worldOverview = WorldOverviewParser.fromContent(TestResources.getResource("worlds/worldsSomeOffline.txt"))
        worldOverview.totalOnline shouldBe 1973
        worldOverview.worlds.forAtLeast(1) { it.isOnline shouldBe false }
    }
})
