package com.galarzaa.tibiakt.models

import kotlinx.serialization.Serializable

@Serializable
data class GuildMembership(val guildRank: String, val guildName: String)