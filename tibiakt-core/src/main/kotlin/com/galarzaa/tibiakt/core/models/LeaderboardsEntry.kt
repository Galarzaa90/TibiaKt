package com.galarzaa.tibiakt.core.models

import com.galarzaa.tibiakt.core.utils.getCharacterUrl
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
sealed class BaseLeaderboardEntry {
    abstract val rank: Int
    abstract val dromeLevel: Int
}

/**
 * An entry in the [LeaderboardsEntry].
 *
 * @property name The name of the character. Will be null if the character [isDeleted].
 * @property rank The rank of the character.
 * @property dromeLevel The Drome level of the character.
 */
@Serializable
@SerialName("leaderboardsEntry")
data class LeaderboardsEntry(
    override val rank: Int,
    val name: String,
    override val dromeLevel: Int,
) : BaseLeaderboardEntry() {
    /**
     * The URL to the character's page. If the character is deleted it will be null.
     */
    val url: String get() = getCharacterUrl(name)
}

@Serializable
@SerialName("deletedLeaderboardsEntry")
data class DeletedLeaderboardsEntry(
    override val rank: Int,
    override val dromeLevel: Int,
) : BaseLeaderboardEntry()
