@file:UseSerializers(InstantSerializer::class)

package com.galarzaa.tibiakt.core.models

import com.galarzaa.tibiakt.core.enums.HouseStatus
import com.galarzaa.tibiakt.core.enums.HouseType
import com.galarzaa.tibiakt.core.utils.InstantSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.time.Instant

@Serializable
data class House(
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
    val transferDate: Instant?,
    val transferPrice: Int?,
    val transferAccepted: Boolean?,
    val highestBid: Int?,
    val highestBidder: String?,
    val auctionEnd: Instant?,
) : BaseHouse
