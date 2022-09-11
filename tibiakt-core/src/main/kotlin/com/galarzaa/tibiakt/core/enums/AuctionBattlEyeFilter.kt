package com.galarzaa.tibiakt.core.enums

/**
 * The possible BattlEye protection type filters for auctions
 */
public enum class AuctionBattlEyeFilter(override val value: Int) : IntEnum {
    /**
     * Worlds that have been protected from the beginning, these have a green icon.
     */
    INITIALLY_PROTECTED(1),

    /**
     * Worlds that had BattlEye implemented at a later date, these have a yellow icon.
     */
    PROTECTED(2),

    /**
     * Worlds that have no BattlEye protection at all.
     */
    NOT_PROTECTED(3);

    public companion object {
        public const val queryParam: String = "filter_worldbattleyestate"
    }
}
