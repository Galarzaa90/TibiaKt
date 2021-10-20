package com.galarzaa.tibiakt.core.models.bazaar

import kotlinx.serialization.Serializable

@Serializable
data class BlessingEntry(
    val name: String,
    val amount: Int,
)