package com.galarzaa.tibiakt.core.utils

import kotlin.math.min

fun <T> List<T>.offsetStart(offset: Int): List<T> {
    return subList(min(offset, size), size)
}