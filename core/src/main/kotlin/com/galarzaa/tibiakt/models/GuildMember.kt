@file:UseSerializers(LocalDateSerializer::class)

package com.galarzaa.tibiakt.models

import com.galarzaa.tibiakt.core.LocalDateSerializer
import com.galarzaa.tibiakt.enums.Vocation
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
