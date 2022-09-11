package com.galarzaa.tibiakt.core.models.character

import kotlinx.serialization.Serializable

/**
 * A displayed account badge.
 *
 * @property name The name of the badge.
 * @property description A brief description of how the badge is obtained.
 * @property imageUrl The URL to the badge's image.
 */
@Serializable
public data class AccountBadge(val name: String, val description: String, val imageUrl: String)
