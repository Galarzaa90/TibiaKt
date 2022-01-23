package com.galarzaa.tibiakt.core.models.creatures

import com.galarzaa.tibiakt.core.utils.getCreatureUrl
import com.galarzaa.tibiakt.core.utils.getStaticFileUrl
import kotlinx.serialization.Serializable

/**
 * A creature in the [CreaturesSection]
 *
 * @property name The name of the creature. Usually in plural, except for [CreaturesSection.boostedCreature].
 * @property identifier Internal name for the creature's race. Used for links and images.
 */
@Serializable
data class CreatureEntry(
    val name: String,
    val identifier: String,
) {
    /**
     * The URL to the creature's page.
     */
    val url get() = getCreatureUrl(identifier)

    /**
     * The URL to the creature's image.
     */
    val imageUrl get() = getStaticFileUrl("images", "library", "$identifier.gif")
}