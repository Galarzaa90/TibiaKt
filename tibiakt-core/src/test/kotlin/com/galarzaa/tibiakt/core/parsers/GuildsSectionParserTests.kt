package com.galarzaa.tibiakt.core.parsers

import com.galarzaa.tibiakt.TestResources.getResource
import io.kotest.core.spec.style.StringSpec
import io.kotest.inspectors.forExactly
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class GuildsSectionParserTests : StringSpec({
    "Parse the guilds section list"{
        val guildsSection = GuildsSectionParser.fromContent(getResource("guilds/guildList.txt"))
        guildsSection shouldNotBe null
        guildsSection!!.world shouldBe "Gladera"
        guildsSection.guilds shouldHaveSize 71
        guildsSection.guilds.forExactly(2) {
            it.isActive shouldBe false
        }
    }

    "Parse the guilds section list with only active guilds" {
        val guildsSection = GuildsSectionParser.fromContent(getResource("guilds/guildListActiveOnly.txt"))
        guildsSection shouldNotBe null
        guildsSection!!.world shouldBe "Bona"
        guildsSection.guilds shouldHaveSize 61
        guildsSection.guilds.forExactly(0) {
            it.isActive shouldBe false
        }
    }

    "Parse the guilds section list with no active guilds" {
        val guildsSection = GuildsSectionParser.fromContent(getResource("guilds/guildListNoActiveGuilds.txt"))
        guildsSection shouldNotBe null
        guildsSection!!.world shouldBe "Illusera"
        guildsSection.guilds shouldHaveSize 2
        guildsSection.guilds.forExactly(0) {
            it.isActive shouldBe true
        }
    }

    "Parse the guilds section list of a world that does not exist" {
        val guildsSection = GuildsSectionParser.fromContent(getResource("guilds/guildListWorldDoesntExist"))
        guildsSection shouldBe null
    }
})