package com.galarzaa.tibiakt.core.models

interface Paginated<T> {
    val currentPage: Int
    val totalPages: Int
    val resultsCount: Int
    val entries: List<T>
}