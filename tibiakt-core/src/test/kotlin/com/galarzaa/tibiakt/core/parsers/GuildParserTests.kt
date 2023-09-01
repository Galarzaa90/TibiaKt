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
import com.galarzaa.tibiakt.core.models.guild.Guild
import com.galarzaa.tibiakt.core.models.guild.GuildHall
import io.kotest.core.spec.style.FunSpec
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldHaveAtLeastSize
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.types.shouldBeInstanceOf
import java.time.LocalDate

class GuildParserTests : FunSpec({
    test("Active guild") {
        val guild = GuildParser.fromContent(getResource("guild/guild.txt"))

        guild.shouldBeInstanceOf<Guild>()
        guild.name shouldBe "Bald Dwarfs"
        guild.world shouldBe "Gladera"
        guild.description shouldNotBe null
        guild.foundingDate shouldBe LocalDate.of(2010, 9, 21)
        guild.isActive shouldBe true
        guild.applicationsOpen shouldBe true
        guild.guildHall.shouldBeInstanceOf<GuildHall>()
        guild.guildHall?.run {
            name shouldBe "Warriors' Guildhall"
        }
        guild.members shouldHaveAtLeastSize 1
    }
    test("Guild being disbanded") {
        val guild = GuildParser.fromContent(getResource("guild/guildDisbanding.txt"))

        guild.shouldBeInstanceOf<Guild>()
        guild.isActive shouldBe true
        guild.disbandingReason shouldNotBe null
        guild.disbandingDate shouldNotBe null
    }


    test("Guild in formation") {
        val guild = GuildParser.fromContent(getResource("guild/guildFormation.txt"))

        guild.shouldBeInstanceOf<Guild>()
        guild.disbandingDate shouldNotBe null
        guild.disbandingReason shouldNotBe null
        guild.isActive shouldBe false
    }

    test("Guild with minimum information") {
        val guild = GuildParser.fromContent(getResource("guild/guildMinimumInfo.txt"))

        guild.shouldBeInstanceOf<Guild>()
        with(guild){
            disbandingDate shouldBe null
            guildHall shouldBe null
            homepage shouldBe null
            description shouldBe null
        }
    }

    test("Guild not found") {
        val guild = GuildParser.fromContent(getResource("guild/guildNotFound.txt"))

        guild shouldBe null
    }
})
