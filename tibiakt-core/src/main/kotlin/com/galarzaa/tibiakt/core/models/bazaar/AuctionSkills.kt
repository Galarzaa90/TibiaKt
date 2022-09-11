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
public data class AuctionSkills(
    val axeFighting: Double,
    val clubFighting: Double,
    val distanceFighting: Double,
    val fishing: Double,
    val fistFighting: Double,
    val magicLevel: Double,
    val shielding: Double,
    val swordFighting: Double,
)
