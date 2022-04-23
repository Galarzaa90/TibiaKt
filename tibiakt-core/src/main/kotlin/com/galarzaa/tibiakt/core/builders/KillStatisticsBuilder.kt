package com.galarzaa.tibiakt.core.builders

import com.galarzaa.tibiakt.core.models.KillStatistics
import com.galarzaa.tibiakt.core.models.KillsStatisticEntry
import com.galarzaa.tibiakt.core.utils.BuilderDsl

@BuilderDsl
inline fun killStatistics(block: KillStatisticsBuilder.() -> Unit) = KillStatisticsBuilder().apply(block).build()

@BuilderDsl
inline fun killStatisticsBuilder(block: KillStatisticsBuilder.() -> Unit) = KillStatisticsBuilder().apply(block)

@BuilderDsl
class KillStatisticsBuilder {
    var world: String? = null
    val entries: MutableMap<String, KillsStatisticEntry> = mutableMapOf()
    private var total: KillsStatisticEntry = KillsStatisticEntry(0, 0, 0, 0)

    fun total(
        lastDayKilledPlayers: Int,
        lastDayKilled: Int,
        lastWeekKilledPlayers: Int,
        lastWeekKilled: Int,
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