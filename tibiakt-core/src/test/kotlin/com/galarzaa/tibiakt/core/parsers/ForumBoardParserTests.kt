package com.galarzaa.tibiakt.core.parsers

import com.galarzaa.tibiakt.TestResources
import io.kotest.core.spec.style.StringSpec
import io.kotest.inspectors.forAll
import io.kotest.inspectors.forAtLeastOne
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.ints.shouldBeGreaterThanOrEqual
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class ForumBoardParserTests : StringSpec({
    "Forum board with announcements"{
        val board = ForumBoardParser.fromContent(TestResources.getResource("forums/forumBoardWithAnnouncements.txt"))
        board shouldNotBe null
        with(board!!){
            name shouldBe "Payment Support"
            boardId shouldBe 20
            section shouldBe "Support Boards"
            sectionId shouldBe 7
            threadAge shouldBe 30
            currentPage shouldBe 1
            entries shouldHaveSize 30
            entries.forAll {
                it.title.isBlank() shouldBe false
                it.threadId shouldBeGreaterThan 0
                it.author.isBlank() shouldBe false
                it.replies shouldBeGreaterThanOrEqual 0
            }
        }
    }
    "Forum with traded author and last post"{
        val board = ForumBoardParser.fromContent(TestResources.getResource("forums/forumBoardTradedThreadAutorAndLastPost.txt"))
        board shouldNotBe null
        with(board!!) {
            name shouldBe "Astera"
            section shouldBe "World Boards"
            sectionId shouldBe 2
            entries.forAtLeastOne {
                it.authorTraded shouldBe true
                it.lastPost.traded shouldBe true
            }
        }

    }
})