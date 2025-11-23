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

package com.galarzaa.tibiakt.core.section.community.killstatistics.model

import com.galarzaa.tibiakt.core.section.community.urls.killStatisticsUrl
import kotlinx.serialization.Serializable

/**
 * The Kill Statistics section for a specific world.
 * @property world The game world these statistics are for.
 * @property entries A mapping of races to their kills' information.
 * @property total The kill statistics totals.
 */
@Serializable
public data class KillStatistics(
    val world: String,
    val entries: Map<String, KillsStatisticEntry>,
    val total: KillsStatisticEntry,
) {
    /**
     * The URL to these kill statistics.
     */
    val url: String
        get() = killStatisticsUrl(world)

    /**
     * The kill statistics for players.
     */
    val players: KillsStatisticEntry
        get() = entries.getOrDefault("players", KillsStatisticEntry(0, 0, 0, 0))
}
