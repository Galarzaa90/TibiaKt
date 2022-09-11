package com.galarzaa.tibiakt.core.models

import kotlinx.serialization.Serializable

/**
 * The kill statistics for a given entry.
 *
 * @property lastDayKilledPlayers The number of players killed by this race in the last day.
 * @property lastDayKilled The number of this race killed in the last day.
 * @property lastWeekKilledPlayers The number of players killed by this race in the last week.
 * @property lastWeekKilled The number of this race killed in the last week.
 */
@Serializable
public data class KillsStatisticEntry(
    val lastDayKilledPlayers: Int,
    val lastDayKilled: Int,
    val lastWeekKilledPlayers: Int,
    val lastWeekKilled: Int,
)
