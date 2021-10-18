package com.galarzaa.tibiakt.core.models

import com.galarzaa.tibiakt.core.utils.getKillStatisticsUrl
import kotlinx.serialization.Serializable

/**
 * The Kill Statistics section for a specific world.
 * @property world The game world this statistics are for.
 * @property entries A mapping of races to their kills information.
 * @property total The kill statistics totals.
 */
@Serializable
data class KillStatistics(
    val world: String,
    val entries: Map<String, KillsStatisticEntry> = mapOf(),
    val total: KillsStatisticEntry,
) {
    /**
     * The URL to these kill stastistics.
     */
    val url
        get() = getKillStatisticsUrl(world)

    /**
     * The kill statistics for players.
     */
    val players
        get() = entries.getOrDefault("players", KillsStatisticEntry(0, 0, 0, 0))
}