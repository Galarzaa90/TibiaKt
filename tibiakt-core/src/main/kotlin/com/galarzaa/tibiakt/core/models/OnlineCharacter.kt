package com.galarzaa.tibiakt.core.models

import com.galarzaa.tibiakt.core.enums.Vocation
import kotlinx.serialization.Serializable

/**
 * An online character.
 *
 * @property level The level of the character
 * @property vocation The vocation of the character
 */
@Serializable
data class OnlineCharacter(
    override val name: String,
    val level: Int,
    val vocation: Vocation
) : BaseCharacter