package com.galarzaa.tibiakt.core.models.bazaar

import kotlinx.serialization.Serializable

/**
 * A creature entry of an [Auction] character, could be a bestiary or bosstiary entry.
 *
 * @property name The name of the creature.
 * @property kills The number of kills done by the character.
 * @property step The current unlock step of the character.
 */
@Serializable
public data class CreatureEntry(
    val name: String,
    val kills: Long,
    val step: Int,
) {
    /**
     * Whether the entry is complete or not.
     */
    val isComplete: Boolean get() = step == 4
}
