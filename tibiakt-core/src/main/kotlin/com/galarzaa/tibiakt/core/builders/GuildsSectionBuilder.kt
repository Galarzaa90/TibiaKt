package com.galarzaa.tibiakt.core.builders

import com.galarzaa.tibiakt.core.models.guild.GuildEntry
import com.galarzaa.tibiakt.core.models.guild.GuildsSection

class GuildsSectionBuilder {
    private var world: String? = null
    private val guilds: MutableList<GuildEntry> = mutableListOf()

    fun world(world: String) = apply { this.world = world }
    fun addGuild(name: String, logoUrl: String, description: String? = null, isActive: Boolean) = apply {
        guilds.add(GuildEntry(name, description, logoUrl, isActive))
    }

    fun build(): GuildsSection {
        return GuildsSection(
            world = world ?: throw IllegalStateException("name is required"),
            guilds = guilds
        )
    }
}