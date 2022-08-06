package com.galarzaa.tibiakt.core.parsers

import com.galarzaa.tibiakt.TestResources.getResource
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class ForumThreadParserTests : StringSpec({
    "Parse thread with traded poster" {
        val thread = ForumThreadParser.fromContent(getResource("forums/forumThreadTradedPoster.txt"))
        thread shouldNotBe null
        with(thread!!) {
            title shouldBe "*ASAP* New Balancing Changes - Buff OLD Areas and Creatures!"
            threadId shouldBe 4807028
            boardId shouldBe 10
            currentPage shouldBe 1
            totalPages shouldBe 16
            resultsCount shouldBe 317
            entries shouldHaveSize 20
        }
    }

    //TODO: Find new sample and update
//    "Parse thread with a poster that was traded recently" {
//        val thread =
//            ForumThreadParser.fromContent(getResource("forums/forumThreadPostAuthorRecentlyTraded.txt"))
//        thread shouldNotBe null
//        with(thread!!) {
//            title shouldBe "Tibia Coin"
//            threadId shouldBe 4911031
//            boardId shouldBe 20
//            currentPage shouldBe 1
//            totalPages shouldBe 1
//            resultsCount shouldBe 2
//            entries shouldHaveSize 2
//            with(entries.last()) {
//                author.shouldBeInstanceOf<ForumAuthor>()
//                with(author as ForumAuthor) {
//                    title shouldBe "Creature of Habit (Grade 2)"
//                    isRecentlyTraded shouldBe true
//                    guild?.name shouldBe "Dark Purples Faster Turtles"
//                    posts shouldBe 77
//                }
//                signature shouldNotBe null
//
//            }
//        }
//    }

    "Parse the content of a thread going to an invalid page" {
        val thread = ForumThreadParser.fromContent(getResource("forums/forumThreadInvalidPage.txt"))
        thread shouldNotBe null
        with(thread!!) {
            title shouldBe "Bald Dwarfs Rec..."
            board shouldBe "Gladera"
            boardId shouldBe 143609
            section shouldBe "World Boards"
            sectionId shouldBe 2
        }
    }

    "Parse the content of a thread that doesn't exist" {
        val thread = ForumThreadParser.fromContent(getResource("forums/forumThreadNotFound.txt"))
        thread shouldBe null
    }
})