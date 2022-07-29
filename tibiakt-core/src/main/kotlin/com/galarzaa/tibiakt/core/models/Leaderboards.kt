@file:UseSerializers(InstantSerializer::class)

package com.galarzaa.tibiakt.core.models

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
    override val entries: List<BaseLeaderboardEntry>,
) : PaginatedWithUrl<BaseLeaderboardEntry> {

    /**
     * The URL to these leaderboards.
     */
    val url get() = getLeaderboardUrl(world, rotation.rotationId, currentPage)

    override fun getPageUrl(page: Int) = getLeaderboardUrl(world, rotation.rotationId, page)
}

@Serializable
data class LeaderboardsRotation(
    val rotationId: Int,
    val current: Boolean,
    val endDate: Instant,
)