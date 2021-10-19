package com.galarzaa.tibiakt.core.enums

/**
 * The possible skills to auctions by.
 */
enum class AuctionSkillFilter(override val value: Int) : IntEnum {
    AXE_FIGHTING(10),
    CLUB_FIGHTING(9),
    DISTANCE_FIGHTING(7),
    FISHING(13),
    FIST_FIGHTING(11),
    MAGIC_LEVEL(1),
    SHIELDING(6),
    SWORD_FIGHTING(8),
}