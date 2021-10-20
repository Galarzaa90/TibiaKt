package com.galarzaa.tibiakt.core.models.character

import com.galarzaa.tibiakt.core.utils.getCharacterUrl

/**
 * Base interface for characters
 *
 * @property name The name of the character.
 */
interface BaseCharacter {
    val name: String

    /**
     * URL to the character's information page.
     */
    val url: String
        get() = getCharacterUrl(name)
}