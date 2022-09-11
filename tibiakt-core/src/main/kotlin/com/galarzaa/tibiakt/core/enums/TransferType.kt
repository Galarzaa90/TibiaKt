package com.galarzaa.tibiakt.core.enums

/**
 * The type of transfer rules applied to this server.
 */
public enum class TransferType {
    /**
     * No special rules besides the regular PvP and BattlEye restrictions.
     */
    REGULAR,

    /**
     * Character cannot transfer to this world but can transfer from this world to another.
     */
    BLOCKED,

    /**
     * Character can transfer to this world, but can only transfer to other locked worlds from these.
     */
    LOCKED
}
