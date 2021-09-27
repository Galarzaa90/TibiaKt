package com.galarzaa.tibiakt.models

import kotlinx.serialization.Serializable

@Serializable
data class HighscoresEntry(
    val position: Int,
    override val name: String,
    val level: Int,
    val world: String,
    val vocation: Vocation,
    val value: Long,
    val additionalValue: String? = null
) : BaseCharacter