package com.galarzaa.tibiakt.core.models.guild

import com.galarzaa.tibiakt.core.utils.getWorldGuildsUrl
import kotlinx.serialization.Serializable

@Serializable
data class GuildsSection(
    val world: String,
    val guilds: List<GuildEntry> = emptyList(),
) {
    val url
        get() = getWorldGuildsUrl(world)
}

