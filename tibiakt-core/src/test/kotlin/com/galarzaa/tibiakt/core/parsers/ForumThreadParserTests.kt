/*
 * Copyright © 2024 Allan Galarza
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
import com.galarzaa.tibiakt.core.models.forums.ForumThread
import com.galarzaa.tibiakt.core.models.forums.UnavailableForumAuthor
import io.kotest.core.spec.style.FunSpec
import io.kotest.inspectors.forAtLeastOne
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldNotContainIgnoringCase
import io.kotest.matchers.types.shouldBeInstanceOf

class ForumThreadParserTests : FunSpec({
    test("Forum thread with edited post") {
        val thread = ForumThreadParser.fromContent(getResource("forumThread/forumThreadWithEditedPost.txt"))

        thread.shouldBeInstanceOf<ForumThread>()
        thread.entries.forAtLeastOne {
            it.editedBy shouldNotBe null
            it.editedAt shouldNotBe null
        }
    }

    test("Forum thread with golden frame") {
        val thread = ForumThreadParser.fromContent(getResource("forumThread/forumThreadWithGoldenFrame.txt"))

        thread.shouldBeInstanceOf<ForumThread>()
        thread.goldenFrame shouldBe true
    }

    test("Forum thread with post by deleted char") {
        val thread = ForumThreadParser.fromContent(getResource("forumThread/forumThreadWithPostByDeletedChar.txt"))

        thread.shouldBeInstanceOf<ForumThread>()
        thread.entries.forAtLeastOne {
            it.author.shouldBeInstanceOf<UnavailableForumAuthor>()
            it.author.name shouldNotContainIgnoringCase "traded"
            (it.author as UnavailableForumAuthor).isDeleted shouldBe true
        }
    }
    test("Forum thread with post by traded char") {
        val thread = ForumThreadParser.fromContent(getResource("forumThread/forumThreadWithPostByTradedChar.txt"))

        thread.shouldBeInstanceOf<ForumThread>()
        thread.entries.forAtLeastOne {
            it.author.shouldBeInstanceOf<UnavailableForumAuthor>()
            it.author.name shouldNotContainIgnoringCase "traded"
            (it.author as UnavailableForumAuthor).isTraded shouldBe true
        }
    }
    test("Forum thread with post with golden frame") {
        val thread = ForumThreadParser.fromContent(getResource("forumThread/forumThreadWithPostWithGoldenFrame.txt"))

        thread.shouldBeInstanceOf<ForumThread>()
        thread.entries.forAtLeastOne {
            // TODO: No golden frame attribute.
        }
    }

    test("Forum thread invalid page") {
        val thread = ForumThreadParser.fromContent(getResource("forumThread/forumThreadInvalidPage.txt"))

        thread.shouldBeInstanceOf<ForumThread>()
        thread.entries shouldHaveSize 0
    }

    test("Forum thread not found") {
        val thread = ForumThreadParser.fromContent(getResource("forumThread/forumThreadNotFound.txt"))

        thread shouldBe null
    }
})
