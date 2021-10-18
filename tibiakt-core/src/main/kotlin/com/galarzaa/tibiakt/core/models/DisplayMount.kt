package com.galarzaa.tibiakt.core.models

import kotlinx.serialization.Serializable

@Serializable
data class DisplayMount(
    val name: String,
    val mountId: Int,
)