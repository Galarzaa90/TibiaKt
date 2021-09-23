package com.galarzaa.tibiakt.utils

import com.galarzaa.tibiakt.models.BaseGuild
import com.galarzaa.tibiakt.models.Guild

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