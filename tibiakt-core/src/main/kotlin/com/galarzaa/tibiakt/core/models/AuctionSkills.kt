package com.galarzaa.tibiakt.core.models

import kotlinx.serialization.Serializable

/**
 * The skill levels of the character in the auction.
 */
@Serializable
data class AuctionSkills(
    val axeFighting: Float,
    val clubFighting: Float,
    val distanceFighting: Float,
    val fishing: Float,
    val fistFighting: Float,
    val magicLevel: Float,
    val shielding: Float,
    val swordFighting: Float,
)