package com.galarzaa.tibiakt.core.models.highscores

import com.galarzaa.tibiakt.core.enums.Vocation
import com.galarzaa.tibiakt.core.models.character.BaseCharacter
import kotlinx.serialization.Serializable

@Serializable
data class HighscoresEntry(
    val rank: Int,
    override val name: String,
    val level: Int,
    val world: String,
    val vocation: Vocation,
    val value: Long,
    val additionalValue: String? = null
) : BaseCharacter