package com.galarzaa.tibiakt.core.models.world

import com.galarzaa.tibiakt.core.enums.Vocation
import com.galarzaa.tibiakt.core.models.character.BaseCharacter
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