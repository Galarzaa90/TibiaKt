package com.galarzaa.tibiakt.parsers

interface Parser<T> {
    fun fromContent(content: String): T
}