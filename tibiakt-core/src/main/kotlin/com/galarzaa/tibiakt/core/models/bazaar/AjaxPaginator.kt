package com.galarzaa.tibiakt.core.models.bazaar

import com.galarzaa.tibiakt.core.models.Paginated

/**
 * A paginator that can be fetched via AJAX requests.
 */
interface AjaxPaginator<T> : Paginated<T> {
    /**
     * Whether this result set was fully fetched or not.
     */
    val fullyFetched: Boolean
}