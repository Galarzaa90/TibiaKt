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
import com.galarzaa.tibiakt.core.section.community.house.model.HouseEntry
import com.galarzaa.tibiakt.core.section.community.house.model.HouseOrder
import com.galarzaa.tibiakt.core.section.community.house.model.HouseStatus
import com.galarzaa.tibiakt.core.section.community.house.model.HouseType
import com.galarzaa.tibiakt.core.section.community.house.model.HousesSection
import kotlin.time.Duration

@BuilderDsl
public inline fun housesSection(block: HousesSectionBuilder.() -> Unit): HousesSection =
    HousesSectionBuilder().apply(block).build()

@BuilderDsl
public inline fun housesSectionBuilder(block: HousesSectionBuilder.() -> Unit): HousesSectionBuilder =
    HousesSectionBuilder().apply(block)

/** Builder for [HousesSection] instances. */
@BuilderDsl
public class HousesSectionBuilder : TibiaKtBuilder<HousesSection> {
    public var world: String? = null
    public var town: String? = null
    public var status: HouseStatus? = null
    public var type: HouseType = HouseType.HOUSE
    public var order: HouseOrder = HouseOrder.NAME
    public val entries: MutableList<HouseEntry> = mutableListOf()


    public fun addEntry(
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

    public fun addEntry(houseEntry: HouseEntry): HousesSectionBuilder = apply { entries.add(houseEntry) }

    @BuilderDsl
    public fun addEntry(block: HouseEntryBuilder.() -> Unit): Boolean =
        entries.add(HouseEntryBuilder().apply(block).build())

    override fun build(): HousesSection = HousesSection(
        world = world ?: error("world is required"),
        town = town ?: error("town is required"),
        type = type,
        status = status,
        order = order,
        entries = entries
    )

    public class HouseEntryBuilder : TibiaKtBuilder<HouseEntry> {
        public var houseId: Int? = null
        public var name: String? = null
        public var size: Int? = null
        public var rent: Int? = null
        public var town: String? = null
        public var world: String? = null
        public var type: HouseType? = null
        public var status: HouseStatus? = null
        public var highestBid: Int? = null
        public var timeLeft: Duration? = null

        override fun build(): HouseEntry = HouseEntry(
            houseId = houseId ?: error("houseId is required"),
            name = name ?: error("name is required"),
            size = size ?: error("size is required"),
            rent = rent ?: error("rent is required"),
            town = town ?: error("town is required"),
            world = world ?: error("world is required"),
            type = type ?: error("type is required"),
            status = status ?: error("status is required"),
            highestBid = highestBid,
            timeLeft = timeLeft,
        )
    }
}
