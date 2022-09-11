@file:UseSerializers(LocalDateSerializer::class)

package com.galarzaa.tibiakt.core.models.guild

import com.galarzaa.tibiakt.core.serializers.LocalDateSerializer
import com.galarzaa.tibiakt.core.utils.offsetStart
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.time.LocalDate

/**
 * A Tibia guild
 *
 * @property world The world where the guild is in.
 * @property logoUrl The URL to the guild's logo, or the default logo.
 * @property description The description of the guild.
 * @property foundingDate The date when the guild was founded.
 * @property isActive Whether the guild is active or still in formation.
 * @property applicationsOpen Whether applications are open or not.
 * @property homepage The URL to the guild's homepage, if set.
 * @property guildHall The guildhall rented by the guild.
 * @property disbandingDate The date when the guild is set to be disbanded.
 * @property disbandingReason The reason why it will be disbanded.
 * @property members The list of members in the guild.
 * @property invited The list of characters currently invited to the guild.
 */
@Serializable
public data class Guild(
    override val name: String,
    val world: String,
    val logoUrl: String,
    val description: String?,
    val foundingDate: LocalDate,
    val isActive: Boolean,
    val applicationsOpen: Boolean,
    val homepage: String?,
    val guildHall: GuildHall?,
    val disbandingDate: LocalDate?,
    val disbandingReason: String?,
    val members: List<GuildMember>,
    val invited: List<GuildInvite>,
) : BaseGuild {
    /**
     * A list of the guild ranks.
     */
    val ranks: List<String>
        get() = members.map { it.rank }.distinct()

    /** The leader of the guild */
    val leader: GuildMember
        get() = members.first()

    /** The vice leaders of the guild */
    val viceLeaders: List<GuildMember>
        get() = members.offsetStart(1).takeWhile { it.rank == members[1].rank }

    /** A mapping of the members by their rank */
    val membersByRank: Map<String, List<GuildMember>>
        get() = members.groupBy { it.rank }

    /** The number of members in the guild. */
    val memberCount: Int
        get() = members.size

    /**
     * The list of members currently online.
     */
    val onlineMembers: List<GuildMember>
        get() = members.filter { it.isOnline }

    /**
     * The number of online members in the guild
     */
    val onlineCount: Int
        get() = onlineMembers.count()
}
