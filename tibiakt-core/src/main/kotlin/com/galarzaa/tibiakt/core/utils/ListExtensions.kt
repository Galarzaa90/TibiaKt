package com.galarzaa.tibiakt.core.utils

import kotlin.math.max

fun <T> List<T>.offsetStart(offset: Int): List<T> {
    return subList(max(offset, size), size)
}