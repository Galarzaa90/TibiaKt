package com.galarzaa.tibiakt.utils

import com.galarzaa.tibiakt.models.BaseHouse

val BaseHouse.url: String
    get() = getHouseUrl(world, houseId)

val BaseHouse.imageUrl: String
    get() = "https://static.tibia.com/images/houses/house_$houseId.png"