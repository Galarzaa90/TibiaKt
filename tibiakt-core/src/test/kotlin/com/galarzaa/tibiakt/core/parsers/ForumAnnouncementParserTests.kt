package com.galarzaa.tibiakt.core.parsers

import com.galarzaa.tibiakt.TestResources
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class ForumAnnouncementParserTests: StringSpec({
    "Parse announcement"{
        val announcement = ForumAnnouncementParser.fromContent(TestResources.getResource("forums/forumAnnouncement.txt"))
        announcement shouldNotBe null
        with(announcement!!){
            title shouldBe "Welcome to the Proposal Board!"
        }
    }
})