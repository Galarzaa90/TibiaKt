package com.galarzaa.tibiakt.core.models.creatures

import com.galarzaa.tibiakt.core.utils.getCreaturesSectionUrl
import kotlinx.serialization.Serializable

/**
 * The Creature's section in Tibia.com
 *
 * @property boostedCreature The currently boosted creature, also known as creature of the day.
 * @property creatures The list of creatures in the library. Not all creatures in-game are visible.
 */
@Serializable
data class CreaturesSection(
    val boostedCreature: CreatureEntry,
    val creatures: List<CreatureEntry>,
) {
    /**
     * The URL to the creatures section.
     */
    val url get() = getCreaturesSectionUrl()
}


