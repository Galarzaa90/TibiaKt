package com.galarzaa.tibiakt.core.enums

/**
 * The possible fields you can order autions by
 */
enum class AuctionOrderBy(override val value: Int) : IntEnum {
    BID(100),
    END_DATE(101),
    LEVEL(102),
    START_DATE(103),
    AXE_FIGHTING(10),
    CLUB_FIGHTING(9),
    DISTANCE_FIGHTING(7),
    FISHING(13),
    FIST_FIGHTING(11),
    MAGIC_LEVEL(1),
    SHIELDING(6),
    SWORD_FIGHTING(8);

    companion object {
        const val queryParam = "order_column"
    }
}