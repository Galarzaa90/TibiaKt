package com.galarzaa.tibiakt.core.builders

import com.galarzaa.tibiakt.core.enums.HouseStatus
import com.galarzaa.tibiakt.core.enums.HouseType
import com.galarzaa.tibiakt.core.models.house.House
import com.galarzaa.tibiakt.core.utils.BuilderDsl
import java.time.Instant

@BuilderDsl
inline fun house(block: HouseBuilder.() -> Unit) = HouseBuilder().apply(block).build()

@BuilderDsl
inline fun houseBuilder(block: HouseBuilder.() -> Unit) = HouseBuilder().apply(block)

class HouseBuilder : TibiaKtBuilder<House>() {
    var houseId: Int? = null
    var name: String? = null
    var size: Int? = null
    var type: HouseType? = null
    var beds: Int? = null
    var rent: Int? = null
    var world: String? = null
    var status: HouseStatus? = null
    var paidUntil: Instant? = null
    var owner: String? = null
    var movingDate: Instant? = null
    var transferPrice: Int? = null
    var transferAccepted: Boolean? = null
    var transferRecipient: String? = null
    var highestBid: Int? = null
    var highestBidder: String? = null
    var auctionEnd: Instant? = null


    override fun build() = House(
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
        movingDate = movingDate,
        transferPrice = transferPrice,
        transferAccepted = transferAccepted,
        transferRecipient = transferRecipient,
        highestBid = highestBid,
        highestBidder = highestBidder,
        auctionEnd = auctionEnd,
    )
}
