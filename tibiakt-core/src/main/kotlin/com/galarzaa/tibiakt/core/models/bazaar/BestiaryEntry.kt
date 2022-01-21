package com.galarzaa.tibiakt.core.models.bazaar

import kotlinx.serialization.Serializable

/**
 * A bestiary entry of an [Auction] character.
 *
 * @property name The name of the creature.
 * @property kills The number of kills done by the character.
 * @property step The current unlock step of the character.
 */
@Serializable
data class BestiaryEntry(
    val name: String,
    val kills: Long,
    val step: Int,
) {
    /**
     * Whether the entry is complete or not.
     */
    val isComplete get() = step == 4
}