package com.galarzaa.tibiakt.core.models

import kotlinx.serialization.Serializable

@Serializable
data class GuildsSection(
    val world: String,
    val guilds: List<GuildEntry> = emptyList()
)