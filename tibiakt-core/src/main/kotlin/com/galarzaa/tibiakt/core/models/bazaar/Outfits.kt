package com.galarzaa.tibiakt.core.models.bazaar

import kotlinx.serialization.Serializable

/**
 * The outfits of a character in an [Auction].
 */
@Serializable
data class Outfits(
    override val currentPage: Int,
    override val totalPages: Int,
    override val resultsCount: Int,
    override val entries: List<OutfitEntry>,
    override val fullyFetched: Boolean,
) : AjaxPaginator<OutfitEntry>