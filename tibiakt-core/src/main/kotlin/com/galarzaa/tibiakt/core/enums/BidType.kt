package com.galarzaa.tibiakt.core.enums

/**
 * The possible bid types an auction might have.
 */
enum class BidType(override val value: String) : StringEnum {
    /**
     * The current maximum bid, meaning the auction has received at least one bid.
     */
    CURRENT("Current Bid"),

    /**
     * The minimum bid set by the auction author, meaning the auction hasn't received any bids or it finished without bids.
     */
    MINIMUM("Minimum Bid"),

    /** The bid that won the auction. */
    WINNING("Winning Bid"),
}