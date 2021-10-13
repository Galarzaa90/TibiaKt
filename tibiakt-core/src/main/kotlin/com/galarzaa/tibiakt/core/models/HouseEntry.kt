package com.galarzaa.tibiakt.core.models

import com.galarzaa.tibiakt.core.enums.HouseStatus
import com.galarzaa.tibiakt.core.enums.HouseType
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
