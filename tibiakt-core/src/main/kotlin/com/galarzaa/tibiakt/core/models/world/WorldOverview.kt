@file:UseSerializers(InstantSerializer::class)

package com.galarzaa.tibiakt.core.models.world

import com.galarzaa.tibiakt.core.serializers.InstantSerializer
import com.galarzaa.tibiakt.core.utils.getWorldOverviewUrl
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.time.Instant

/**
 * The world overview section in Tibia.com
 *
 * @property overallMaximumCount The record for simultaneous online players.
 * @property overallMaximumCountDateTime The date and time when the record for online players was set.
 */
@Serializable
data class WorldOverview(
    val overallMaximumCount: Int,
    val overallMaximumCountDateTime: Instant,
    val worlds: List<WorldEntry> = emptyList(),
    val tournamentWorlds: List<WorldEntry> = emptyList(),
) {
    /**
     * The total of currently online players across worlds.
     */
    val totalOnline: Int
        get() = worlds.sumOf { it.onlineCount }

    /**
     * The URL to the world overview section.
     */
    val url: String
        get() = getWorldOverviewUrl()
}