package com.galarzaa.tibiakt.models

import kotlinx.serialization.Serializable

@Serializable
data class KillsStatisticEntry(
    val lastDayKilled: Int,
    val lastDayPlayersKilled: Int,
    val lastWeekKilled: Int,
    val lastWeekPlayersKilled: Int
)