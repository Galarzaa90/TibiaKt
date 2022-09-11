package com.galarzaa.tibiakt.core.models.bazaar

import com.galarzaa.tibiakt.core.utils.getStaticFileUrl
import kotlinx.serialization.Serializable

/**
 * A mount in an [Auction] character.
 *
 * @property name The name of the mount.
 * @property mountId The internal ID of the mount.
 */
@Serializable
public data class MountEntry(
    val name: String,
    val mountId: Int,
) {
    /**
     * The URL to the mount's image.
     */
    val imageUrl: String get() = getStaticFileUrl("images", "charactertrade", "mounts", "$mountId.gif")
}
