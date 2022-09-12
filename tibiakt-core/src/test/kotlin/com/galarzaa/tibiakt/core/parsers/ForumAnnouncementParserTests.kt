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
import com.galarzaa.tibiakt.core.models.forums.ForumAuthor
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.types.shouldBeInstanceOf

class ForumAnnouncementParserTests: StringSpec({
    "Parse announcement"{
        val announcement = ForumAnnouncementParser.fromContent(getResource("forums/forumAnnouncement.txt"))
        announcement shouldNotBe null
        with(announcement!!){
            title shouldBe "Welcome to the Proposal Board!"
            board shouldBe "Proposals (English Only)"
            boardId shouldBe 10
            section shouldBe "Community Boards"
            sectionId shouldBe 12
            author.shouldBeInstanceOf<ForumAuthor>()
            with(author as ForumAuthor) {
                name shouldBe "CM Mirade"
                world shouldBe "Vunira"
                position shouldBe "Community Manager"
                level shouldBe 2
                posts shouldBe 160
            }
        }
    }

    "Parse announcement not found" {
        val announcement = ForumAnnouncementParser.fromContent(getResource("forums/forumAnnouncementNotFound.txt"))
        announcement shouldBe null
    }
})
