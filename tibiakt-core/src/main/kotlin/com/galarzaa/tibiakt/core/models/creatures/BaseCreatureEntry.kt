package com.galarzaa.tibiakt.core.models.creatures

import com.galarzaa.tibiakt.core.utils.getStaticFileUrl

interface BaseCreatureEntry {
    val name: String
    val identifier: String

    /**
     * The URL to the creature's image.
     */
    val imageUrl get() = getStaticFileUrl("images", "library", "$identifier.gif")
}