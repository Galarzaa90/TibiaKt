package com.galarzaa.tibiakt.models

import kotlinx.serialization.Serializable

@Serializable
data class HighscoresEntry(
    val position: Int,
    override val name: String,
    val level: Int,
    val world: String,
    val vocation: String,
    val value: Int,
    val additionalValue: String? = null
) : BaseCharacter