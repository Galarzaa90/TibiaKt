package com.galarzaa.tibiakt.core.models

import kotlinx.serialization.Serializable

@Serializable
data class DisplayFamiliar(
    val familiarId: Int,
    val name: String,
)