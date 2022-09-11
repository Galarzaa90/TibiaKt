@file:UseSerializers(LocalDateSerializer::class)

package com.galarzaa.tibiakt.core.models.guild

import com.galarzaa.tibiakt.core.models.character.BaseCharacter
import com.galarzaa.tibiakt.core.serializers.LocalDateSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.time.LocalDate

/**
 * A character invited to a [Guild].
 *
 * @property inviteDate The date when the character was invited.
 */
@Serializable
public data class GuildInvite(
    override val name: String,
    val inviteDate: LocalDate,
) : BaseCharacter
