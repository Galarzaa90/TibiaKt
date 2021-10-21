package com.galarzaa.tibiakt.core.enums

/**
 * The possible PvP types to choose for filtering auctions.
 */
enum class AuctionPvpTypeFilter(override val value: Int) : IntEnum {
    OPEN_PVP(0),
    OPTIONAL_PVP(1),
    HARDCORE_PVP(2),
    RETRO_OPEN_PVP(3),
    RETRO_HARDCORE_PVP(4);

    companion object {
        const val queryParam = "filter_worldpvptype"
    }
}