package com.galarzaa.tibiakt.core.models.bazaar

import com.galarzaa.tibiakt.core.models.Paginated

interface AjaxPaginator<T> : Paginated<T> {
    val fullyFetched: Boolean
}