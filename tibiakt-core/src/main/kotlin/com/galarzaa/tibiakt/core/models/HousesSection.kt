package com.galarzaa.tibiakt.core.models

import com.galarzaa.tibiakt.core.enums.HouseOrder
import com.galarzaa.tibiakt.core.enums.HouseStatus
import com.galarzaa.tibiakt.core.enums.HouseType
import kotlinx.serialization.Serializable

@Serializable
data class HousesSection(
    val world: String,
    val town: String,
    val status: HouseStatus?,
    val type: HouseType,
    val order: HouseOrder,
    val entries: List<HouseEntry>,
)
