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

package com.galarzaa.tibiakt.core.section.community.urls

import com.galarzaa.tibiakt.core.domain.world.PvpType
import com.galarzaa.tibiakt.core.net.tibiaUrl
import com.galarzaa.tibiakt.core.section.community.highscores.model.HighscoresBattlEyeType
import com.galarzaa.tibiakt.core.section.community.highscores.model.HighscoresCategory
import com.galarzaa.tibiakt.core.section.community.highscores.model.HighscoresProfession
import com.galarzaa.tibiakt.core.section.community.house.model.HouseOrder
import com.galarzaa.tibiakt.core.section.community.house.model.HouseStatus
import com.galarzaa.tibiakt.core.section.community.house.model.HouseType

/**
 * URL for a specific character.
 */
public fun characterUrl(name: String): String = tibiaUrl("community", "characters", "name" to name)

/**
 * URL for the World Overview section.
 */
public fun worldOverviewUrl(): String = tibiaUrl("community", "worlds")

/**
 * URL for a specific world.
 */
public fun worldUrl(name: String): String = tibiaUrl("community", "worlds", "world" to name)

/**
 * URL for highscores with filters.
 *
 * @param world World name; if null, shows highscores for all worlds.
 */
public fun highscoresUrl(
    world: String?,
    category: HighscoresCategory = HighscoresCategory.EXPERIENCE_POINTS,
    vocations: HighscoresProfession = HighscoresProfession.ALL,
    page: Int = 1,
    battleEye: HighscoresBattlEyeType = HighscoresBattlEyeType.ANY_WORLD,
    worldTypes: Set<PvpType>? = null,
): String = tibiaUrl(
    "community",
    "subtopic" to "highscores",
    "world" to world,
    "profession" to vocations.value,
    "currentpage" to page,
    "category" to category.value,
    "beprotection" to battleEye.value,
    *worldTypes.orEmpty().map { "${PvpType.Companion.QUERY_PARAM_HIGHSCORES}[]" to it.highscoresFilterValue }
        .toTypedArray())

/**
 * URL for a world's Leaderboards.
 */
public fun leaderboardsUrl(world: String, rotation: Int? = null, page: Int = 1): String = tibiaUrl(
    "community", "leaderboards", "world" to world, "rotation" to rotation, "currentpage" to page
)

/**
 * URL for a world's kill statistics.
 */
public fun killStatisticsUrl(world: String): String = tibiaUrl("community", "killstatistics", "world" to world)

/**
 * URL for the Houses section with filters.
 */
public fun housesSectionUrl(
    world: String,
    town: String,
    type: HouseType? = null,
    status: HouseStatus? = null,
    order: HouseOrder? = null,
): String = tibiaUrl(
    "community",
    "houses",
    "world" to world,
    "town" to town,
    "state" to status?.value,
    "type" to type?.value,
    "order" to order?.value,
)

/**
 * URL for a specific house.
 */
public fun houseUrl(world: String, houseId: Int): String =
    tibiaUrl("community", "houses", "page" to "view", "world" to world, "houseid" to houseId)

/**
 * URL for a world's guild list.
 */
public fun worldGuildsUrl(world: String): String = tibiaUrl("community", "guilds", "world" to world)

/**
 * URL for a specific guild.
 */
public fun guildUrl(name: String): String = tibiaUrl("community", "guilds", "GuildName" to name, "page" to "view")


