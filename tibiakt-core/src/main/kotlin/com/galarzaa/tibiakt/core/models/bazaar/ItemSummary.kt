package com.galarzaa.tibiakt.core.models.bazaar

import com.galarzaa.tibiakt.core.models.Paginated
import kotlinx.serialization.Serializable

/**
 * A collection of items of an auctioned character.
 */
@Serializable
data class ItemSummary(
    override val currentPage: Int,
    override val totalPages: Int,
    override val resultsCount: Int,
    override val entries: List<DisplayItem> = emptyList(),
    val fullyFetched: Boolean = false,
) : Paginated<DisplayItem>