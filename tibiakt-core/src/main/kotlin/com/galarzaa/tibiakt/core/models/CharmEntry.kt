package com.galarzaa.tibiakt.core.models

import kotlinx.serialization.Serializable

@Serializable
data class CharmEntry(
    val name: String,
    val cost: Int,
)