/*
 * Copyright © 2023 Allan Galarza
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.galarzaa.tibiakt.core.parsers

import com.galarzaa.tibiakt.TestResources.getResource
import com.galarzaa.tibiakt.core.models.forums.ForumBoard
import io.kotest.core.spec.style.FunSpec
import io.kotest.inspectors.forAtLeastOne
import io.kotest.matchers.collections.shouldHaveAtLeastSize
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.ints.shouldBeGreaterThanOrEqual
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

class ForumBoardParserTests : FunSpec({
    test("Forum board") {
        val forumBoard = ForumBoardParser.fromContent(getResource("forumBoard/forumBoard.txt"))

        forumBoard.shouldBeInstanceOf<ForumBoard>()
        forumBoard.entries shouldHaveAtLeastSize 1
        forumBoard.totalPages shouldBeGreaterThanOrEqual 1
    }
    test("Forum board with announcements") {
        val forumBoard = ForumBoardParser.fromContent(getResource("forumBoard/forumBoardWithAnnouncements.txt"))

        forumBoard.shouldBeInstanceOf<ForumBoard>()
        forumBoard.announcements shouldHaveAtLeastSize 1
    }
    test("Forum board empty") {
        val forumBoard = ForumBoardParser.fromContent(getResource("forumBoard/forumBoardEmpty.txt"))

        forumBoard.shouldBeInstanceOf<ForumBoard>()
        forumBoard.announcements shouldHaveSize 0
        forumBoard.entries shouldHaveSize 0
        forumBoard.totalPages shouldBe 1
        forumBoard.resultsCount shouldBe 0
    }
    test("Forum board on invalid page") {
        val forumBoard = ForumBoardParser.fromContent(getResource("forumBoard/forumBoardInvalidPage.txt"))

        forumBoard.shouldBeInstanceOf<ForumBoard>()
        forumBoard.announcements shouldHaveSize 0
        forumBoard.entries shouldHaveSize 0
        forumBoard.totalPages shouldBe 1
        forumBoard.resultsCount shouldBe 0
    }
    test("Forum board not found") {
        val forumBoard = ForumBoardParser.fromContent(getResource("forumBoard/forumBoardNotFound.txt"))

        forumBoard shouldBe null
    }
    test("Forum board with golden frame") {
        val forumBoard = ForumBoardParser.fromContent(getResource("forumBoard/forumBoardWithGoldenFrame.txt"))

        forumBoard.shouldBeInstanceOf<ForumBoard>()
        forumBoard.entries.forAtLeastOne {
            it.goldenFrame shouldBe true
        }
    }
    test("Forum board with thread by deleted char") {
        val forumBoard = ForumBoardParser.fromContent(getResource("forumBoard/forumBoardWithThreadByDeletedChar.txt"))

        forumBoard.shouldBeInstanceOf<ForumBoard>()
        forumBoard.entries.forAtLeastOne {
            it.isAuthorDeleted shouldBe true
        }
    }
    test("Forum board with thread by traded char") {
        val forumBoard = ForumBoardParser.fromContent(getResource("forumBoard/forumBoardWithThreadByTradedChar.txt"))

        forumBoard.shouldBeInstanceOf<ForumBoard>()
        forumBoard.entries.forAtLeastOne {
            it.isAuthorTraded shouldBe true
        }
    }
    test("Forum board with thread with last post by deleted char") {
        val forumBoard =
            ForumBoardParser.fromContent(getResource("forumBoard/forumBoardWithThreadWithLastPostByDeletedChar.txt"))

        forumBoard.shouldBeInstanceOf<ForumBoard>()
        forumBoard.entries.forAtLeastOne {
            it.lastPost.deleted shouldBe true
        }
    }
    test("Forum board with thread with last post by traded char") {
        val forumBoard =
            ForumBoardParser.fromContent(getResource("forumBoard/forumBoardWithThreadWithLastPostByTradedChar.txt"))

        forumBoard.shouldBeInstanceOf<ForumBoard>()
        forumBoard.entries.forAtLeastOne {
            it.lastPost.traded shouldBe true
        }
    }
})
