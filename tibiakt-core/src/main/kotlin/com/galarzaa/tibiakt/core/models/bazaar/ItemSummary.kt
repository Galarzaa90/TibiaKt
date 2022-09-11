package com.galarzaa.tibiakt.core.models.bazaar

import kotlinx.serialization.Serializable

/**
 * A collection of items of an auctioned character.
 */
@Serializable
public data class ItemSummary(
    override val currentPage: Int,
    override val totalPages: Int,
    override val resultsCount: Int,
    override val entries: List<ItemEntry>,
    override val fullyFetched: Boolean,
) : AjaxPaginator<ItemEntry>
