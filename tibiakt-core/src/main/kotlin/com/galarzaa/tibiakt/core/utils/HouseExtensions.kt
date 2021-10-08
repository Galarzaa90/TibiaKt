package com.galarzaa.tibiakt.core.utils

import com.galarzaa.tibiakt.core.models.BaseHouse

val BaseHouse.url: String
    get() = getHouseUrl(world, houseId)

val BaseHouse.imageUrl: String
    get() = "https://static.tibia.com/images/houses/house_$houseId.png"