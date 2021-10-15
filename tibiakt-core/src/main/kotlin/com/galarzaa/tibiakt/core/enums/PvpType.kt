package com.galarzaa.tibiakt.core.enums

/**
 * A world PvP type
 *
 * @property weight The restriction weight of this type, used to check transferability between worlds.
 */
enum class PvpType(override val value: String, val weight: Int) : StringEnum {
    OPEN_PVP("Open PvP", 2),
    OPTIONAL_PVP("Optional PvP", 1),
    RETRO_OPEN_PVP("Retro Open PvP", 2),
    RETRO_HARDCORE_PVP("Retro Hardcore PvP", 3),
    HARDCORE_PVP("Hardcore PvP", 3),
}