package com.galarzaa.tibiakt.core.models.bazaar

import kotlinx.serialization.Serializable

/**
 * The skill levels of the character in the auction.
 *
 * Decimal values represent the percentage of progress towards the next level.
 *
 * @property axeFighting The current Axe Fighting level.
 * @property clubFighting The current Club Fighting level.
 * @property distanceFighting The current Distance Fighting level.
 * @property fishing The current Fishing level.
 * @property fistFighting The current Fist Fighting level.
 * @property magicLevel The current Magic Level.
 * @property shielding The current Shielding level.
 * @property swordFighting The current Sword Fighting level.
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