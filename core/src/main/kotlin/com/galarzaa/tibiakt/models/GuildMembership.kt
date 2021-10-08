package com.galarzaa.tibiakt.models

import kotlinx.serialization.Serializable

@Serializable
data class GuildMembership(override val name: String, val rank: String) : BaseGuild