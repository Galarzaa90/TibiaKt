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

package com.galarzaa.tibiakt.core.section.community.house.builder

import com.galarzaa.tibiakt.core.builder.BuilderDsl
import com.galarzaa.tibiakt.core.builder.TibiaKtBuilder
import com.galarzaa.tibiakt.core.builder.requireField
import com.galarzaa.tibiakt.core.section.community.house.model.HouseEntry
import com.galarzaa.tibiakt.core.section.community.house.model.HouseOrder
import com.galarzaa.tibiakt.core.section.community.house.model.HouseStatus
import com.galarzaa.tibiakt.core.section.community.house.model.HouseType
import com.galarzaa.tibiakt.core.section.community.house.model.HousesSection
import kotlin.time.Duration

@BuilderDsl
internal inline fun housesSection(block: HousesSectionBuilder.() -> Unit): HousesSection =
    HousesSectionBuilder().apply(block).build()

@BuilderDsl
internal inline fun housesSectionBuilder(block: HousesSectionBuilder.() -> Unit): HousesSectionBuilder =
    HousesSectionBuilder().apply(block)

/** Builder for [HousesSection] instances. */
@BuilderDsl
internal class HousesSectionBuilder : TibiaKtBuilder<HousesSection> {
    var world: String? = null
    var town: String? = null
    var status: HouseStatus? = null
    var type: HouseType = HouseType.HOUSE
    var order: HouseOrder = HouseOrder.NAME
    val entries: MutableList<HouseEntry> = mutableListOf()


    fun addEntry(
        name: String,
        size: Int,
        rent: Int,
        status: HouseStatus,
        houseId: Int,
        highestBid: Int? = null,
        timeLeft: Duration? = null,
    ): HousesSectionBuilder = apply {
        entries.add(
            HouseEntryBuilder().apply {
                this.name = name
                this.size = size
                this.rent = rent
                this.status = status
                this.houseId = houseId
                this.highestBid = highestBid
                this.timeLeft = timeLeft
            }.build()
        )
    }

    fun addEntry(houseEntry: HouseEntry): HousesSectionBuilder = apply { entries.add(houseEntry) }

    @BuilderDsl
    fun addEntry(block: HouseEntryBuilder.() -> Unit): Boolean =
        entries.add(HouseEntryBuilder().apply(block).build())

    override fun build(): HousesSection = HousesSection(
        world = requireField(world, "world"),
        town = requireField(town, "town"),
        type = type,
        status = status,
        order = order,
        entries = entries
    )

    class HouseEntryBuilder : TibiaKtBuilder<HouseEntry> {
        var houseId: Int? = null
        var name: String? = null
        var size: Int? = null
        var rent: Int? = null
        var town: String? = null
        var world: String? = null
        var type: HouseType? = null
        var status: HouseStatus? = null
        var highestBid: Int? = null
        var timeLeft: Duration? = null

        override fun build(): HouseEntry = HouseEntry(
            houseId = requireField(houseId, "houseId"),
            name = requireField(name, "name"),
            size = requireField(size, "size"),
            rent = requireField(rent, "rent"),
            town = requireField(town, "town"),
            world = requireField(world, "world"),
            type = requireField(type, "type"),
            status = requireField(status, "status"),
            highestBid = highestBid,
            timeLeft = timeLeft,
        )
    }
}
