package com.galarzaa.tibiakt.core.models.world

import com.galarzaa.tibiakt.core.utils.getWorldUrl

/**
 * Base interface for worlds
 *
 * @property name The name of the world.
 */
public interface BaseWorld {
    public val name: String

    /**
     * The URL to the world's page in Tibia.com
     */
    public val url: String
        get() = getWorldUrl(name)
}
