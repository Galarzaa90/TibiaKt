package com.galarzaa.tibiakt.core.models

import kotlinx.serialization.Serializable

@Serializable
data class SalesArgument(
    val categoryImage: Int,
    val content: String,
)