package com.galarzaa.tibiakt.core.models.guild

import com.galarzaa.tibiakt.core.utils.getGuildUrl

/**
 * Base interface for guild related classes
 *
 * @property name The name of the guild.
 * @property url The URL to the guild's page on Tibia.com
 */
interface BaseGuild {
    val name: String

    val url: String get() = getGuildUrl(name)
}