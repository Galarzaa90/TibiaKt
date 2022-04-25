package com.galarzaa.tibiakt.core.builders

import com.galarzaa.tibiakt.core.models.guild.GuildEntry
import com.galarzaa.tibiakt.core.models.guild.GuildsSection
import com.galarzaa.tibiakt.core.utils.BuilderDsl

@BuilderDsl
inline fun guildsSectionBuilder(block: GuildsSectionBuilder.() -> Unit) = GuildsSectionBuilder().apply(block)

@BuilderDsl
inline fun guildsSection(block: GuildsSectionBuilder.() -> Unit) = guildsSectionBuilder(block).build()

@BuilderDsl
class GuildsSectionBuilder : TibiaKtBuilder<GuildsSection>() {
    var world: String? = null
    val guilds: MutableList<GuildEntry> = mutableListOf()

    fun addGuild(name: String, logoUrl: String, description: String? = null, isActive: Boolean) = apply {
        guilds.add(GuildEntry(name, description, logoUrl, isActive))
    }

    override fun build(): GuildsSection {
        return GuildsSection(
            world = world ?: throw IllegalStateException("name is required"),
            guilds = guilds
        )
    }
}