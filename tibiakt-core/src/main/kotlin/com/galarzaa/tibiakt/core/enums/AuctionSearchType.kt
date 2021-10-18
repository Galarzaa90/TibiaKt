package com.galarzaa.tibiakt.core.enums

enum class AuctionSearchType(override val value: Int) : IntEnum {
    ITEM_DEFAULT(0),
    ITEM_WILDCARD(1),
    CHARACTER_NAME(2),
}