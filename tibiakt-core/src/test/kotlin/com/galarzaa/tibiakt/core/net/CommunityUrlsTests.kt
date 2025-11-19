/*
 * Copyright © 2025 Allan Galarza
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

package com.galarzaa.tibiakt.core.net

import com.galarzaa.tibiakt.core.domain.world.PvpType
import com.galarzaa.tibiakt.core.section.community.highscores.model.HighscoresBattlEyeType
import com.galarzaa.tibiakt.core.section.community.highscores.model.HighscoresCategory
import com.galarzaa.tibiakt.core.section.community.highscores.model.HighscoresProfession
import com.galarzaa.tibiakt.core.section.community.house.model.HouseOrder
import com.galarzaa.tibiakt.core.section.community.house.model.HouseStatus
import com.galarzaa.tibiakt.core.section.community.house.model.HouseType
import com.galarzaa.tibiakt.core.section.community.urls.characterUrl
import com.galarzaa.tibiakt.core.section.community.urls.guildUrl
import com.galarzaa.tibiakt.core.section.community.urls.highscoresUrl
import com.galarzaa.tibiakt.core.section.community.urls.houseUrl
import com.galarzaa.tibiakt.core.section.community.urls.housesSectionUrl
import com.galarzaa.tibiakt.core.section.community.urls.killStatisticsUrl
import com.galarzaa.tibiakt.core.section.community.urls.leaderboardsUrl
import com.galarzaa.tibiakt.core.section.community.urls.worldGuildsUrl
import com.galarzaa.tibiakt.core.section.community.urls.worldOverviewUrl
import com.galarzaa.tibiakt.core.section.community.urls.worldUrl
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.string.shouldContain

class CommunityUrlsTests : FunSpec({

    test("characterUrl") {
        val url = characterUrl("Galarzaa")
        url shouldContain "/community"
        url shouldContain "subtopic=characters"
        url shouldContain "name=Galarzaa"
    }

    test("worldOverviewUrl") {
        val url = worldOverviewUrl()
        url shouldContain "/community"
        url shouldContain "subtopic=worlds"
    }

    test("worldUrl") {
        val url = worldUrl("Antica")
        url shouldContain "/community"
        url shouldContain "subtopic=worlds"
        url shouldContain "world=Antica"
    }
    context("highscoresUrl") {
        test("with defaults") {
            val url = highscoresUrl(null)
            url shouldContain "/community"
            url shouldContain "subtopic=highscores"
            url shouldContain "category=${HighscoresCategory.EXPERIENCE_POINTS.value}"
            url shouldContain "profession=${HighscoresProfession.ALL.value}"
            url shouldContain "beprotection=${HighscoresBattlEyeType.ANY_WORLD.value}"
            url shouldContain "currentpage=1"
        }

        test("with world and PvP types") {
            val url = highscoresUrl(
                world = "Antica", worldTypes = setOf(PvpType.OPEN_PVP, PvpType.HARDCORE_PVP), page = 3
            )
            url shouldContain "/community"
            url shouldContain "subtopic=highscores"
            url shouldContain "world=Antica"
            url shouldContain "currentpage=3"
            url shouldContain PvpType.QUERY_PARAM_HIGHSCORES
        }
    }

    test("leaderboardsUrl") {
        val url = leaderboardsUrl("Antica", rotation = 2, page = 5)
        url shouldContain "/community"
        url shouldContain "subtopic=leaderboards"
        url shouldContain "world=Antica"
        url shouldContain "rotation=2"
        url shouldContain "currentpage=5"
    }

    test("killStatisticsUrl") {
        val url = killStatisticsUrl("Antica")
        url shouldContain "/community"
        url shouldContain "subtopic=killstatistics"
        url shouldContain "world=Antica"
    }
    context("housesSectionUrl") {
        test("basic") {
            val url = housesSectionUrl("Antica", "Thais")
            url shouldContain "/community"
            url shouldContain "subtopic=houses"
            url shouldContain "world=Antica"
            url shouldContain "town=Thais"
        }

        test("housesSectionUrl with filters") {
            val url = housesSectionUrl(
                "Antica", "Thais", type = HouseType.HOUSE, status = HouseStatus.AUCTIONED, order = HouseOrder.RENT
            )
            url shouldContain "state=${HouseStatus.AUCTIONED.value}"
            url shouldContain "type=${HouseType.HOUSE.value}"
            url shouldContain "order=${HouseOrder.RENT.value}"
        }
    }

    test("houseUrl") {
        val url = houseUrl("Antica", 1234)
        url shouldContain "/community"
        url shouldContain "subtopic=houses"
        url shouldContain "page=view"
        url shouldContain "world=Antica"
        url shouldContain "houseid=1234"
    }

    test("worldGuildsUrl") {
        val url = worldGuildsUrl("Antica")
        url shouldContain "/community"
        url shouldContain "subtopic=guilds"
        url shouldContain "world=Antica"
    }

    test("guildUrl") {
        val url = guildUrl("Crimson Warriors")
        url shouldContain "/community"
        url shouldContain "subtopic=guilds"
        url shouldContain "GuildName=Crimson+Warriors"
        url shouldContain "page=view"
    }
})
