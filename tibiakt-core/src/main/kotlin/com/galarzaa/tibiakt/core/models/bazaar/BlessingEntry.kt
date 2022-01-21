package com.galarzaa.tibiakt.core.models.bazaar

import kotlinx.serialization.Serializable

/**
 * A blessing of an [Auction] character.
 *
 * @property name The name of the blessing
 * @property amount The amount of this blessing the character has.
 */
@Serializable
data class BlessingEntry(
    val name: String,
    val amount: Int,
)