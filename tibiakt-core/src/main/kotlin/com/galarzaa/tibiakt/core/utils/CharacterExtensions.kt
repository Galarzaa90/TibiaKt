package com.galarzaa.tibiakt.core.utils

import com.galarzaa.tibiakt.core.models.BaseCharacter
import com.galarzaa.tibiakt.core.models.Character
import kotlin.math.ceil
import kotlin.math.floor

val Character.shareRange: IntRange
    get() {
        val minLevel = floor((level / 3.0) * 2).toInt()
        val maxLevel = ceil((level / 2.0) * 3).toInt() + if (level % 2 == 0) 1 else 0
        return minLevel..maxLevel
    }

val BaseCharacter.url: String
    get() = getCharacterUrl(name)

val Character.scheduledForDeletion: Boolean
    get() = deletionDate != null

/**
 * URL to the character this character is married to, if any.
 */
val Character.marriedToUrl: String?
    get() {
        return getCharacterUrl(marriedTo ?: return null)
    }