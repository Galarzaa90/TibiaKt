package com.galarzaa.tibiakt.core.models

import kotlinx.serialization.Serializable

@Serializable
data class ItemSummary(
    override val currentPage: Int,
    override val totalPages: Int,
    override val resultsCount: Int,
    override val entries: List<DisplayItem>,
    val fullyFetched: Boolean = false,
) : Paginated<DisplayItem>