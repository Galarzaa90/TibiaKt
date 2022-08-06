package com.galarzaa.tibiakt.core.models.character

import com.galarzaa.tibiakt.core.models.guild.BaseGuild
import kotlinx.serialization.Serializable

@Serializable
data class GuildMembershipWithTitle(override val name: String, val rank: String, val title: String?) : BaseGuild