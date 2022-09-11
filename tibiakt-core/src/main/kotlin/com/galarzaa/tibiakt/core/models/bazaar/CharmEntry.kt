package com.galarzaa.tibiakt.core.models.bazaar

import kotlinx.serialization.Serializable

/**
 * A charm of an [Auction]ed character.
 *
 * @property name The name of the charm
 * @property cost The cost of the charm in charm points.
 */
@Serializable
public data class CharmEntry(
    val name: String,
    val cost: Int,
)
