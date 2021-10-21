package com.galarzaa.tibiakt.core.models.bazaar

import com.galarzaa.tibiakt.core.enums.BazaarType
import com.galarzaa.tibiakt.core.models.Paginated
import com.galarzaa.tibiakt.core.utils.getBazaarUrl
import kotlinx.serialization.Serializable

@Serializable
data class CharacterBazaar(
    val type: BazaarType,
    val filters: BazaarFilters = BazaarFilters(),
    override val currentPage: Int,
    override val totalPages: Int,
    override val resultsCount: Int,
    override val entries: List<Auction> = emptyList(),
) : Paginated<Auction> {
    val url get() = getBazaarUrl(type, filters, currentPage)
}