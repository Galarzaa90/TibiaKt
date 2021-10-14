package com.galarzaa.tibiakt.core.models

import com.galarzaa.tibiakt.core.utils.getWorldGuilds
import kotlinx.serialization.Serializable

@Serializable
data class GuildsSection(
    val world: String,
    val guilds: List<GuildEntry> = emptyList()
)

val GuildsSection.url
    get() = getWorldGuilds(world)