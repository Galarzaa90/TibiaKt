package com.galarzaa.tibiakt.core.models

import kotlinx.serialization.Serializable

@Serializable
data class AuctionSkills(
    val axeFighting: Float,
    val clubFighting: Float,
    val distanceFighting: Float,
    val fishing: Float,
    val magicLevel: Float,
    val shielding: Float,
    val swordFighting: Float,
)