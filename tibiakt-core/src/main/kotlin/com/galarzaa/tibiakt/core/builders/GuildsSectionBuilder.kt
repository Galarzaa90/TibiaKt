package com.galarzaa.tibiakt.core.builders

import com.galarzaa.tibiakt.core.models.guild.GuildEntry
import com.galarzaa.tibiakt.core.models.guild.GuildsSection
import com.galarzaa.tibiakt.core.utils.BuilderDsl

@BuilderDsl
public inline fun guildsSectionBuilder(block: GuildsSectionBuilder.() -> Unit): GuildsSectionBuilder =
    GuildsSectionBuilder().apply(block)

@BuilderDsl
public inline fun guildsSection(block: GuildsSectionBuilder.() -> Unit): GuildsSection =
    guildsSectionBuilder(block).build()

@BuilderDsl
public class GuildsSectionBuilder : TibiaKtBuilder<GuildsSection>() {
    public var world: String? = null
    public val guilds: MutableList<GuildEntry> = mutableListOf()

    public fun addGuild(
        name: String,
        logoUrl: String,
        description: String? = null,
        isActive: Boolean,
    ): GuildsSectionBuilder = apply {
        guilds.add(GuildEntry(name, description, logoUrl, isActive))
    }

    override fun build(): GuildsSection {
        return GuildsSection(
            world = world ?: throw IllegalStateException("name is required"),
            guilds = guilds
        )
    }
}
