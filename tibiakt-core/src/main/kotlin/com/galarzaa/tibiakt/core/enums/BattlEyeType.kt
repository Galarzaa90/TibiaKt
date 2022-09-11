package com.galarzaa.tibiakt.core.enums

public enum class BattlEyeType(public val weight: Int) {
    /**
     * Protected by BattlEye since the beginning.
     */
    GREEN(2),

    /**
     * Protected by BattlEye at a later date.
     */
    YELLOW(1),

    /**
     * Not protected by BattlEye
     */
    UNPROTECTED(0),
}
