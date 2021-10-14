package com.galarzaa.tibiakt.core.builders

import com.galarzaa.tibiakt.core.enums.HouseStatus
import com.galarzaa.tibiakt.core.enums.HouseType
import com.galarzaa.tibiakt.core.models.House
import java.time.Instant

class HouseBuilder {
    private var houseId: Int? = null
    private var name: String? = null
    private var size: Int? = null
    private var type: HouseType? = null
    private var beds: Int? = null
    private var rent: Int? = null
    private var world: String? = null
    private var status: HouseStatus? = null
    private var paidUntil: Instant? = null
    private var owner: String? = null
    private var transferDate: Instant? = null
    private var transferPrice: Int? = null
    private var transferAccepted: Boolean? = null
    private var highestBid: Int? = null
    private var highestBidder: String? = null
    private var auctionEnd: Instant? = null

    fun name(name: String) = apply { this.name = name }
    fun size(size: Int) = apply { this.size = size }
    fun rent(rent: Int) = apply { this.rent = rent }
    fun beds(beds: Int) = apply { this.beds = beds }
    fun world(world: String) = apply { this.world = world }

    fun build() = House(
        houseId = houseId ?: throw IllegalStateException("houseId is required"),
        name = name ?: throw IllegalStateException("name is required"),
        size = size ?: throw IllegalStateException("size is required"),
        type = type ?: throw IllegalStateException("type is required"),
        beds = beds ?: throw IllegalStateException("beds is required"),
        rent = rent ?: throw IllegalStateException("rent is required"),
        world = world ?: throw IllegalStateException("world is required"),
        status = status ?: throw IllegalStateException("status is required"),
        paidUntil = paidUntil,
        owner = owner,
        transferDate = transferDate,
        transferPrice = transferPrice,
        transferAccepted = transferAccepted,
        highestBid = highestBid,
        highestBidder = highestBidder,
        auctionEnd = auctionEnd,
    )
}
