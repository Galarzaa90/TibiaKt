package com.galarzaa.tibiakt.utils

import com.galarzaa.tibiakt.models.CharacterHouse

val CharacterHouse.url: String
    get() = getHouseUrl(world, houseId)