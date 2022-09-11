package com.galarzaa.tibiakt.core.models

/**
 * An object made of multiple pages.
 *
 * @property currentPage The currently viewed page.
 * @property totalPages The total number of pages.
 * @property resultsCount The total number of [entries] across all pages.
 * @property entries The entries in this page.
 */
public interface Paginated<T> {
    public val currentPage: Int
    public val totalPages: Int
    public val resultsCount: Int
    public val entries: List<T>
}
