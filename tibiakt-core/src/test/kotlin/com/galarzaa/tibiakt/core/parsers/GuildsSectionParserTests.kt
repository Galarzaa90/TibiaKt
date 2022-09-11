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
