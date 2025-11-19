/*
 * Copyright © 2023 Allan Galarza
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

package com.galarzaa.tibiakt.core.parser

import com.galarzaa.tibiakt.TestUtilities.getResource
import com.galarzaa.tibiakt.core.section.community.guild.model.GuildsSection
import com.galarzaa.tibiakt.core.section.community.guild.parser.GuildsSectionParser
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldHaveAtLeastSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

class GuildsSectionParserTests : StringSpec({
    "Parse the guilds section list"{
        val guildsSection = GuildsSectionParser.fromContent(getResource("guildsSection/guildsSection.txt"))

        guildsSection.shouldBeInstanceOf<GuildsSection>()
        guildsSection.guilds shouldHaveAtLeastSize 1
        guildsSection.activeGuilds shouldHaveAtLeastSize 1
        guildsSection.guildsInFormation shouldHaveAtLeastSize 1
    }

    "Parse the guilds section list of a world that does not exist" {
        val guildsSection = GuildsSectionParser.fromContent(getResource("guildsSection/guildsSectionNotFound.txt"))

        guildsSection shouldBe null
    }
})
