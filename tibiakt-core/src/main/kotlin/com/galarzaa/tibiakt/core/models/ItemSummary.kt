package com.galarzaa.tibiakt.core.models

data class ItemSummary(
    override val currentPage: Int,
    override val totalPages: Int,
    override val resultsCount: Int,
    override val entries: List<DisplayItem>,
    val fullyFetched: Boolean = false,
) : Paginated<DisplayItem>