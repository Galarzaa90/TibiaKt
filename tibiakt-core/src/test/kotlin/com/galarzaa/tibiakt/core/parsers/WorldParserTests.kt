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
import com.galarzaa.tibiakt.core.enums.BattlEyeType
import com.galarzaa.tibiakt.core.enums.TransferType
import com.galarzaa.tibiakt.core.models.world.World
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.FunSpec
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldHaveAtLeastSize
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.types.shouldBeInstanceOf
import java.time.LocalDate

class WorldParserTests : FunSpec({
    test("World online") {
        val world = WorldParser.fromContent(getResource("world/worldOnline.txt"))
        world.shouldBeInstanceOf<World>()
        with(world) {
            isOnline shouldBe true
            location shouldBe "Europe"
            worldQuests shouldHaveAtLeastSize 1
            playersOnline shouldHaveSize onlineCount
        }
    }

    test("World offline") {
        val world = WorldParser.fromContent(getResource("world/worldOffline.txt"))
        world.shouldBeInstanceOf<World>()
        with(world) {
            isOnline shouldBe false
            playersOnline shouldHaveSize 0
            onlineCount shouldBe 0
        }
    }

    test("World without any titles") {
        val world = WorldParser.fromContent(getResource("world/worldNoTitles.txt"))
        world.shouldBeInstanceOf<World>()
        with(world) {
            worldQuests shouldHaveSize 0
        }
    }

    test("World unprotected") {
        val world = WorldParser.fromContent(getResource("world/worldUnprotected.txt"))
        world.shouldBeInstanceOf<World>()
        with(world) {
            battlEyeType shouldBe BattlEyeType.UNPROTECTED
            battlEyeStartDate shouldBe null
        }
    }

    test("World with yellow BattlEye") {
        val world = WorldParser.fromContent(getResource("world/worldYellowBattlEye.txt"))
        world.shouldBeInstanceOf<World>()
        with(world) {
            battlEyeType shouldBe BattlEyeType.YELLOW
            battlEyeStartDate shouldNotBe null
        }
    }
    test("World with green BattlEye") {
        val world = WorldParser.fromContent(getResource("world/worldGreenBattlEye.txt"))

        world.shouldBeInstanceOf<World>()
        with(world) {
            battlEyeType shouldBe BattlEyeType.GREEN
            battlEyeStartDate shouldBe null
        }
    }
    test("Experimental world") {
        val world = WorldParser.fromContent(getResource("world/worldExperimental.txt"))

        world.shouldBeInstanceOf<World>()
        with(world) {
            isExperimental shouldBe true
            battlEyeType shouldBe BattlEyeType.UNPROTECTED
        }
    }
    test("World not found") {
        val world = WorldParser.fromContent(getResource("world/worldNotFound.txt"))
        world shouldBe null
    }

})
