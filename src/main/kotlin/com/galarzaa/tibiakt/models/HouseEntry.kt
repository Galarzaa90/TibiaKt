package com.galarzaa.tibiakt.models

import kotlinx.serialization.Serializable

@Serializable
data class HouseEntry(
    override val houseId: Int,
    val name: String,
    val size: Int,
    val rent: Int,
    val town: String,
    override val world: String,
    val type: HouseType,
    val status: HouseStatus
) : BaseHouse
