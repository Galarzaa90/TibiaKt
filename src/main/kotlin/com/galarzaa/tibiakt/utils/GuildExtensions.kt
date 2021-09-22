package com.galarzaa.tibiakt.utils

import com.galarzaa.tibiakt.models.BaseGuild
import com.galarzaa.tibiakt.models.Guild

val BaseGuild.url: String
    get() = getGuildUrl(name)

val Guild.ranks: List<String>
    get() = members.map { it.rank }.distinct()