package com.galarzaa.tibiakt.core.models.bazaar

import com.galarzaa.tibiakt.core.utils.getStaticFileUrl
import kotlinx.serialization.Serializable

/**
 * A familiar of an [Auction] character.
 *
 * @property name The name of the familiar.
 * @property familiarId The internal ID of the familiar.
 */
@Serializable
data class FamiliarEntry(
    val name: String,
    val familiarId: Int,
) {
    /**
     * The URL to the familiar's image.
     */
    val imageUrl get() = getStaticFileUrl("images", "charactertrade", "summons", "$familiarId.gif")
}