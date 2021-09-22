package com.galarzaa.tibiakt.models

import kotlinx.serialization.Serializable

@Serializable
data class AccountBadge(val name: String, val description: String, val imageUrl: String)