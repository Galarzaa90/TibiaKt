@file:UseSerializers(InstantSerializer::class)

package com.galarzaa.tibiakt.core.models.bazaar

import com.galarzaa.tibiakt.core.enums.AuctionStatus
import com.galarzaa.tibiakt.core.enums.BidType
import com.galarzaa.tibiakt.core.enums.Sex
import com.galarzaa.tibiakt.core.enums.Vocation
import com.galarzaa.tibiakt.core.serializers.InstantSerializer
import com.galarzaa.tibiakt.core.utils.getAuctionUrl
import com.galarzaa.tibiakt.core.utils.getCharacterUrl
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers

/**
 * An auction in the [CharacterBazaar]
 *
 * @property name The name of the character on auction.
 * @property auctionId The ID of the auction.
 * @property level The level of the character.
 * @property world The world of the character.
 * @property vocation The vocation of the character.
 * @property sex The character's sex.
 * @property outfit The current outfit the character is wearing.
 * @property displayedItems A list of items selected for display.
 * @property salesArguments Arguments selected by the auction author.
 * @property auctionStart The date when the auction started.
 * @property auctionEnd The date when the auction ends.
 * @property bid The current bid on the auction.
 * @property bidType The type of bid.
 * @property status The current status of the auction.
 * @property details The details of the auction.
 */
@Serializable
data class Auction(
    val name: String,
    val auctionId: Int,
    val level: Int,
    val world: String,
    val vocation: Vocation,
    val sex: Sex,
    val outfit: OutfitImage,
    val displayedItems: List<ItemEntry>,
    val salesArguments: List<SalesArgument>,
    val auctionStart: Instant,
    val auctionEnd: Instant,
    val bid: Int,
    val bidType: BidType,
    val status: AuctionStatus,
    val details: AuctionDetails?,
) {
    /** URL to the auction */
    val url: String get() = getAuctionUrl(auctionId)

    /** URL to the character being sold */
    val characterUrl: String get() = getCharacterUrl(name)
}