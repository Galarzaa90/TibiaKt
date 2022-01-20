package com.galarzaa.tibiakt.core.models.character

import com.galarzaa.tibiakt.core.models.guild.BaseGuild
import kotlinx.serialization.Serializable

/**
 * The guild a [Character] belongs to.
 *
 * @property rank The name of the rank the character holds in the guild.
 */
@Serializable
data class GuildMembership(override val name: String, val rank: String) : BaseGuild