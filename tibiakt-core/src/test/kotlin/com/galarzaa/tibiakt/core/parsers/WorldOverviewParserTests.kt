package com.galarzaa.tibiakt.core.parsers

import com.galarzaa.tibiakt.TestResources
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.StringSpec
import io.kotest.inspectors.forAtLeast
import io.kotest.inspectors.forExactly
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import java.time.Instant

class WorldOverviewParserTests : StringSpec({
    isolationMode = IsolationMode.InstancePerTest
    "Parse the world overview" {
        val worldOverview = WorldOverviewParser.fromContent(TestResources.getResource("worlds/worldsList.txt"))
        worldOverview.overallMaximumCount shouldBe 64028
        worldOverview.totalOnline shouldBe 10669
        worldOverview.overallMaximumCountDateTime shouldBe Instant.ofEpochSecond(1196274360)
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