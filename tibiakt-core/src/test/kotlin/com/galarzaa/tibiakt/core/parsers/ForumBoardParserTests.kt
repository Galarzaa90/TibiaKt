package com.galarzaa.tibiakt.core.parsers

import com.galarzaa.tibiakt.TestResources
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class ForumBoardParserTests : StringSpec({
    "Forum with traded author and last post"{
        val board = ForumBoardParser.fromContent(TestResources.getResource("forums/forumBoardTradedThreadAutorAndLastPost.txt"))
        board shouldNotBe null
        board!!.name shouldBe "Astera"
        board.section shouldBe "World Boards"
    }
})