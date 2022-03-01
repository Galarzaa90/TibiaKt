package com.galarzaa.tibiakt.core.models.character

import com.galarzaa.tibiakt.core.utils.getCharacterUrl

/**
 * Base interface for characters.
 *
 * @property name The name of the character.
 * @property url The URL to the character's information page.
 */
interface BaseCharacter {
    val name: String
    val url: String get() = getCharacterUrl(name)
}