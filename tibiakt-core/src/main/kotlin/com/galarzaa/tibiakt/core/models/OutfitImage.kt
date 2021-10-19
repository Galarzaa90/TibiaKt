package com.galarzaa.tibiakt.core.models

import kotlinx.serialization.Serializable

/**
 * The currently selected outfit of a character in an [Auction].
 */
@Serializable
data class OutfitImage(
    override val outfitId: Int,
    override val addons: Int,
) : BaseOutfit