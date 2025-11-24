/*
 * Copyright © 2025 Allan Galarza
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

package com.galarzaa.tibiakt.core.section.forum.announcement.parser

import com.galarzaa.tibiakt.TestUtilities.getResource
import com.galarzaa.tibiakt.core.section.forum.announcement.model.ForumAnnouncement
import com.galarzaa.tibiakt.core.section.forum.shared.model.ForumAuthor
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

class ForumAnnouncementParserTests : StringSpec({
    "Parse announcement" {
        val announcement = ForumAnnouncementParser.fromContent(getResource("forumAnnouncement/forumAnnouncement.txt"))

        announcement.shouldBeInstanceOf<ForumAnnouncement>()
        with(announcement) {

            author.shouldBeInstanceOf<ForumAuthor.Character>()
            with(author) {
                position shouldBe "Community Manager"
            }
        }
    }

    "Parse announcement not found" {
        val announcement =
            ForumAnnouncementParser.fromContent(getResource("forumAnnouncement/forumAnnouncementNotFound.txt"))

        announcement shouldBe null
    }
})
