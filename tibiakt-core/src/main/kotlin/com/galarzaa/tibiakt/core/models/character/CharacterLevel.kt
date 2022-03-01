package com.galarzaa.tibiakt.core.models.character

import kotlin.math.ceil
import kotlin.math.floor

interface CharacterLevel {
    val level: Int

    /**
     * The party shared experience range of the character.
     */
    val shareRange: IntRange
        get() {
            val minLevel = floor((level / 3.0) * 2).toInt()
            val maxLevel = ceil((level / 2.0) * 3).toInt() + if (level % 2 == 0) 1 else 0
            return minLevel..maxLevel
        }
}