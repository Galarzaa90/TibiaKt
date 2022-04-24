package com.galarzaa.tibiakt.core.builders

import com.galarzaa.tibiakt.core.enums.HouseOrder
import com.galarzaa.tibiakt.core.enums.HouseStatus
import com.galarzaa.tibiakt.core.enums.HouseType
import com.galarzaa.tibiakt.core.models.house.HouseEntry
import com.galarzaa.tibiakt.core.models.house.HousesSection
import com.galarzaa.tibiakt.core.utils.BuilderDsl
import java.time.Duration

@BuilderDsl
inline fun housesSection(block: HousesSectionBuilder.() -> Unit) = HousesSectionBuilder().apply(block).build()

@BuilderDsl
inline fun housesSectionBuilder(block: HousesSectionBuilder.() -> Unit) = HousesSectionBuilder().apply(block)

@BuilderDsl
class HousesSectionBuilder {
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
    ) = apply {
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

    fun addEntry(houseEntry: HouseEntry) = apply { entries.add(houseEntry) }

    @BuilderDsl
    fun addEntry(block: HouseEntryBuilder.() -> Unit) = entries.add(HouseEntryBuilder().apply(block).build())

    fun build() = HousesSection(
        world = world ?: throw IllegalStateException("world is required"),
        town = town ?: throw IllegalStateException("town is required"),
        type = type,
        status = status,
        order = order ?: HouseOrder.NAME,
        entries = entries
    )

    inner class HouseEntryBuilder {
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

        fun build() = HouseEntry(
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