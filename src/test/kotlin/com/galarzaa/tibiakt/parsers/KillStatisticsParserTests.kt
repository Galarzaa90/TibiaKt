package com.galarzaa.tibiakt.parsers

import com.galarzaa.tibiakt.TestResources.getResource
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class KillStatisticsParserTests : StringSpec({
    "Testing parsing kill statistics" {
        val killStatistics = KillStatisticsParser.fromContent(getResource("killStatistics/killStatistics.txt"))
        killStatistics.world shouldBe "Antica"
        killStatistics.entries["(elemental forces)"]?.apply {
            lastDayKilledPlayers shouldBe 11
            lastDayKilled shouldBe 0
            lastWeekKilledPlayers shouldBe 58
            lastWeekKilled shouldBe 0
        } shouldNotBe null
    }
})