package com.galarzaa.tibiakt.core.builders

import com.galarzaa.tibiakt.core.enums.HouseStatus
import com.galarzaa.tibiakt.core.enums.HouseType
import com.galarzaa.tibiakt.core.models.house.House
import com.galarzaa.tibiakt.core.utils.BuilderDsl
import kotlinx.datetime.Instant

@BuilderDsl
public inline fun house(block: HouseBuilder.() -> Unit): House = HouseBuilder().apply(block).build()

@BuilderDsl
public inline fun houseBuilder(block: HouseBuilder.() -> Unit): HouseBuilder = HouseBuilder().apply(block)


@BuilderDsl
public class HouseBuilder : TibiaKtBuilder<House>() {
    public var houseId: Int? = null
    public var name: String? = null
    public var size: Int? = null
    public var type: HouseType? = null
    public var beds: Int? = null
    public var rent: Int? = null
    public var world: String? = null
    public var status: HouseStatus? = null
    public var paidUntil: Instant? = null
    public var owner: String? = null
    public var movingDate: Instant? = null
    public var transferPrice: Int? = null
    public var transferAccepted: Boolean? = null
    public var transferRecipient: String? = null
    public var highestBid: Int? = null
    public var highestBidder: String? = null
    public var auctionEnd: Instant? = null

    override fun build(): House = House(
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
