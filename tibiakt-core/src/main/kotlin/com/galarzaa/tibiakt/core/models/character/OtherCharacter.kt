package com.galarzaa.tibiakt.core.models.character

import kotlinx.serialization.Serializable

/**
 * Additional character listed in a [Character]'s information.
 *
 * @property world The world of the character.
 * @property main Whether this is the main character of the account or not.
 * @property isOnline Whether this character is currently online or not.
 * @property isDeleted Whether this character is scheduled for deletion or not.
 * @property recentlyTraded Whether this character was recently traded.
 * @property position Any special position the character holds.
 */
@Serializable
data class OtherCharacter(
    override val name: String,
    val world: String,
    val main: Boolean,
    val isOnline: Boolean,
    val isDeleted: Boolean,
    val recentlyTraded: Boolean,
    val position: String?
) : BaseCharacter