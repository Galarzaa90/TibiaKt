package com.galarzaa.tibiakt.core.models.bazaar

import com.galarzaa.tibiakt.core.utils.getStaticFileUrl

/**
 * A base outfit displayed in auctions.
 *
 * @property outfitId The internal ID of the outfit.
 * @property addons The selected or unlocked addons.
 */
public interface BaseOutfit {
    public val outfitId: Int
    public val addons: Int

    /**
     * The URL to the outfit's image.
     */
    public val imageUrl: String
        get() = getStaticFileUrl(
            "images",
            "charactertrade",
            "outfits",
            "${outfitId}_$addons.gif"
        )
}
