package com.galarzaa.tibiakt.core.parsers

import com.galarzaa.tibiakt.TestResources.getResource
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe

class LeaderboardsParserTests : StringSpec({
    "Parse page with results"{
        val leaderboards = LeaderboardsParser.fromContent(getResource("leaderboards/leaderbordsResults.txt"))
        leaderboards!!.world shouldBe "Adra"
        leaderboards.rotation shouldBe 14
        leaderboards.currentPage shouldBe 1
        leaderboards.totalPages shouldBe 1
        leaderboards.resultsCount shouldBe 45
        leaderboards.entries shouldHaveSize 45
    }
})
