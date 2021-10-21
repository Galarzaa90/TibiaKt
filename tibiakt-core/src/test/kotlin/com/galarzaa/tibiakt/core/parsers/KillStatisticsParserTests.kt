package com.galarzaa.tibiakt.core.parsers

import com.galarzaa.tibiakt.TestResources.getResource
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.maps.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class KillStatisticsParserTests : StringSpec({
    "Kill statistics with values" {
        val killStatistics = KillStatisticsParser.fromContent(getResource("killStatistics/killStatistics.txt"))
        killStatistics.world shouldBe "Antica"
        killStatistics.entries["(elemental forces)"]?.apply {
            lastDayKilledPlayers shouldBe 11
            lastDayKilled shouldBe 0
            lastWeekKilledPlayers shouldBe 58
            lastWeekKilled shouldBe 0
        } shouldNotBe null
    }
    "Kill statistics with no values"{
        val killStatistics = KillStatisticsParser.fromContent(getResource("killStatistics/killStatisticsEmpty.txt"))
        killStatistics shouldNotBe null
        killStatistics.entries shouldHaveSize 0
        killStatistics.total.run {
            lastDayKilledPlayers shouldBe 0
            lastDayKilled shouldBe 0
            lastWeekKilledPlayers shouldBe 0
            lastWeekKilled shouldBe 0
        }
    }
})