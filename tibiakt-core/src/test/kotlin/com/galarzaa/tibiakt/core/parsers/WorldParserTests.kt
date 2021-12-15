package com.galarzaa.tibiakt.core.parsers

import com.galarzaa.tibiakt.TestResources.getResource
import com.galarzaa.tibiakt.core.enums.BattlEyeType
import com.galarzaa.tibiakt.core.enums.TransferType
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import java.time.LocalDate

class WorldParserTests : StringSpec({
    isolationMode = IsolationMode.InstancePerTest
    "World with yellow BattlEye" {
        val world = WorldParser.fromContent(getResource("worlds/worldYellowBE.txt"))
        world shouldNotBe null
        world!!.name shouldBe "Gladera"
        world.isOnline shouldBe true
        world.onlineCount shouldBe 208
        world.playersOnline shouldHaveSize world.onlineCount
        world.worldQuests shouldHaveSize 5
        world.battlEyeType shouldBe BattlEyeType.YELLOW
        world.battlEyeStartDate shouldBe LocalDate.of(2018, 4, 19)
    }

    "World with no BattlEye" {
        val world = WorldParser.fromContent(getResource("worlds/worldNoBE.txt"))
        world shouldNotBe null
        world!!.name shouldBe "Zuna"
        world.isOnline shouldBe true
        world.onlineCount shouldBe 13
        world.playersOnline shouldHaveSize world.onlineCount
        world.worldQuests shouldHaveSize 1
        world.battlEyeType shouldBe BattlEyeType.UNPROTECTED
        world.battlEyeStartDate shouldBe null
    }

    "World with green BattlEye and no world quest titles" {
        val world = WorldParser.fromContent(getResource("worlds/worldGreenBENoTitles.txt"))
        world shouldNotBe null
        world!!.name shouldBe "Ardera"
        world.isOnline shouldBe true
        world.onlineCount shouldBe 197
        world.playersOnline shouldHaveSize world.onlineCount
        world.worldQuests shouldHaveSize 0
        world.battlEyeType shouldBe BattlEyeType.GREEN
        world.battlEyeStartDate shouldBe null
        world.transferType shouldBe TransferType.LOCKED
        world.isPremiumRestricted shouldBe true
    }

    "Offline world" {
        val world = WorldParser.fromContent(getResource("worlds/worldOffline.txt"))
        world shouldNotBe null
        world!!.name shouldBe "Gladera"
        world.isOnline shouldBe false
        world.onlineCount shouldBe 0
    }

    "World not found"{
        val world = WorldParser.fromContent(getResource("worlds/worldNotFound.txt"))
        world shouldBe null
    }
})