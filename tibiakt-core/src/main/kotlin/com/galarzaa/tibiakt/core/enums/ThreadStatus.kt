package com.galarzaa.tibiakt.core.enums

/**
 * The possible status a thread can have.
 *
 * Threads can have a combination of multiple status.
 */
public enum class ThreadStatus {
    /** Thread has more than 16 replies. */
    HOT,

    /** Thread has new posts since last visit. */
    NEW,

    /** Thread is closed. */
    CLOSED,

    /** Thread is stickied to the top. */
    STICKY,
}
