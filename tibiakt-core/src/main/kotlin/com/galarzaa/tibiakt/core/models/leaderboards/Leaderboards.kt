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

@file:UseSerializers(InstantSerializer::class)

package com.galarzaa.tibiakt.core.models.leaderboards

import com.galarzaa.tibiakt.core.models.PaginatedWithUrl
import com.galarzaa.tibiakt.core.serializers.InstantSerializer
import com.galarzaa.tibiakt.core.utils.getLeaderboardUrl
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers

/**
 * The Tibia Drome leaderboards of a world.
 *
 * @property world The name of the world.
 * @property rotation The rotation of this leaderboards.
 * @property availableRotations The rotations that are available to view.
 * @property lastUpdated The time when the leaderboard was last updated. Only available for the current rotation.
 */
@Serializable
data class Leaderboards(
    val world: String,
    val rotation: LeaderboardsRotation,
    val availableRotations: List<LeaderboardsRotation>,
    val lastUpdated: Instant?,
    override val currentPage: Int,
    override val totalPages: Int,
    override val resultsCount: Int,
    override val entries: List<BaseLeaderboardsEntry>,
) : PaginatedWithUrl<BaseLeaderboardsEntry> {

    /**
     * The URL to these leaderboards.
     */
    val url get() = getLeaderboardUrl(world, rotation.rotationId, currentPage)

    override fun getPageUrl(page: Int) = getLeaderboardUrl(world, rotation.rotationId, page)
}

