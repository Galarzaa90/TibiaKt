package com.galarzaa.tibiakt.core.models.character

import kotlinx.serialization.Serializable

@Serializable
data class DisplayedAchievement(val name: String, val grade: Int, val secret: Boolean = false)