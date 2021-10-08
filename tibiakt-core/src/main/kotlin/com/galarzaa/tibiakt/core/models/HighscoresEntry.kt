package com.galarzaa.tibiakt.core.models

import com.galarzaa.tibiakt.core.enums.Vocation
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