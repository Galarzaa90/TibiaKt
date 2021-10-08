package com.galarzaa.tibiakt.core.utils

fun <T> List<T>.offsetStart(offset: Int): List<T> {
    return subList(offset, size)
}