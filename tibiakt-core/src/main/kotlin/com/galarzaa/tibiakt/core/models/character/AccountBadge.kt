package com.galarzaa.tibiakt.core.models.character

import kotlinx.serialization.Serializable

/**
 * A displayed account badge.
 */
@Serializable
data class AccountBadge(val name: String, val description: String, val imageUrl: String)