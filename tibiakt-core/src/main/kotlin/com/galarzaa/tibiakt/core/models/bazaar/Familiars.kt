package com.galarzaa.tibiakt.core.models.bazaar

import kotlinx.serialization.Serializable

/**
 * The familiars of a character in an [Auction].
 */
@Serializable
public data class Familiars(
    override val currentPage: Int,
    override val totalPages: Int,
    override val resultsCount: Int,
    override val entries: List<FamiliarEntry>,
    override val fullyFetched: Boolean,
) : AjaxPaginator<FamiliarEntry>
