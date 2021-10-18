package com.galarzaa.tibiakt.core.models

import kotlinx.serialization.Serializable

@Serializable
data class AchievementEntry(
    val name: String,
    val secret: Boolean,
)