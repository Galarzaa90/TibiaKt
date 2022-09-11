package com.galarzaa.tibiakt.core.models.house

import com.galarzaa.tibiakt.core.utils.getHouseUrl
import com.galarzaa.tibiakt.core.utils.getStaticFileUrl

/**
 * Base interface for house related classes.
 *
 * @property houseId The internal ID of the house.
 * @property world The world where this house is located.
 */
public interface BaseHouse {
    public val houseId: Int
    public val world: String

    /**
     * The URL to the house's information page.
     */
    public val url: String get() = getHouseUrl(world, houseId)

    /**
     * URL to the house's image.
     */
    public val imageUrl: String get() = getStaticFileUrl("images", "houses", "house_$houseId.png")
}
