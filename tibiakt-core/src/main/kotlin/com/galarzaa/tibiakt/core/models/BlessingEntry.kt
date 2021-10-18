package com.galarzaa.tibiakt.core.models

import kotlinx.serialization.Serializable

@Serializable
data class BlessingEntry(
    val name: String,
    val amount: Int,
)