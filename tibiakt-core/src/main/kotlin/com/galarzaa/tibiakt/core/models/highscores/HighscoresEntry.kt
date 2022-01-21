package com.galarzaa.tibiakt.core.models.highscores

import com.galarzaa.tibiakt.core.enums.Vocation
import com.galarzaa.tibiakt.core.models.character.BaseCharacter
import kotlinx.serialization.Serializable

/**
 * An entry in the [Highscores].
 *
 * @property rank The rank of the character. Multiple entries might have the same rank if they are tied.
 * @property level The level of the character.
 * @property world The world of the character.
 * @property vocation The vocation of the character.
 * @property value The value of the entry (e.g. distance level, magic level, experience)
 * @property additionalValue Additional information displayed in some categories, such as Loyalty Title.
 */
@Serializable
data class HighscoresEntry(
    val rank: Int,
    override val name: String,
    val level: Int,
    val world: String,
    val vocation: Vocation,
    val value: Long,
    val additionalValue: String? = null
) : BaseCharacter