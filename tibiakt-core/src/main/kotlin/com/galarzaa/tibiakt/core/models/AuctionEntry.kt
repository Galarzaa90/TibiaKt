package com.galarzaa.tibiakt.core.models

import com.galarzaa.tibiakt.core.enums.BidType
import com.galarzaa.tibiakt.core.enums.Vocation
import java.time.Instant

data class AuctionEntry(
    override val name: String,
    override val auctionId: Int,
    override val level: Int,
    override val world: String,
    override val vocation: Vocation,
    override val sex: String,
    override val outfit: DisplayOutfit,
    override val displayedItems: List<DisplayItem>,
    override val salesArguments: List<SalesArgument>,
    override val auctionStart: Instant,
    override val auctionEnd: Instant,
    override val bid: Int,
    override val bidType: BidType,
) : BaseAuction