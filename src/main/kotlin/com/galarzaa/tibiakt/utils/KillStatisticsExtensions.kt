package com.galarzaa.tibiakt.utils

import com.galarzaa.tibiakt.models.KillStatistics
import com.galarzaa.tibiakt.models.KillsStatisticEntry

val KillStatistics.url
    get() = getKillStatisticsUrl(world)

val KillStatistics.players
    get() = entries.getOrDefault("players", KillsStatisticEntry(0, 0, 0, 0))