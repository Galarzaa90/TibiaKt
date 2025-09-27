/*
 * Copyright © 2022 Allan Galarza
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

package com.galarzaa.tibiakt.core.builders

import com.galarzaa.tibiakt.core.enums.BattlEyeType
import com.galarzaa.tibiakt.core.enums.PvpType
import com.galarzaa.tibiakt.core.enums.TransferType
import com.galarzaa.tibiakt.core.enums.Vocation
import com.galarzaa.tibiakt.core.models.world.OnlineCharacter
import com.galarzaa.tibiakt.core.models.world.World
import com.galarzaa.tibiakt.core.builders.BuilderDsl
import kotlin.time.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.YearMonth

@BuilderDsl
public inline fun worldBuilder(block: WorldBuilder.() -> Unit): WorldBuilder = WorldBuilder().apply(block)

@BuilderDsl
public inline fun world(block: WorldBuilder.() -> Unit): World = worldBuilder(block).build()

/** Builder for [World] instances. */
@BuilderDsl
public class WorldBuilder : TibiaKtBuilder<World> {
    public var name: String? = null
    public var isOnline: Boolean = false
    public var onlineCount: Int = 0
    public var location: String? = null
    public var pvpType: PvpType? = null
    public var battlEyeType: BattlEyeType = BattlEyeType.UNPROTECTED
    public var battlEyeStartDate: LocalDate? = null
    public var transferType: TransferType = TransferType.REGULAR
    public var isPremiumRestricted: Boolean = false
    public var isExperimental: Boolean = false
    public var onlineRecordCount: Int = 0
    public var onlineRecordDateTime: Instant? = null
    public var creationDate: YearMonth? = null
    public val worldQuests: MutableList<String> = mutableListOf()
    public val playersOnline: MutableList<OnlineCharacter> = mutableListOf()

    public fun worldQuest(quest: String): Boolean = worldQuests.add(quest)
    public fun addOnlinePlayer(name: String, level: Int, vocation: Vocation): Boolean =
        playersOnline.add(OnlineCharacter(name, level, vocation))


    override fun build(): World {
        return World(
            name = name ?: error("name is required"),
            isOnline = isOnline,
            onlineCount = onlineCount,
            location = location ?: error("location is required"),
            pvpType = pvpType ?: error("pvpType is required"),
            battlEyeType = battlEyeType,
            battlEyeStartDate = battlEyeStartDate,
            transferType = transferType,
            isPremiumRestricted = isPremiumRestricted,
            onlineRecordCount = onlineRecordCount,
            onlineRecordDateTime = onlineRecordDateTime
                ?: error("onlineRecordDateTime is required"),
            isExperimental = isExperimental,
            creationDate = creationDate ?: error("creationDate is required"),
            playersOnline = playersOnline,
            worldQuests = worldQuests,
        )
    }
}
