package com.galarzaa.tibiakt.core.models

data class Outfits(
    override val currentPage: Int,
    override val totalPages: Int,
    override val resultsCount: Int,
    override val entries: List<DisplayOutfit>,
    val fullyFetched: Boolean = false,
) : Paginated<DisplayOutfit>