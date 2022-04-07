package com.galarzaa.tibiakt.core.parsers

import com.galarzaa.tibiakt.TestResources.getResource
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe

class ForumSectionParserTests : StringSpec({
    "World Boards" {
        val boards = ForumSectionParser.fromContent(getResource("forums/forumSectionWorldBoards.txt"))
        boards.sectionId shouldBe 2
        boards.entries shouldHaveSize 79
    }
})