package com.galarzaa.tibiakt.core.models

import com.galarzaa.tibiakt.core.utils.getStaticFileUrl
import kotlinx.serialization.Serializable

@Serializable
data class DisplayOutfit(
    val outfitId: Int,
    val addons: Int,
) {
    val imageUrl get() = getStaticFileUrl("images", "charactertrade", "outfits", "${outfitId}_$addons.gif")
}