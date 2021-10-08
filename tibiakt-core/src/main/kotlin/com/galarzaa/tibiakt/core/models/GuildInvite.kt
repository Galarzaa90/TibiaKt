@file:UseSerializers(LocalDateSerializer::class)

package com.galarzaa.tibiakt.core.models

import com.galarzaa.tibiakt.core.LocalDateSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.time.LocalDate


@Serializable
data class GuildInvite(
    override val name: String,
    val inviteDate: LocalDate
) : BaseCharacter
