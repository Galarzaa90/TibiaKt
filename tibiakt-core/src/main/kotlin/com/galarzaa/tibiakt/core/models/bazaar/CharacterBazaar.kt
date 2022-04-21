package com.galarzaa.tibiakt.core.models.bazaar

import com.galarzaa.tibiakt.core.enums.BazaarType
import com.galarzaa.tibiakt.core.models.PaginatedWithUrl
import com.galarzaa.tibiakt.core.utils.getBazaarUrl
import kotlinx.serialization.Serializable

/**
 * The Character Bazaar.
 *
 * @property type The type of bazaar, current auctions or auction history.
 * @property filters The currently active filters for the bazaar.
 */
@Serializable
data class CharacterBazaar(
    val type: BazaarType,
    val filters: BazaarFilters = BazaarFilters(),
    override val currentPage: Int,
    override val totalPages: Int,
    override val resultsCount: Int,
    override val entries: List<Auction>,
) : PaginatedWithUrl<Auction> {
    /**
     * The URL to the bazaar with the current filters and page.
     */
    val url get() = getBazaarUrl(type, filters, currentPage)
    override fun getPageUrl(page: Int) = getBazaarUrl(type, filters, page)
}