@file:UseSerializers(LocalDateSerializer::class)

package com.galarzaa.tibiakt.core.models.guild

import com.galarzaa.tibiakt.core.enums.Vocation
import com.galarzaa.tibiakt.core.models.character.BaseCharacter
import com.galarzaa.tibiakt.core.utils.LocalDateSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.time.LocalDate

/**
 * A character that is part of a [Guild].
 *
 * @property rank The name of the rank the character holds.
 * @property title The title of the character in the guild.
 * @property level The current level of the character.
 * @property vocation The vocation of the character.
 * @property joiningDate The date when the character joined the guild.
 * @property isOnline Whether the character is currently online or not.
 */
@Serializable
data class GuildMember(
    override val name: String,
    val rank: String,
    val title: String?,
    val level: Int,
    val vocation: Vocation,
    val joiningDate: LocalDate,
    val isOnline: Boolean,
) : BaseCharacter
