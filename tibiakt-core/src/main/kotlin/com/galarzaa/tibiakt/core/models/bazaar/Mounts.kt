package com.galarzaa.tibiakt.core.models.bazaar

import kotlinx.serialization.Serializable

@Serializable
data class Mounts(
    override val currentPage: Int,
    override val totalPages: Int,
    override val resultsCount: Int,
    override val entries: List<DisplayMount> = emptyList(),
    override val fullyFetched: Boolean = false,
) : AjaxPaginator<DisplayMount>