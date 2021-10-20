package com.galarzaa.tibiakt.core.models.bazaar

import kotlinx.serialization.Serializable

@Serializable
data class Outfits(
    override val currentPage: Int,
    override val totalPages: Int,
    override val resultsCount: Int,
    override val entries: List<DisplayOutfit> = emptyList(),
    override val fullyFetched: Boolean = false,
) : AjaxPaginator<DisplayOutfit>