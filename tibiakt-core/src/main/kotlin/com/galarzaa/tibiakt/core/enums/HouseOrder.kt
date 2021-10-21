package com.galarzaa.tibiakt.core.enums

/**
 * Possible fields to filter by.
 */
enum class HouseOrder(override val value: String) : StringEnum {
    NAME("name"),
    SIZE("size"),
    RENT("rent"),
    BID("bid"),
    AUCTION_END("end"),
}
