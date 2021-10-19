@file:UseSerializers(InstantSerializer::class)

package com.galarzaa.tibiakt.core.models

import com.galarzaa.tibiakt.core.enums.AuctionStatus
import com.galarzaa.tibiakt.core.enums.BidType
import com.galarzaa.tibiakt.core.enums.Vocation
import com.galarzaa.tibiakt.core.utils.InstantSerializer
import com.galarzaa.tibiakt.core.utils.getAuctionUrl
import com.galarzaa.tibiakt.core.utils.getCharacterUrl
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
    val status: AuctionStatus,
    val details: AuctionDetails?,
) {
    /** URL to the auction */
    val url get() = getAuctionUrl(auctionId)

    /** URL to the character being sold */
    val characterUrl get() = getCharacterUrl(name)
}