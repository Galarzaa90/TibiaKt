package com.galarzaa.tibiakt.core.models.bazaar

import kotlinx.serialization.Serializable

@Serializable
data class CharmEntry(
    val name: String,
    val cost: Int,
)