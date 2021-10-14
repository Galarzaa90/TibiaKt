package com.galarzaa.tibiakt.core.utils

import com.galarzaa.tibiakt.core.models.BaseGuild
import com.galarzaa.tibiakt.core.models.Guild
import com.galarzaa.tibiakt.core.models.GuildsSection

val BaseGuild.url: String
    get() = getGuildUrl(name)

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

val GuildsSection.url
    get() = getWorldGuilds(world)