package com.galarzaa.tibiakt.models

import kotlinx.serialization.Serializable

@Serializable
data class DisplayedAchievement(val name: String, val grade: Int, val secret: Boolean = false)