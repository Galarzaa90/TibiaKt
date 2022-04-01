package com.galarzaa.tibiakt.core.parsers

import com.galarzaa.tibiakt.TestResources.getResource
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldHaveSize

class ForumsSectionParserTests : StringSpec({
    "World Boards" {
        val boards = ForumsSectionParser.fromContent(getResource("forums/forumSectionWorldBoards.txt"))
        boards.entries shouldHaveSize 79
    }
})