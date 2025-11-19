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

import kotlinx.serialization.Serializable

/**
 * The kill statistics for a given entry.
 *
 * @property lastDayKilledPlayers The number of players killed by this race in the last day.
 * @property lastDayKilled The number of this race killed in the last day.
 * @property lastWeekKilledPlayers The number of players killed by this race in the last week.
 * @property lastWeekKilled The number of this race killed in the last week.
 */
@Serializable
public data class KillsStatisticEntry(
    val lastDayKilledPlayers: Int,
    val lastDayKilled: Int,
    val lastWeekKilledPlayers: Int,
    val lastWeekKilled: Int,
)
