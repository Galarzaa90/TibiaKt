package com.galarzaa.tibiakt.core.parsers

import com.galarzaa.tibiakt.TestResources.getResource
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import java.time.LocalDate

class CMPostArchiveParserTests : StringSpec({
    "Parsing with results"{
        val cmPostArchive = CMPostArchiveParser.fromContent(getResource("forums/cmPostArchiveWithItems.txt"))
        cmPostArchive shouldNotBe null
        cmPostArchive.startDate shouldBe LocalDate.of(2022, 1, 13)
        cmPostArchive.endDate shouldBe LocalDate.of(2022, 1, 20)
        cmPostArchive.currentPage shouldBe 1
        cmPostArchive.totalPages shouldBe 1
        cmPostArchive.resultsCount shouldBe 9
        cmPostArchive.entries shouldHaveSize 9
    }
    "Parsing with no results"{
        val cmPostArchive = CMPostArchiveParser.fromContent(getResource("forums/cmPostArchiveEmpty.txt"))
        cmPostArchive shouldNotBe null
        cmPostArchive.startDate shouldBe LocalDate.of(2000, 1, 13)
        cmPostArchive.endDate shouldBe LocalDate.of(2000, 1, 20)
        cmPostArchive.currentPage shouldBe 1
        cmPostArchive.totalPages shouldBe 1
        cmPostArchive.resultsCount shouldBe 0
        cmPostArchive.entries shouldHaveSize 0
    }
})
