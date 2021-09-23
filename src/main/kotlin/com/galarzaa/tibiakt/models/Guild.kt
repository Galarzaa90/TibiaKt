@file:UseSerializers(LocalDateSerializer::class)

package com.galarzaa.tibiakt.models

import com.galarzaa.tibiakt.LocalDateSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.time.LocalDate

@Serializable
data class Guild(
    override val name: String,
    val world: String,
    val logoUrl: String,
    val description: String? = null,
    val foundingDate: LocalDate,
    val isActive: Boolean,
    val applicationsOpen: Boolean,
    val homepage: String? = null,
    val guildHall: GuildHall? = null,
    val disbandingDate: LocalDate? = null,
    val disbandingReason: String? = null,
    val members: List<GuildMember> = emptyList(),
    val invited: List<GuildInvite> = emptyList()
) : BaseGuild