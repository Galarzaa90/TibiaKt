package com.galarzaa.tibiakt.core.models

/**
 * An object made of multiple pages.
 *
 * @property currentPage The currently viewed page.
 * @property totalPages The total number of pages.
 * @property resultsCount The total number of [entries] across all pages.
 * @property entries The entries in this page.
 */
interface Paginated<T> {
    val currentPage: Int
    val totalPages: Int
    val resultsCount: Int
    val entries: List<T>
}