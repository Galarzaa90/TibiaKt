package com.galarzaa.tibiakt.core.parsers

import com.galarzaa.tibiakt.TestResources
import com.galarzaa.tibiakt.core.models.forums.ForumAuthor
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.types.shouldBeInstanceOf

class ForumThreadParserTests : StringSpec({
    "Parse thread with traded poster" {
        val thread = ForumThreadParser.fromContent(TestResources.getResource("forums/forumThreadTradedPoster.txt"))
        thread shouldNotBe null
        with(thread!!) {
            title shouldBe "*ASAP* New Balancing Changes - Buff OLD Areas and Creatures!"
            threadId shouldBe 4807028
            boardId shouldBe 10
            currentPage shouldBe 1
            totalPages shouldBe 15
            resultsCount shouldBe 284
            entries shouldHaveSize 20
        }
    }

    "Parse thread with a poster that was traded recently" {
        val thread =
            ForumThreadParser.fromContent(TestResources.getResource("forums/forumThreadPostAuthorRecentlyTraded.txt"))
        thread shouldNotBe null
        with(thread!!) {
            title shouldBe "Tibia Coin"
            threadId shouldBe 4911031
            boardId shouldBe 20
            currentPage shouldBe 1
            totalPages shouldBe 1
            resultsCount shouldBe 2
            entries shouldHaveSize 2
            with(entries.last()) {
                author.shouldBeInstanceOf<ForumAuthor>()
                with(author as ForumAuthor) {
                    title shouldBe "Creature of Habit (Grade 2)"
                    traded shouldBe true
                    guild?.name shouldBe "Dark Purples Faster Turtles"
                    posts shouldBe 77
                }
                signature shouldNotBe null

            }
        }
    }
})