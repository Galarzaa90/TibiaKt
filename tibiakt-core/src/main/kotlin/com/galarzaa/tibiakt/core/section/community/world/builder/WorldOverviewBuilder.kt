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
import com.galarzaa.tibiakt.core.domain.world.BattlEyeType
import com.galarzaa.tibiakt.core.domain.world.PvpType
import com.galarzaa.tibiakt.core.domain.world.TransferType
import com.galarzaa.tibiakt.core.section.community.world.model.WorldEntry
import com.galarzaa.tibiakt.core.section.community.world.model.WorldOverview
import kotlinx.datetime.LocalDate
import kotlin.time.Instant

@BuilderDsl
internal inline fun worldOverviewBuilder(block: WorldOverviewBuilder.() -> Unit): WorldOverviewBuilder =
    WorldOverviewBuilder().apply(block)

@BuilderDsl
internal inline fun worldOverview(block: WorldOverviewBuilder.() -> Unit): WorldOverview =
    worldOverviewBuilder(block).build()


/** Builder for world overview. */
@BuilderDsl
internal class WorldOverviewBuilder : TibiaKtBuilder<WorldOverview> {
    var overallMaximumCount: Int? = null
    var overallMaximumCountDateTime: Instant? = null
    val worlds: MutableList<WorldEntry> = mutableListOf()

    fun addWorld(world: WorldEntry): Boolean = worlds.add(world)

    @BuilderDsl
    fun addWorld(block: WorldEntryBuilder.() -> Unit): Boolean =
        worlds.add(WorldEntryBuilder().apply(block).build())

    override fun build(): WorldOverview {
        return WorldOverview(
            overallMaximumCount = overallMaximumCount
                ?: error("overallMaximumCount is required"),
            overallMaximumCountAt = overallMaximumCountDateTime
                ?: error("overallMaximumCountDateTime is required"),
            worlds = worlds,
        )
    }

    /** A builder for world entries. */
    @BuilderDsl
    class WorldEntryBuilder : TibiaKtBuilder<WorldEntry> {
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
        var onlineRecordDateTime: Instant? = null


        override fun build(): WorldEntry {
            return WorldEntry(
                name = requireField(name, "name"),
                isOnline = isOnline,
                onlinePlayersCount = onlinePlayersCount,
                location = requireField(location, "location"),
                pvpType = requireField(pvpType, "pvpType"),
                battlEyeType = battlEyeType,
                battlEyeStartedOn = battlEyeStartedOn,
                transferType = transferType,
                isPremiumRestricted = isPremiumRestricted,
                isExperimental = isExperimental,
            )
        }
    }
}
