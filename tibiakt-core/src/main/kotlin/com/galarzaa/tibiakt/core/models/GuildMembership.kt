package com.galarzaa.tibiakt.core.models

import kotlinx.serialization.Serializable

@Serializable
data class GuildMembership(override val name: String, val rank: String) : BaseGuild