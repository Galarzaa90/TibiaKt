package com.galarzaa.tibiakt.core.builders

import com.galarzaa.tibiakt.core.enums.HouseOrder
import com.galarzaa.tibiakt.core.enums.HouseStatus
import com.galarzaa.tibiakt.core.enums.HouseType
import com.galarzaa.tibiakt.core.models.house.HouseEntry
import com.galarzaa.tibiakt.core.models.house.HousesSection
import com.galarzaa.tibiakt.core.utils.BuilderDsl
import kotlin.time.Duration

@BuilderDsl
public inline fun housesSection(block: HousesSectionBuilder.() -> Unit): HousesSection =
    HousesSectionBuilder().apply(block).build()

@BuilderDsl
public inline fun housesSectionBuilder(block: HousesSectionBuilder.() -> Unit): HousesSectionBuilder =
    HousesSectionBuilder().apply(block)

@BuilderDsl
public class HousesSectionBuilder : TibiaKtBuilder<HousesSection>() {
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
        world = world ?: throw IllegalStateException("world is required"),
        town = town ?: throw IllegalStateException("town is required"),
        type = type,
        status = status,
        order = order,
        entries = entries
    )

    public class HouseEntryBuilder : TibiaKtBuilder<HouseEntry>() {
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
            houseId = houseId ?: throw IllegalStateException("houseId is required"),
            name = name ?: throw IllegalStateException("name is required"),
            size = size ?: throw IllegalStateException("size is required"),
            rent = rent ?: throw IllegalStateException("rent is required"),
            town = town ?: throw IllegalStateException("town is required"),
            world = world ?: throw IllegalStateException("world is required"),
            type = type ?: throw IllegalStateException("type is required"),
            status = status ?: throw IllegalStateException("status is required"),
            highestBid = highestBid,
            timeLeft = timeLeft,
        )
    }
}
