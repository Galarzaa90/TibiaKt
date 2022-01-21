package com.galarzaa.tibiakt.core.enums

/**
 * A world PvP type
 */
enum class PvpType(override val value: String) : StringEnum {
    OPEN_PVP("Open PvP") {
        override val weight: Int = 2
        override val highscoresFilterValue: Int = 0
        override val bazaarFilterValue: Int = 0
    },
    OPTIONAL_PVP("Optional PvP") {
        override val weight: Int = 1
        override val highscoresFilterValue: Int = 1
        override val bazaarFilterValue: Int = 1
    },
    RETRO_OPEN_PVP("Retro Open PvP") {
        override val weight: Int = 2
        override val highscoresFilterValue: Int = 3
        override val bazaarFilterValue: Int = 3
    },
    RETRO_HARDCORE_PVP("Retro Hardcore PvP") {
        override val weight: Int = 3
        override val highscoresFilterValue: Int = 4
        override val bazaarFilterValue: Int = 4
    },
    HARDCORE_PVP("Hardcore PvP") {
        override val weight: Int = 3
        override val highscoresFilterValue: Int = 2
        override val bazaarFilterValue: Int = 2
    };

    /**
     * The restriction weight of this type, used to check transferability between worlds.
     */
    abstract val weight: Int

    /**
     * The value used internally to filter Highscores.
     */
    abstract val highscoresFilterValue: Int

    /**
     * The value used internally to filter the CharacterBazaar.
     */
    abstract val bazaarFilterValue: Int

    companion object {
        const val highscoresQueryParam: String = "worldtypes"
        fun fromHighscoresFilterValue(value: Int?) =
            enumValues<PvpType>().firstOrNull { it.highscoresFilterValue == value }

        const val bazaarQueryParam = "filter_worldpvptype"
        fun fromBazaarFilterValue(value: Int?) = enumValues<PvpType>().firstOrNull { it.bazaarFilterValue == value }
    }
}