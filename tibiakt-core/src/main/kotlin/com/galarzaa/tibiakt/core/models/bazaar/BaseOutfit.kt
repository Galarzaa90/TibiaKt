package com.galarzaa.tibiakt.core.models.bazaar

import com.galarzaa.tibiakt.core.utils.getStaticFileUrl

/**
 * A base outfit displayed in auctions.
 *
 * @property outfitId The internal ID of the outfit.
 * @property addons The selected or unlocked addons.
 */
interface BaseOutfit {
    val outfitId: Int
    val addons: Int

    /**
     * The URL to the outfit's image.
     */
    val imageUrl get() = getStaticFileUrl("images", "charactertrade", "outfits", "${outfitId}_$addons.gif")
}