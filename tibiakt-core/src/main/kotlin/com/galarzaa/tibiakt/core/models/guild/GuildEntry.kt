package com.galarzaa.tibiakt.core.models.guild

import kotlinx.serialization.Serializable

/**
 * A guild listed in the [GuildsSection] of a world.
 *
 * @property description The description of the guild.
 * @property logoUrl The URL to the guild's logo.
 * @property isActive Whether the guild is active or still in formation.
 */
@Serializable
public data class GuildEntry(
    override val name: String,
    val description: String?,
    val logoUrl: String,
    val isActive: Boolean,
) : BaseGuild
