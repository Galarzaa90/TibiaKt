package com.galarzaa.tibiakt.core.enums

/**
 * The possible vocations to filter auction results.
 */
public enum class AuctionVocationFilter(override val value: Int) : IntEnum {
    NONE(1),
    DRUID(2),
    KNIGHT(3),
    PALADIN(4),
    SORCERER(5);

    public companion object {
        public const val queryParam: String = "filter_profession"
    }
}
