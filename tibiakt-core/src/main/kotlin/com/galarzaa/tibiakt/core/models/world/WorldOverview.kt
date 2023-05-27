/*
 * Copyright © 2023 Allan Galarza
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


package com.galarzaa.tibiakt.core.models.world

import com.galarzaa.tibiakt.core.utils.getWorldOverviewUrl
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

/**
 * The world overview section in Tibia.com.
 *
 * @property overallMaximumCount The record for simultaneous online players.
 * @property overallMaximumCountDateTime The date and time when the record for online players was set.
 */
@Serializable
public data class WorldOverview(
    val overallMaximumCount: Int,
    val overallMaximumCountDateTime: Instant,
    val worlds: List<WorldEntry>,
    val tournamentWorlds: List<WorldEntry>,
) {
    /**
     * The total of currently online players across worlds.
     */
    val totalOnline: Int
        get() = worlds.sumOf { it.onlineCount }

    /**
     * The URL to the world overview section.
     */
    val url: String
        get() = getWorldOverviewUrl()
}
