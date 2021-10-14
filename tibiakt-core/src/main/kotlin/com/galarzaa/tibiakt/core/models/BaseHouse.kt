package com.galarzaa.tibiakt.core.models

import com.galarzaa.tibiakt.core.utils.getHouseUrl

/**
 * @property houseId The internal ID of the house.
 * @property world The world where this house is located.
 */
interface BaseHouse {
    val houseId: Int
    val world: String
}

val BaseHouse.url: String
    get() = getHouseUrl(world, houseId)

val BaseHouse.imageUrl: String
    get() = "https://static.tibia.com/images/houses/house_$houseId.png"