package com.galarzaa.tibiakt.core.models.bazaar

import kotlinx.serialization.Serializable

/**
 * The familiars of a character in an [Auction].
 */
@Serializable
data class Familiars(
    override val currentPage: Int,
    override val totalPages: Int,
    override val resultsCount: Int,
    override val entries: List<FamiliarEntry> = emptyList(),
    override val fullyFetched: Boolean = false,
) : AjaxPaginator<FamiliarEntry>