package com.galarzaa.tibiakt.models

import kotlinx.serialization.Serializable

@Serializable
data class KillsStatisticEntry(
    val lastDayKilledPlayers: Int,
    val lastDayKilled: Int,
    val lastWeekKilledPlayers: Int,
    val lastWeekKilled: Int,
)