package com.galarzaa.tibiakt.core.models

import kotlinx.serialization.Serializable

@Serializable
data class DisplayOutfit(
    val outfitId: Int,
    val addons: Int,
)