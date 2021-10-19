package com.galarzaa.tibiakt.core.enums

enum class AuctionOrder(override val value: Int) : IntEnum {
    HIGHEST_LATEST(0),
    LOWEST_EARLIEST(1),
}