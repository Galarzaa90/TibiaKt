package com.galarzaa.tibiakt.core.models.guild

import com.galarzaa.tibiakt.core.utils.getWorldGuilds
import kotlinx.serialization.Serializable

@Serializable
data class GuildsSection(
    val world: String,
    val guilds: List<GuildEntry> = emptyList(),
) {
    val url
        get() = getWorldGuilds(world)
}

