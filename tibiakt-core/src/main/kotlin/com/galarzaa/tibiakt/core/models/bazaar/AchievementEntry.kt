package com.galarzaa.tibiakt.core.models.bazaar

import kotlinx.serialization.Serializable

@Serializable
data class AchievementEntry(
    val name: String,
    val secret: Boolean,
)