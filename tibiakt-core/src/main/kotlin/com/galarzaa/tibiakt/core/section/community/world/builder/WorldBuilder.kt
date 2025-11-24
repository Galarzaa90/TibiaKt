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
import com.galarzaa.tibiakt.core.builder.requireField
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
internal inline fun worldBuilder(block: WorldBuilder.() -> Unit): WorldBuilder = WorldBuilder().apply(block)

@BuilderDsl
internal inline fun world(block: WorldBuilder.() -> Unit): World = worldBuilder(block).build()

/** Builder for [World] instances. */
@BuilderDsl
internal class WorldBuilder : TibiaKtBuilder<World> {
    var name: String? = null
    var isOnline: Boolean = false
    var onlinePlayersCount: Int = 0
    var location: String? = null
    var pvpType: PvpType? = null
    var battlEyeType: BattlEyeType = BattlEyeType.UNPROTECTED
    var battlEyeStartedOn: LocalDate? = null
    var transferType: TransferType = TransferType.REGULAR
    var isPremiumRestricted: Boolean = false
    var isExperimental: Boolean = false
    var onlineRecordCount: Int = 0
    var onlineRecordAt: Instant? = null
    var createdOn: YearMonth? = null
    val worldQuests: MutableList<String> = mutableListOf()
    val onlinePlayers: MutableList<OnlineCharacter> = mutableListOf()

    fun worldQuest(quest: String): Boolean = worldQuests.add(quest)
    fun addOnlinePlayer(name: String, level: Int, vocation: Vocation): Boolean =
        onlinePlayers.add(OnlineCharacter(name, level, vocation))


    override fun build(): World {
        return World(
            name = requireField(name, "name"),
            isOnline = isOnline,
            onlinePlayersCount = onlinePlayersCount,
            location = requireField(location, "location"),
            pvpType = requireField(pvpType, "pvpType"),
            battlEyeType = battlEyeType,
            battlEyeStartedOn = battlEyeStartedOn,
            transferType = transferType,
            isPremiumRestricted = isPremiumRestricted,
            onlineRecordCount = onlineRecordCount,
            onlineRecordAt = requireField(onlineRecordAt, "onlineRecordAt"),
            isExperimental = isExperimental,
            createdOn = requireField(createdOn, "createdOn"),
            onlinePlayers = onlinePlayers,
            worldQuests = worldQuests,
        )
    }
}
