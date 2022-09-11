package com.galarzaa.tibiakt.core.enums

/**
 * The possible ordering directions for auctions
 */
public enum class AuctionOrderDirection(override val value: Int) : IntEnum {
    /**
     * Descending order
     */
    HIGHEST_LATEST(0),

    /**
     * Ascending order
     */
    LOWEST_EARLIEST(1);

    public companion object {
        public const val queryParam: String = "order_direction"
    }
}
