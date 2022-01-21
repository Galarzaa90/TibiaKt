package com.galarzaa.tibiakt.core.models.bazaar

import kotlinx.serialization.Serializable

/**
 * An achievement in an [Auction].
 *
 * @property name The name of the achievement.
 * @property secret Whether the achievement is secret or not.
 */
@Serializable
data class AchievementEntry(
    val name: String,
    val secret: Boolean,
)