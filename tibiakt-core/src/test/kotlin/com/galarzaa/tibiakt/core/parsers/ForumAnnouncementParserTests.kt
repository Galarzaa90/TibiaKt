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
})

