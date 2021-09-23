package com.galarzaa.tibiakt.parsers

import com.galarzaa.tibiakt.TestResources
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import java.time.LocalDate

class GuildParserTests : StringSpec({
    "Parsing an active guild"{
        val guild = GuildParser.fromContent(TestResources.getResource("guilds/guildActive.txt"))
        guild shouldNotBe null
        guild!!.name shouldBe "Bald Dwarfs"
        guild.world shouldBe "Gladera"
        guild.description shouldNotBe null
        guild.foundingDate shouldBe LocalDate.of(2010, 9, 21)
        guild.isActive shouldBe true
        guild.applicationsOpen shouldBe true
        guild.guildHall shouldNotBe null
        guild.guildHall?.run {
            name shouldBe "Warriors' Guildhall"
            paidUntil shouldBe LocalDate.of(2021, 10, 8)
        }
        guild.members shouldHaveSize 2289
        guild.invited shouldHaveSize 0
        guild.homepage shouldNotBe null
    }

    "Parsing a guild with invites"{
        val guild = GuildParser.fromContent(TestResources.getResource("guilds/guildInvites.txt"))
        guild shouldNotBe null
        guild!!.name shouldBe "Black Dragons"
        guild.members shouldHaveSize 176
        guild.invited shouldHaveSize 1
    }

    "Parsing a guild in formation" {
        val guild = GuildParser.fromContent(TestResources.getResource("guilds/guildInFormation.txt"))
        guild shouldNotBe null
        guild!!.disbandingDate shouldBe LocalDate.of(2021, 9, 24)
        guild.disbandingReason shouldNotBe null
        guild.isActive shouldBe false
    }

    "Parsing an active guild with a disband warning"  {
        val guild =
            GuildParser.fromContent(TestResources.getResource("guilds/guildDisbandWarningLeadershipTransfer.txt"))
        guild shouldNotBe null
        guild!!.disbandingDate shouldBe LocalDate.of(2021, 10, 6)
        guild.disbandingReason shouldNotBe null
        guild.isActive shouldBe true
    }
})