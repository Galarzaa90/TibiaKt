package com.galarzaa.tibiakt.core.builders

import com.galarzaa.tibiakt.core.enums.BazaarType
import com.galarzaa.tibiakt.core.models.bazaar.Auction
import com.galarzaa.tibiakt.core.models.bazaar.BazaarFilters
import com.galarzaa.tibiakt.core.models.bazaar.CharacterBazaar

class CharacterBazaarBuilder {
    private var type: BazaarType? = BazaarType.CURRENT
    private var currentPage: Int = 0
    private var totalPages: Int = 1
    private var resultsCount: Int = 0
    private val entries: MutableList<Auction> = mutableListOf()
    private var filters: BazaarFilters = BazaarFilters()

    fun type(type: BazaarType) = apply { this.type = type }
    fun currentPage(currentPage: Int) = apply { this.currentPage = currentPage }
    fun totalPages(totalPages: Int) = apply { this.totalPages = totalPages }
    fun resultsCount(resultsCount: Int) = apply { this.resultsCount = resultsCount }
    fun addEntry(entry: Auction) = apply { entries.add(entry) }
    fun filters(filters: BazaarFilters) = apply { this.filters = filters }

    fun build() = CharacterBazaar(
        type = type ?: throw IllegalStateException("type is required"),
        currentPage = currentPage,
        totalPages = totalPages,
        resultsCount = resultsCount,
        entries = entries,
        filters = filters,
    )
}