package com.galarzaa.tibiakt.models

import kotlinx.serialization.Serializable

@Serializable
data class OtherCharacter(
    override val name: String,
    val world: String,
    val main: Boolean = false,
    val isOnline: Boolean = false,
    val isDeleted: Boolean = false,
    val recentlyTraded: Boolean = false,
    val position: String?
) : BaseCharacter