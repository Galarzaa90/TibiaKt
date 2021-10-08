package com.galarzaa.tibiakt.builders

import com.galarzaa.tibiakt.models.KillStatistics
import com.galarzaa.tibiakt.models.KillsStatisticEntry

class KillStatisticsBuilder {
    private var world: String? = null
    val entries: MutableMap<String, KillsStatisticEntry> = mutableMapOf()
    private var total: KillsStatisticEntry = KillsStatisticEntry(0, 0, 0, 0)

    fun world(world: String) = apply { this.world = world }
    fun total(
        lastDayKilledPlayers: Int,
        lastDayKilled: Int,
        lastWeekKilledPlayers: Int,
        lastWeekKilled: Int
    ) = apply {
        total = KillsStatisticEntry(lastDayKilledPlayers, lastDayKilled, lastWeekKilledPlayers, lastWeekKilled)
    }

    fun addEntry(
        race: String,
        lastDayKilledPlayers: Int,
        lastDayKilled: Int,
        lastWeekKilledPlayers: Int,
        lastWeekKilled: Int
    ) = apply {
        entries[race] = KillsStatisticEntry(lastDayKilledPlayers, lastDayKilled, lastWeekKilledPlayers, lastWeekKilled)
    }

    fun build() = KillStatistics(
        world = world ?: throw IllegalStateException("world is required"),
        entries = entries,
        total = total
    )
}