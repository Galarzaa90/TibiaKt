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
import io.kotest.core.spec.style.StringSpec
import io.kotest.inspectors.forAtLeastOne
import io.kotest.matchers.collections.shouldHaveAtLeastSize
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe

class ForumSectionParserTests : StringSpec({
    "Forum section" {
        val boards = ForumSectionParser.fromContent(getResource("forumSection/forumSection.txt"))

        boards.sectionId shouldBe 2
        boards.entries shouldHaveAtLeastSize 1
    }
    "Forum section empty" {
        val boards = ForumSectionParser.fromContent(getResource("forumSection/forumSectionEmpty.txt"))

        boards.entries shouldHaveSize 0
    }

    "Forum section with empty board" {
        val boards = ForumSectionParser.fromContent(getResource("forumSection/forumSectionWithEmptyBoard.txt"))

        boards.entries shouldHaveAtLeastSize 1
        boards.entries.forAtLeastOne {
            it.lastPost shouldBe null
            it.postsCount shouldBe 0
            it.threadsCount shouldBe 0
        }
    }
})
