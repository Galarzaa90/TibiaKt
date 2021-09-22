package com.galarzaa.tibiakt.parsers

import com.galarzaa.tibiakt.TestResources
import io.kotest.core.spec.style.StringSpec
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
        guild.applicationsOpen shouldBe true
    }
})