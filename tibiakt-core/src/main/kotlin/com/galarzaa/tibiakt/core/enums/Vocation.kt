package com.galarzaa.tibiakt.core.enums

enum class Vocation(override val value: String) : StringEnum {
    NONE("None"),
    DRUID("Druid"),
    SORCERER("Sorcerer"),
    PALADIN("Paladin"),
    KNIGHT("Knight"),
    ELDER_DRUID("Elder Druid"),
    MASTER_SORCERER("Master Sorcerer"),
    ROYAL_PALADIN("Royal Paladin"),
    ELITE_KNIGHT("Elite Knight");

    /**
     * Gets the base form of a vocation.
     */
    val base
        get() = when (this) {
            ELDER_DRUID -> DRUID
            MASTER_SORCERER -> SORCERER
            ROYAL_PALADIN -> PALADIN
            ELITE_KNIGHT -> KNIGHT
            else -> this
        }
}