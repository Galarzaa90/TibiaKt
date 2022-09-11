package com.galarzaa.tibiakt.core.utils

import kotlin.math.min

/**
 * Get a sublist from the receiver, starting at a certain [offset].
 *
 * If the offset is bigger than the list's size, the same list is returned.
 */
public fun <T> List<T>.offsetStart(offset: Int): List<T> {
    return subList(min(offset, size), size)
}
