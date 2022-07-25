package com.galarzaa.tibiakt.core.models.creatures

import com.galarzaa.tibiakt.core.utils.getCreatureUrl
import kotlinx.serialization.Serializable

/**
 * A creature in the [CreaturesSection]
 *
 * @property name The name of the creature. Usually in plural, except for [CreaturesSection.boostedCreature].
 * @property identifier Internal name for the creature's race. Used for links and images.
 */
@Serializable
data class CreatureEntry(
    override val name: String,
    override val identifier: String,
) : BaseCreatureEntry {
    /**
     * The URL to the creature's page.
     */
    val url get() = getCreatureUrl(identifier)
}

