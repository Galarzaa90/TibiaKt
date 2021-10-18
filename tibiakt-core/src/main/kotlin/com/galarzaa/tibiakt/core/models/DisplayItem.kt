package com.galarzaa.tibiakt.core.models

import kotlinx.serialization.Serializable

@Serializable
data class DisplayItem(
    val itemId: Int,
    val name: String,
    val description: String?,
    val count: Int,
)