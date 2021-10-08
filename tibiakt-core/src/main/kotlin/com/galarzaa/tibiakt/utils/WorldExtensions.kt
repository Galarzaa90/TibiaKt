package com.galarzaa.tibiakt.utils

import com.galarzaa.tibiakt.models.BaseWorld
import com.galarzaa.tibiakt.models.WorldOverview

val WorldOverview.totalOnline: Int
    get() = worlds.sumOf { it.onlineCount }

val WorldOverview.url: String
    get() = getWorldOverviewUrl()

val BaseWorld.url
    get() = getWorldUrl(name)