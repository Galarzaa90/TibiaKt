package com.galarzaa.tibiakt.parsers

import com.galarzaa.tibiakt.TestResources.getResource
import com.galarzaa.tibiakt.models.Vocation
import com.galarzaa.tibiakt.utils.scheduledForDeletion
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.StringSpec
import io.kotest.inspectors.forAtLeastOne
import io.kotest.inspectors.forAtMostOne
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import java.time.LocalDate


class CharacterParserTests : StringSpec({
    isolationMode = IsolationMode.InstancePerTest
    "Parsing a character with houses, guild and married" {
        val char = CharacterParser.fromContent(getResource("characters/characterHousesGuildAndMarried.txt"))
        char shouldNotBe null
        char!!.name shouldBe "Callie Aena"
        char.level shouldBe 392
        char.vocation shouldBe Vocation.ROYAL_PALADIN
        char.unlockedTitles shouldBe 18
        char.achievementPoints shouldBe 816
        char.marriedTo shouldBe "Jacky pumpkin"
        char.houses shouldHaveSize 2
        char.houses.first().run {
            name shouldBe "Trout Plaza 1"
            town shouldBe "Svargrond"
            paidUntil shouldBe LocalDate.of(2021, 10, 4)
        }
        char.guildMembership shouldNotBe null
        char.guildMembership?.run {
            name shouldBe "Naovaiterzezin"
            rank shouldBe "Adestrador de Sucuri"
        }
        char.comment shouldBe "/NB-83CE5FECF800002/"
        char.achievements shouldHaveSize 2
        char.achievements.first().run {
            grade shouldBe 2
            name shouldBe "Green Thumb"
            secret shouldBe true
        }
    }

    "Parsing a character with account badges displayed"{
        val char = CharacterParser.fromContent(getResource("characters/characterAccountBadges.txt"))
        char shouldNotBe null
        char!!.name shouldBe "Galarzaa Fidera"
        char.badges shouldHaveSize 8
        char.badges.first().run {
            name shouldBe "Ancient Hero"
            description shouldBe "The account is older than 15 years."
            imageUrl shouldNotBe null
        }
    }

    "Parsing a tutor"{
        val char = CharacterParser.fromContent(getResource("characters/characterTutor.txt"))
        char?.accountInformation shouldNotBe null
        char?.accountInformation?.run {
            position shouldBe "Tutor"
            tutorStars shouldBe 4
            loyaltyTitle shouldBe "Guardian of Tibia"
        }
    }

    "Parsing a CipSoft member"{
        val char = CharacterParser.fromContent(getResource("characters/characterCipSoftMember.txt"))
        char shouldNotBe null
        char!!.name shouldBe "Steve"
        char.position shouldBe "CipSoft Member"
        char.accountInformation?.position shouldBe "CipSoft Member"
        char.characters.forAtLeastOne { it.position shouldBe "CipSoft Member" }
    }

    "Parsing a character with former names and former world and deleted characters" {
        val char = CharacterParser.fromContent(getResource("characters/characterFormerNamesAndWorld.txt"))
        char shouldNotBe null
        char!!.name shouldBe "Legend Tumate"
        char.formerNames shouldHaveSize 2
        char.formerWorld shouldNotBe null
        char.characters.forAtLeastOne { it.isDeleted shouldBe true }
        char.characters.forAtLeastOne { it.isOnline shouldBe true }
        char.characters.forAtMostOne { it.main shouldBe true }
    }

    "Parsing a character scheduld for deletion" {
        val char = CharacterParser.fromContent(getResource("characters/characterDeleted.txt"))
        char shouldNotBe null
        char!!.name shouldBe "Orsty Serv"
        char.scheduledForDeletion shouldBe true
        char.deletionDate shouldNotBe null
        char.deletionDate?.epochSecond shouldBe 1632678475
    }

    "Parsing a character with PvP deaths"{
        val char = CharacterParser.fromContent(getResource("characters/characterPvpDeaths.txt"))
        char shouldNotBe null
        char!!.deaths shouldHaveSize 5
        char.deaths.first().run {
            level shouldBe 804
            killers shouldHaveSize 23
            assists shouldHaveSize 1
        }
        char.deaths[2].run {
            level shouldBe 802
            killers.last().run {
                name shouldBe "Alloy Hat"
                traded shouldBe true
                isPlayer shouldBe true
                summon shouldBe "a paladin familiar"
            }
        }
    }
})
