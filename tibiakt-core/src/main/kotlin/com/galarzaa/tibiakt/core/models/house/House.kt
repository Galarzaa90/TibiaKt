@file:UseSerializers(InstantSerializer::class)

package com.galarzaa.tibiakt.core.models.house

import com.galarzaa.tibiakt.core.enums.HouseStatus
import com.galarzaa.tibiakt.core.enums.HouseType
import com.galarzaa.tibiakt.core.serializers.InstantSerializer
import com.galarzaa.tibiakt.core.utils.getCharacterUrl
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers

/**
 * A house or guildhall.
 *
 * @property name The name of the house.
 * @property size The size of the house in square meters (tiles).
 * @property type The type of the house.
 * @property beds The maximum amount of beds that can be placed in there.
 * @property rent The monthly rent paid for this house in gold coins.
 * @property status The current status of the house.
 * @property paidUntil The date when the last paid rent is due.
 * @property owner The character that currently owns the house.
 * @property movingDate The date when the current owner will move out of the house.
 * @property transferPrice When the house is being transferred to someone else, the amount of gold paid for the transfer.
 * @property transferAccepted Whether the transfer has been accepted by the recipient or not.
 * @property transferRecipient The character that will receive the house.
 * @property highestBid If the house is on auction, the highest bid received, if any.
 * @property highestBidder The character that placed the currently highest bid.
 * @property auctionEnd The date and time when the auction will end.
 */
@Serializable
public data class House(
    override val houseId: Int,
    val name: String,
    val size: Int,
    val type: HouseType,
    val beds: Int,
    val rent: Int,
    override val world: String,
    val status: HouseStatus,
    val paidUntil: Instant?,
    val owner: String?,
    val movingDate: Instant?,
    val transferPrice: Int?,
    val transferAccepted: Boolean?,
    val transferRecipient: String?,
    val highestBid: Int?,
    val highestBidder: String?,
    val auctionEnd: Instant?,
) : BaseHouse {

    val ownerUrl: String? get() = owner?.let { getCharacterUrl(it) }
    val transferRecipientUrl: String? get() = transferRecipient?.let { getCharacterUrl(it) }
    val highestBidderUrl: String? get() = highestBidder?.let { getCharacterUrl(it) }
}
