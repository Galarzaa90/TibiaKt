package com.galarzaa.tibiakt.core.utils

import com.galarzaa.tibiakt.core.models.KillStatistics
import com.galarzaa.tibiakt.core.models.KillsStatisticEntry

val KillStatistics.url
    get() = getKillStatisticsUrl(world)

val KillStatistics.players
    get() = entries.getOrDefault("players", KillsStatisticEntry(0, 0, 0, 0))