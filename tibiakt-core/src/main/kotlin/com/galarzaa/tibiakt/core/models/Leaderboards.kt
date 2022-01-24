@file:UseSerializers(InstantSerializer::class)

package com.galarzaa.tibiakt.core.models

import com.galarzaa.tibiakt.core.serializers.InstantSerializer
import com.galarzaa.tibiakt.core.utils.getLeaderboardUrl
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.time.Instant

/**
 * The Tibia Drome leaderboards of a world.
 *
 * @property world The name of the world.
 * @property rotation The number of the rotation.
 * @property lastUpdated The time when the leaderboard was last updated. Only available for the current rotation.
 */
@Serializable
data class Leaderboards(
    val world: String,
    val rotation: Int,
    val lastUpdated: Instant?,
    override val currentPage: Int,
    override val totalPages: Int,
    override val resultsCount: Int,
    override val entries: List<LeaderboardsEntry> = emptyList(),
) : Paginated<LeaderboardsEntry> {

    /**
     * The URL to these leaderboards.
     */
    val url get() = getLeaderboardUrl(world, rotation, currentPage)
}
