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
import com.galarzaa.tibiakt.core.models.world.WorldEntry
import com.galarzaa.tibiakt.core.models.world.WorldOverview
import com.galarzaa.tibiakt.core.utils.BuilderDsl
import kotlinx.datetime.Instant
import java.time.LocalDate

@BuilderDsl
public inline fun worldOverviewBuilder(block: WorldOverviewBuilder.() -> Unit): WorldOverviewBuilder =
    WorldOverviewBuilder().apply(block)

@BuilderDsl
public inline fun worldOverview(block: WorldOverviewBuilder.() -> Unit): WorldOverview =
    worldOverviewBuilder(block).build()


/** Builder for world overview. */
@BuilderDsl
public class WorldOverviewBuilder : TibiaKtBuilder<WorldOverview> {
    public var overallMaximumCount: Int? = null
    public var overallMaximumCountDateTime: Instant? = null
    public val worlds: MutableList<WorldEntry> = mutableListOf()
    public val tournamentWorlds: MutableList<WorldEntry> = mutableListOf()


    public fun addWorld(world: WorldEntry): Boolean = worlds.add(world)

    @BuilderDsl
    public fun addWorld(block: WorldEntryBuilder.() -> Unit): Boolean =
        worlds.add(WorldEntryBuilder().apply(block).build())

    override fun build(): WorldOverview {
        return WorldOverview(
            overallMaximumCount = overallMaximumCount
                ?: error("overallMaximumCount is required"),
            overallMaximumCountDateTime = overallMaximumCountDateTime
                ?: error("overallMaximumCountDateTime is required"),
            worlds = worlds,
            tournamentWorlds = tournamentWorlds
        )
    }

    /** A builder for world entries. */
    @BuilderDsl
    public class WorldEntryBuilder : TibiaKtBuilder<WorldEntry> {
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
        public var onlineRecordDateTime: Instant? = null


        override fun build(): WorldEntry {
            return WorldEntry(
                name = name ?: error("name is required"),
                isOnline = isOnline,
                onlineCount = onlineCount,
                location = location ?: error("location is required"),
                pvpType = pvpType ?: error("pvpType is required"),
                battlEyeType = battlEyeType,
                battlEyeStartDate = battlEyeStartDate,
                transferType = transferType,
                isPremiumRestricted = isPremiumRestricted,
                isExperimental = isExperimental,
            )
        }
    }
}
