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


package com.galarzaa.tibiakt.core.section.community.leaderboard.model

import com.galarzaa.tibiakt.core.pagination.PaginatedWithUrl
import com.galarzaa.tibiakt.core.section.community.urls.leaderboardsUrl
import kotlinx.serialization.Serializable
import kotlin.time.Instant

/**
 * The Tibia Drome leaderboards of a world.
 *
 * @property world The name of the world.
 * @property rotation The rotation of these leaderboards.
 * @property availableRotations The rotations that are available to view.
 * @property lastUpdatedAt The time when the leaderboard was last updated. Only available for the current rotation.
 */
@Serializable
public data class Leaderboard(
    val world: String,
    val rotation: LeaderboardRotation,
    val availableRotations: List<LeaderboardRotation>,
    val lastUpdatedAt: Instant?,
    override val currentPage: Int,
    override val totalPages: Int,
    override val resultsCount: Int,
    override val entries: List<LeaderboardEntry>,
) : PaginatedWithUrl<LeaderboardEntry> {

    /**
     * The URL to these leaderboards.
     */
    val url: String get() = leaderboardsUrl(world, rotation.rotationId, currentPage)

    override fun getPageUrl(page: Int): String = leaderboardsUrl(world, rotation.rotationId, page)
}
