package com.galarzaa.tibiakt.core.enums

/**
 * The types of search for the input field in the character bazaar.
 */
enum class AuctionSearchType(override val value: Int) : IntEnum {
    /**
     * Searches everything that includes the words on the search string.
     */
    ITEM_DEFAULT(0),

    /**
     * Searches everything that includes the search string
     */
    ITEM_WILDCARD(1),

    /**
     * Searches a character’s name.
     */
    CHARACTER_NAME(2);

    companion object {
        const val queryParam = "searchtype"
    }
}