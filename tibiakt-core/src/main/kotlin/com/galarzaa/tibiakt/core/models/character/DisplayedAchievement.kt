package com.galarzaa.tibiakt.core.models.character

import kotlinx.serialization.Serializable

/**
 * The achievements choosen to be displayed for a [Character].
 *
 * @property name The name of the achievement.
 * @property grade The grade of the achievement, also know as the number of stars.
 * @property secret Whether this is a secret achievement or not.
 */
@Serializable
public data class DisplayedAchievement(val name: String, val grade: Int, val secret: Boolean)
