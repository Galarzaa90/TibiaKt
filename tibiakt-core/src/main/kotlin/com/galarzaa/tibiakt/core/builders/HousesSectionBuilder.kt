package com.galarzaa.tibiakt.core.builders

import com.galarzaa.tibiakt.core.enums.HouseOrder
import com.galarzaa.tibiakt.core.enums.HouseStatus
import com.galarzaa.tibiakt.core.enums.HouseType
import com.galarzaa.tibiakt.core.models.house.HouseEntry
import com.galarzaa.tibiakt.core.models.house.HousesSection
import java.time.Duration

class HousesSectionBuilder {
    private var world: String? = null
    private var town: String? = null
    private var status: HouseStatus? = null
    private var type: HouseType = HouseType.HOUSE
    private var order: HouseOrder? = null
    private val entries: MutableList<HouseEntryBuilder> = mutableListOf()

    fun world(world: String) = apply { this.world = world }
    fun town(town: String) = apply { this.town = town }
    fun status(status: HouseStatus?) = apply { this.status = status }
    fun type(type: HouseType) = apply { this.type = type }
    fun order(order: HouseOrder?) = apply { this.order = order }
    fun addEntry(
        name: String,
        size: Int,
        rent: Int,
        status: HouseStatus,
        houseId: Int,
        highestBid: Int? = null,
        timeLeft: Duration? = null
    ) = apply {
        entries.add(
            HouseEntryBuilder()
                .name(name)
                .size(size)
                .rent(rent)
                .status(status)
                .houseId(houseId)
                .highestBid(highestBid)
                .timeLeft(timeLeft)
        )
    }

    fun addEntry(houseBuilder: HouseEntryBuilder) = apply { entries.add(houseBuilder) }

    fun build() = HousesSection(
        world = world ?: throw IllegalStateException("world is required"),
        town = town ?: throw IllegalStateException("town is required"),
        type = type,
        status = status,
        order = order ?: HouseOrder.NAME,
        entries = entries.map { it.town(town!!).world(world!!).type(type).build() },
    )

    inner class HouseEntryBuilder {
        private var houseId: Int? = null
        private var name: String? = null
        private var size: Int? = null
        private var rent: Int? = null
        private var town: String? = null
        private var world: String? = null
        private var type: HouseType? = null
        private var status: HouseStatus? = null
        private var highestBid: Int? = null
        private var timeLeft: Duration? = null

        fun houseId(houseId: Int) = apply { this.houseId = houseId }
        fun name(name: String) = apply { this.name = name }
        fun size(size: Int) = apply { this.size = size }
        fun rent(rent: Int) = apply { this.rent = rent }
        fun town(town: String) = apply { this.town = town }
        fun world(world: String) = apply { this.world = world }
        fun type(type: HouseType) = apply { this.type = type }
        fun status(status: HouseStatus) = apply { this.status = status }
        fun highestBid(highestBid: Int?) = apply { this.highestBid = highestBid }
        fun timeLeft(timeLeft: Duration?) = apply { this.timeLeft = timeLeft }

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