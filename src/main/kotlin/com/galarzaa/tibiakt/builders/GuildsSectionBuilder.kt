package com.galarzaa.tibiakt.builders

import com.galarzaa.tibiakt.models.GuildEntry
import com.galarzaa.tibiakt.models.GuildsSection

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