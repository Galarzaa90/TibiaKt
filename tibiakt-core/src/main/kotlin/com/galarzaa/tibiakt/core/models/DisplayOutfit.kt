package com.galarzaa.tibiakt.core.models

import kotlinx.serialization.Serializable

/**
 * An outfit owned or unlocked by a character in an [Auction].
 *
 * @property name The name of the outfit.
 */
@Serializable
data class DisplayOutfit(
    val name: String,
    override val outfitId: Int,
    override val addons: Int,
) : BaseOutfit


