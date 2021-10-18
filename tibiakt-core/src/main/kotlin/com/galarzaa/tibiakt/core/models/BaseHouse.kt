package com.galarzaa.tibiakt.core.models

import com.galarzaa.tibiakt.core.utils.getHouseUrl

/**
 * Base interface for house related classes.
 * @property houseId The internal ID of the house.
 * @property world The world where this house is located.
 */
interface BaseHouse {
    val houseId: Int
    val world: String

    val url: String
        get() = getHouseUrl(world, houseId)

    val imageUrl: String
        get() = "https://static.tibia.com/images/houses/house_$houseId.png"
}