package com.galarzaa.tibiakt.core.models.bazaar

import kotlinx.serialization.Serializable

/**
 * An outfit owned or unlocked by a character in an [Auction].
 *
 * @property name The name of the outfit.
 */
@Serializable
public data class OutfitEntry(
    val name: String,
    override val outfitId: Int,
    override val addons: Int,
) : BaseOutfit
