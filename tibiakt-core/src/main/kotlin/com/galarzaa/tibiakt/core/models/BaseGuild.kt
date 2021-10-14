package com.galarzaa.tibiakt.core.models

import com.galarzaa.tibiakt.core.utils.getGuildUrl

interface BaseGuild {
    val name: String
}

val BaseGuild.url: String
    get() = getGuildUrl(name)