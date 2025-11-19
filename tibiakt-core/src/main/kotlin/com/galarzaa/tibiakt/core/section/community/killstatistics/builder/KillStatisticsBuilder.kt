/*
 * Copyright © 2025 Allan Galarza
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.galarzaa.tibiakt.core.section.community.killstatistics.builder

import com.galarzaa.tibiakt.core.builder.BuilderDsl
import com.galarzaa.tibiakt.core.builder.TibiaKtBuilder
import com.galarzaa.tibiakt.core.section.community.killstatistics.model.KillStatistics
import com.galarzaa.tibiakt.core.section.community.killstatistics.model.KillsStatisticEntry

@BuilderDsl
public inline fun killStatistics(block: KillStatisticsBuilder.() -> Unit): KillStatistics =
    KillStatisticsBuilder().apply(block).build()

@BuilderDsl
public inline fun killStatisticsBuilder(block: KillStatisticsBuilder.() -> Unit): KillStatisticsBuilder =
    KillStatisticsBuilder().apply(block)


/** Builder for [KillStatistics] instances. */
@BuilderDsl
public class KillStatisticsBuilder : TibiaKtBuilder<KillStatistics> {
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
        world = world ?: error("world is required"),
        entries = entries,
        total = total
    )
}
