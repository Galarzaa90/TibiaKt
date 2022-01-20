package com.galarzaa.tibiakt.core.models.guild

import com.galarzaa.tibiakt.core.utils.getWorldGuildsUrl
import kotlinx.serialization.Serializable

/**
 * The list of [Guild]s in a world.
 *
 * @property world The name of the world the guilds belong to.
 * @property guilds A list of all the guilds in the world.
 */
@Serializable
data class GuildsSection(
    val world: String,
    val guilds: List<GuildEntry> = emptyList(),
) {
    /**
     * The URL to this guilds section.
     */
    val url
        get() = getWorldGuildsUrl(world)

    /**
     * A filtered version of [guilds] containing only active guilds.
     */
    val activeGuilds: List<GuildEntry> get() = guilds.filter { it.isActive }

    /**
     * A filtered version of [guilds] containing only guilds in formation.
     */
    val guildsInFormation: List<GuildEntry> get() = guilds.filter { !it.isActive }
}

