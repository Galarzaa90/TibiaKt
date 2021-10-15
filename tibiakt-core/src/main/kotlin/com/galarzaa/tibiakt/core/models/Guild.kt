@file:UseSerializers(LocalDateSerializer::class)

package com.galarzaa.tibiakt.core.models

import com.galarzaa.tibiakt.core.utils.LocalDateSerializer
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
data class Guild(
    override val name: String,
    val world: String,
    val logoUrl: String,
    val description: String? = null,
    val foundingDate: LocalDate,
    val isActive: Boolean,
    val applicationsOpen: Boolean,
    val homepage: String? = null,
    val guildHall: GuildHall? = null,
    val disbandingDate: LocalDate? = null,
    val disbandingReason: String? = null,
    val members: List<GuildMember> = emptyList(),
    val invited: List<GuildInvite> = emptyList()
) : BaseGuild

/**
 * A list of the guild ranks.
 */
val Guild.ranks: List<String>
    get() = members.map { it.rank }.distinct()

/** The leader of the guild */
val Guild.leader
    get() = members.first()

/** The vice leaders of the guild */
val Guild.viceLeaders
    get() = members.offsetStart(1).takeWhile { it.rank == members[1].rank }

/** A mapping of the members by their rank */
val Guild.membersByRank
    get() = members.groupBy { it.rank }

/** The number of members in the guild. */
val Guild.memberCount
    get() = members.size

/**
 * The list of members currently online.
 */
val Guild.onlineMembers
    get() = members.filter { it.isOnline }

/**
 * The number of online members in the guild
 */
val Guild.onlineCount
    get() = onlineMembers.count()