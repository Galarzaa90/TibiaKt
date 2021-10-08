package com.galarzaa.tibiakt.core.parsers

interface Parser<T> {
    fun fromContent(content: String): T
}