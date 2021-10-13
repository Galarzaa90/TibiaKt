package com.galarzaa.tibiakt.core.models

/**
 * @property houseId The internal ID of the house.
 * @property world The world where this house is located.
 */
interface BaseHouse {
    val houseId: Int
    val world: String
}