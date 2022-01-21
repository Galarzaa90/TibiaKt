package com.galarzaa.tibiakt.core.models.bazaar

import kotlinx.serialization.Serializable

/**
 * A collection of items of an auctioned character.
 */
@Serializable
data class ItemSummary(
    override val currentPage: Int,
    override val totalPages: Int,
    override val resultsCount: Int,
    override val entries: List<ItemEntry> = emptyList(),
    override val fullyFetched: Boolean = false,
) : AjaxPaginator<ItemEntry>