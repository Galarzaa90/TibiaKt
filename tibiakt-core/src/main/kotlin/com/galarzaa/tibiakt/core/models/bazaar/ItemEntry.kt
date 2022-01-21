package com.galarzaa.tibiakt.core.models.bazaar

import com.galarzaa.tibiakt.core.utils.getStaticFileUrl
import kotlinx.serialization.Serializable

/**
 * An in-game item in an [Auction].
 *
 * @property itemId The internal ID of the item.
 * @property name The name of the item.
 * @property description The description or flavor text of the item, if any.
 * @property count The amount of this item the character has.
 */
@Serializable
data class ItemEntry(
    val itemId: Int,
    val name: String,
    val description: String?,
    val count: Int,
) {
    /**
     * The URL to the item's image.
     */
    val imageUrl get() = getStaticFileUrl("images", "charactertrade", "objects", "$itemId.gif")
}