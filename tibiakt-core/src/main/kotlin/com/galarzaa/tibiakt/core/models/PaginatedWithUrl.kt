package com.galarzaa.tibiakt.core.models

interface PaginatedWithUrl<T> : Paginated<T> {
    /** Get the URL to a specific page */
    fun getPageUrl(page: Int): String

    /** Get the URL to the next page if there is any. */
    val nextPageUrl: String?
        get() = if (currentPage == totalPages) null else getPageUrl(currentPage + 1)

    /** Get the URL to the previous page if there is any. */
    val previousPageUrl: String?
        get() = if (currentPage == 0) null else getPageUrl(currentPage - 1)
}