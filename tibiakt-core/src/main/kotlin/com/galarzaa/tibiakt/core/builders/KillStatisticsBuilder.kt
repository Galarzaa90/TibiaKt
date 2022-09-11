package com.galarzaa.tibiakt.core.builders

import com.galarzaa.tibiakt.core.models.KillStatistics
import com.galarzaa.tibiakt.core.models.KillsStatisticEntry
import com.galarzaa.tibiakt.core.utils.BuilderDsl

@BuilderDsl
public inline fun killStatistics(block: KillStatisticsBuilder.() -> Unit): KillStatistics =
    KillStatisticsBuilder().apply(block).build()

@BuilderDsl
public inline fun killStatisticsBuilder(block: KillStatisticsBuilder.() -> Unit): KillStatisticsBuilder =
    KillStatisticsBuilder().apply(block)

@BuilderDsl
public class KillStatisticsBuilder : TibiaKtBuilder<KillStatistics>() {
    public var world: String? = null
    public val entries: MutableMap<String, KillsStatisticEntry> = mutableMapOf()
    private var total: KillsStatisticEntry = KillsStatisticEntry(0, 0, 0, 0)

    public fun total(
        lastDayKilledPlayers: Int,
        lastDayKilled: Int,
        lastWeekKilledPlayers: Int,
        lastWeekKilled: Int,
    ): KillStatisticsBuilder = apply {
        total = KillsStatisticEntry(lastDayKilledPlayers, lastDayKilled, lastWeekKilledPlayers, lastWeekKilled)
    }

    public fun addEntry(
        race: String,
        lastDayKilledPlayers: Int,
        lastDayKilled: Int,
        lastWeekKilledPlayers: Int,
        lastWeekKilled: Int,
    ): KillStatisticsBuilder = apply {
        entries[race] = KillsStatisticEntry(lastDayKilledPlayers, lastDayKilled, lastWeekKilledPlayers, lastWeekKilled)
    }

    override fun build(): KillStatistics = KillStatistics(
        world = world ?: throw IllegalStateException("world is required"),
        entries = entries,
        total = total
    )
}
