package com.galarzaa.tibiakt.utils

fun <T> List<T>.offsetStart(offset: Int): List<T> {
    return subList(offset, size)
}