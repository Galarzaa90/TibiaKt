@file:UseSerializers(LocalDateSerializer::class)

package com.galarzaa.tibiakt.core.models.guild

import com.galarzaa.tibiakt.core.enums.Vocation
import com.galarzaa.tibiakt.core.models.character.BaseCharacter
import com.galarzaa.tibiakt.core.utils.LocalDateSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.time.LocalDate

@Serializable
data class GuildMember(
    override val name: String,
    val rank: String,
    val title: String? = null,
    val level: Int,
    val vocation: Vocation,
    val joiningDate: LocalDate,
    val isOnline: Boolean,
) : BaseCharacter
