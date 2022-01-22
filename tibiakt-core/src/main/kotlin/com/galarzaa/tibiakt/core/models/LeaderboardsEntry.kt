package com.galarzaa.tibiakt.core.models

import com.galarzaa.tibiakt.core.utils.getCharacterUrl
import kotlinx.serialization.Serializable

/**
 * An entry in the [LeaderboardsEntry].
 *
 * @property name The name of the character. Will be null if the character [isDeleted].
 * @property rank The rank of the character.
 * @property dromeLevel The Drome level of the character.
 */
@Serializable
data class LeaderboardsEntry(
    val rank: Int,
    val name: String?,
    val dromeLevel: Int,
) {
    /**
     * Whether the character is deleted or not.
     */
    val isDeleted: Boolean get() = name == null

    /**
     * The URL to the character's page. If the character is deleted it will be null.
     */
    val url: String? get() = name?.let { getCharacterUrl(it) }
}
