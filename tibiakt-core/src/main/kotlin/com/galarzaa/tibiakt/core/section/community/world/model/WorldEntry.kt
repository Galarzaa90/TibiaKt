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

package com.galarzaa.tibiakt.core.section.community.world.model

import com.galarzaa.tibiakt.core.domain.world.BattlEyeType
import com.galarzaa.tibiakt.core.domain.world.PvpType
import com.galarzaa.tibiakt.core.domain.world.TransferType
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

/**
 * A listed world in the [WorldOverview] section.
 *
 * @property isOnline Whether this world is currently online or not.
 * @property onlinePlayersCount The total of online players in this world.
 * @property location The geographical location of the server.
 * @property pvpType The type of PvP in this world.
 * @property battlEyeType The type of BattlEye protection present in this world.
 * @property battlEyeStartedOn The date when BattlEye was implemented in this world.
 * @property transferType The type of transfer restrictions this world has.
 * @property isPremiumRestricted Whether the world can only be played by premium account characters or not.
 * @property isExperimental Whether the world is experimental or not.
 */
@Serializable
public data class WorldEntry(
    override val name: String,
    val isOnline: Boolean,
    val onlinePlayersCount: Int,
    val location: String,
    val pvpType: PvpType,
    val battlEyeType: BattlEyeType,
    val battlEyeStartedOn: LocalDate?,
    val transferType: TransferType,
    val isPremiumRestricted: Boolean,
    val isExperimental: Boolean,
) : BaseWorld
