package com.galarzaa.tibiakt.core.models

import com.galarzaa.tibiakt.core.utils.getWorldUrl

interface BaseWorld {
    val name: String
}

val BaseWorld.url
    get() = getWorldUrl(name)