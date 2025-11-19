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

package com.galarzaa.tibiakt.core.section.community.world.builder

import com.galarzaa.tibiakt.core.builder.BuilderDsl
import com.galarzaa.tibiakt.core.builder.TibiaKtBuilder
import com.galarzaa.tibiakt.core.domain.character.Vocation
import com.galarzaa.tibiakt.core.domain.world.BattlEyeType
import com.galarzaa.tibiakt.core.domain.world.PvpType
import com.galarzaa.tibiakt.core.domain.world.TransferType
import com.galarzaa.tibiakt.core.section.community.world.model.OnlineCharacter
import com.galarzaa.tibiakt.core.section.community.world.model.World
import kotlinx.datetime.LocalDate
import kotlinx.datetime.YearMonth
import kotlin.time.Instant

@BuilderDsl
public inline fun worldBuilder(block: WorldBuilder.() -> Unit): WorldBuilder = WorldBuilder().apply(block)

@BuilderDsl
public inline fun world(block: WorldBuilder.() -> Unit): World = worldBuilder(block).build()

/** Builder for [World] instances. */
@BuilderDsl
public class WorldBuilder : TibiaKtBuilder<World> {
    public var name: String? = null
    public var isOnline: Boolean = false
    public var onlinePlayersCount: Int = 0
    public var location: String? = null
    public var pvpType: PvpType? = null
    public var battlEyeType: BattlEyeType = BattlEyeType.UNPROTECTED
    public var battlEyeStartedOn: LocalDate? = null
    public var transferType: TransferType = TransferType.REGULAR
    public var isPremiumRestricted: Boolean = false
    public var isExperimental: Boolean = false
    public var onlineRecordCount: Int = 0
    public var onlineRecordAt: Instant? = null
    public var createdOn: YearMonth? = null
    public val worldQuests: MutableList<String> = mutableListOf()
    public val onlinePlayers: MutableList<OnlineCharacter> = mutableListOf()

    public fun worldQuest(quest: String): Boolean = worldQuests.add(quest)
    public fun addOnlinePlayer(name: String, level: Int, vocation: Vocation): Boolean =
        onlinePlayers.add(OnlineCharacter(name, level, vocation))


    override fun build(): World {
        return World(
            name = name ?: error("name is required"),
            isOnline = isOnline,
            onlinePlayersCount = onlinePlayersCount,
            location = location ?: error("location is required"),
            pvpType = pvpType ?: error("pvpType is required"),
            battlEyeType = battlEyeType,
            battlEyeStartedOn = battlEyeStartedOn,
            transferType = transferType,
            isPremiumRestricted = isPremiumRestricted,
            onlineRecordCount = onlineRecordCount,
            onlineRecordAt = onlineRecordAt
                ?: error("onlineRecordAt is required"),
            isExperimental = isExperimental,
            createdOn = createdOn ?: error("createdOn is required"),
            onlinePlayers = onlinePlayers,
            worldQuests = worldQuests,
        )
    }
}
