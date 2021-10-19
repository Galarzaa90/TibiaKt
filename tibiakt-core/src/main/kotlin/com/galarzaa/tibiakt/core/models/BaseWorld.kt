package com.galarzaa.tibiakt.core.models

import com.galarzaa.tibiakt.core.utils.getWorldUrl

/**
 * Base interface for worlds
 *
 * @property name The name of the world.
 */
interface BaseWorld {
    val name: String

    /**
     * The URL to the world's page in Tibia.com
     */
    val url
        get() = getWorldUrl(name)
}