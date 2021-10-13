package com.galarzaa.tibiakt.core.utils

import com.galarzaa.tibiakt.core.models.BaseHouse
import com.galarzaa.tibiakt.core.models.HousesSection


val BaseHouse.url: String
    get() = getHouseUrl(world, houseId)

val BaseHouse.imageUrl: String
    get() = "https://static.tibia.com/images/houses/house_$houseId.png"

val HousesSection.url
    get() = getHousesSectionUrl(world, town, type, status, order)