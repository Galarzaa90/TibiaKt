package com.galarzaa.tibiakt.models

import kotlinx.serialization.Serializable

@Serializable
data class HousesSection(
    val world: String,
    val town: String,
    val status: HouseStatus?,
    val order: HouseOrder,
    val entries: List<HouseEntry>,
)
