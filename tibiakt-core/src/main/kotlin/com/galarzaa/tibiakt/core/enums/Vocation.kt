package com.galarzaa.tibiakt.core.enums

enum class Vocation(val properName: String) {
    NONE("None"),
    DRUID("Druid"),
    SORCERER("Sorcerer"),
    PALADIN("Paladin"),
    KNIGHT("Knight"),
    ELDER_DRUID("Elder Druid"),
    MASTER_SORCERER("Master Sorcerer"),
    ROYAL_PALADIN("Royal Paladin"),
    ELITE_KNIGHT("Elite Knight");

    companion object {
        fun fromProperName(properName: String): Vocation? {
            return values().firstOrNull { it.properName.equals(properName, true) }
        }
    }
}