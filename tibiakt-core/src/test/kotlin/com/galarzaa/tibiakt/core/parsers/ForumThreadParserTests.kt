/*
 * Copyright © 2022 Allan Galarza
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
import com.galarzaa.tibiakt.core.enums.Vocation
import com.galarzaa.tibiakt.core.models.forums.ForumAuthor
import io.kotest.core.spec.style.StringSpec
import io.kotest.inspectors.forAtLeastOne
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.types.shouldBeInstanceOf

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

    "Parse thread with a poster that was traded recently" {
        val thread =
            ForumThreadParser.fromContent(getResource("forums/forumThreadPostAuthorRecentlyTraded.txt"))
        thread shouldNotBe null
        with(thread!!) {
            title shouldBe "heffaklumparna!!"
            threadId shouldBe 4927392
            boardId shouldBe 25
            currentPage shouldBe 1
            totalPages shouldBe 1
            resultsCount shouldBe 6
            entries shouldHaveSize 6
            entries.forAtLeastOne {
                it.author.shouldBeInstanceOf<ForumAuthor>()
                with(it.author as ForumAuthor) {
                    name shouldBe "Wideswing"
                    world shouldBe "Vunira"
                    vocation shouldBe Vocation.ELITE_KNIGHT
                    level shouldBe 159
                    posts shouldBe 1
                }
            }
        }
    }

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
