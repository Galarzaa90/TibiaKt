@file:UseSerializers(InstantSerializer::class)
package com.galarzaa.tibiakt.core.models

import com.galarzaa.tibiakt.core.enums.BidType
import com.galarzaa.tibiakt.core.enums.Vocation
import com.galarzaa.tibiakt.core.utils.InstantSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.time.Instant

@Serializable
data class Auction(
    val name: String,
    val auctionId: Int,
    val level: Int,
    val world: String,
    val vocation: Vocation,
    val sex: String,
    val outfit: DisplayOutfit,
    val displayedItems: List<DisplayItem>,
    val salesArguments: List<SalesArgument>,
    val auctionStart: Instant,
    val auctionEnd: Instant,
    val bid: Int,
    val bidType: BidType,
    val details: AuctionDetails,
)