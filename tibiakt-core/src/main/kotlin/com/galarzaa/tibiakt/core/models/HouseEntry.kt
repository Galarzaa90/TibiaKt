@file:UseSerializers(DurationSerializer::class)

package com.galarzaa.tibiakt.core.models

import com.galarzaa.tibiakt.core.enums.HouseStatus
import com.galarzaa.tibiakt.core.enums.HouseType
import com.galarzaa.tibiakt.core.utils.DurationSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.time.Duration

/**
 * A listed house in the Houses Section of tibia.com
 *
 * @property name The name of the house.
 * @property size The size of the house in SQM.
 * @property rent The monthly rent paid for this house.
 * @property town The town where this house is located or closest too.
 * @property type The type of the house.
 * @property status The current status of the house.
 * @property highestBid The current highest bid of this house, if it is being auctioned.
 * @property timeLeft The remaining time for the auction to end. Note that the resolution of this is either days or hours.
 */
@Serializable
data class HouseEntry(
    override val houseId: Int,
    val name: String,
    val size: Int,
    val rent: Int,
    val town: String,
    override val world: String,
    val type: HouseType,
    val status: HouseStatus,
    val highestBid: Int?,
    val timeLeft: Duration?
) : BaseHouse
