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
            announcements shouldHaveSize 1
            totalPages shouldBe 2
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
        val board =
            ForumBoardParser.fromContent(TestResources.getResource("forums/forumBoardTradedThreadAutorAndLastPost.txt"))
        board shouldNotBe null
        with(board!!) {
            name shouldBe "Batabra"
            section shouldBe "World Boards"
            sectionId shouldBe 2
            entries.forAtLeastOne {
                it.authorTraded shouldBe true
                it.authorDeleted shouldBe false
                it.lastPost.traded shouldBe true
                it.lastPost.deleted shouldBe false
            }
        }
    }
    "Forum with deleted author and last post"{
        val board =
            ForumBoardParser.fromContent(TestResources.getResource("forums/forumBoardDeletedThreadAuthorAndLastPost.txt"))
        board shouldNotBe null
        with(board!!) {
            name shouldBe "Zunera - Trade"
            section shouldBe "Trade Boards"
            sectionId shouldBe 3
            entries.forAtLeastOne {
                it.authorTraded shouldBe false
                it.authorDeleted shouldBe true
                it.lastPost.traded shouldBe false
                it.lastPost.deleted shouldBe true
            }
        }
    }
    "Forum with golden frame threads"{
        val board = ForumBoardParser.fromContent(TestResources.getResource("forums/forumBoardGoldenFramePosts.txt"))
        board shouldNotBe null
        with(board!!) {
            name shouldBe "Proposals (English Only)"
            boardId shouldBe 10
            section shouldBe "Community Boards"
            sectionId shouldBe 12
            threadAge shouldBe -1
            currentPage shouldBe 1
            announcements shouldHaveSize 5
            totalPages shouldBe 1938
            resultsCount shouldBe 58115
            entries shouldHaveSize 30
            entries.forAll {
                it.goldenFrame shouldBe true
            }
        }
    }
})