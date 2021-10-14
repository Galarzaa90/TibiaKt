@file:UseSerializers(LocalDateSerializer::class)

package com.galarzaa.tibiakt.core.models

import com.galarzaa.tibiakt.core.utils.LocalDateSerializer
import com.galarzaa.tibiakt.core.utils.offsetStart
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.time.LocalDate

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

val Guild.ranks: List<String>
    get() = members.map { it.rank }.distinct()

val Guild.leader
    get() = members.first()

val Guild.viceLeaders
    get() = members.offsetStart(1).takeWhile { it.rank == members[1].rank }

val Guild.membersByRank
    get() = members.groupBy { it.rank }

val Guild.memberCount
    get() = members.size

val Guild.onlineMembers
    get() = members.filter { it.isOnline }

val Guild.onlineCount
    get() = onlineMembers.count()