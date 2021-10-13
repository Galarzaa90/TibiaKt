package com.galarzaa.tibiakt.core.builders

import com.galarzaa.tibiakt.core.enums.HouseOrder
import com.galarzaa.tibiakt.core.enums.HouseStatus
import com.galarzaa.tibiakt.core.enums.HouseType
import com.galarzaa.tibiakt.core.models.HouseEntry
import com.galarzaa.tibiakt.core.models.HousesSection

class HousesSectionBuilder {
    private var world: String? = null
    private var town: String? = null
    private var status: HouseStatus? = null
    private var type: HouseType = HouseType.HOUSE
    private var order: HouseOrder? = null
    private val entries: MutableList<HouseEntry> = mutableListOf()

    fun world(world: String) = apply { this.world = world }
    fun town(town: String) = apply { this.town = town }
    fun status(status: HouseStatus?) = apply { this.status = status }
    fun type(type: HouseType) = apply { this.type = type }
    fun order(order: HouseOrder?) = apply { this.order = order }

    fun build() = HousesSection(
        world = world ?: throw IllegalStateException("world is required"),
        town = town ?: throw IllegalStateException("town is required"),
        type = type,
        status = status,
        order = order ?: HouseOrder.NAME,
        entries = entries,
    )
}