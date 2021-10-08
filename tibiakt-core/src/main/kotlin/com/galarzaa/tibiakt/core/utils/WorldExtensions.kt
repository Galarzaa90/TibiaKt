package com.galarzaa.tibiakt.core.utils

import com.galarzaa.tibiakt.core.models.BaseWorld
import com.galarzaa.tibiakt.core.models.WorldOverview

val WorldOverview.totalOnline: Int
    get() = worlds.sumOf { it.onlineCount }

val WorldOverview.url: String
    get() = getWorldOverviewUrl()

val BaseWorld.url
    get() = getWorldUrl(name)