package com.galarzaa.tibiakt.core.models

import com.galarzaa.tibiakt.core.enums.BidType
import com.galarzaa.tibiakt.core.enums.Vocation
import java.time.Instant

interface BaseAuction {
    val name: String
    val auctionId: Int
    val level: Int
    val world: String
    val vocation: Vocation
    val sex: String
    val outfit: DisplayOutfit
    val displayedItems: List<DisplayItem>
    val salesArguments: List<SalesArgument>
    val auctionStart: Instant
    val auctionEnd: Instant
    val bid: Int
    val bidType: BidType
}