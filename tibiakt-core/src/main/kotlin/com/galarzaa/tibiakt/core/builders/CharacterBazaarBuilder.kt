package com.galarzaa.tibiakt.core.builders

import com.galarzaa.tibiakt.core.enums.BazaarType
import com.galarzaa.tibiakt.core.models.bazaar.Auction
import com.galarzaa.tibiakt.core.models.bazaar.BazaarFilters
import com.galarzaa.tibiakt.core.models.bazaar.CharacterBazaar
import com.galarzaa.tibiakt.core.utils.BuilderDsl


@BuilderDsl
inline fun characterBazaar(block: CharacterBazaarBuilder.() -> Unit) = characterBazaarBuilder(block).build()

@BuilderDsl
inline fun characterBazaarBuilder(block: CharacterBazaarBuilder.() -> Unit) = CharacterBazaarBuilder().apply(block)

class CharacterBazaarBuilder {
    var type: BazaarType = BazaarType.CURRENT
    var currentPage: Int = 0
    var totalPages: Int = 1
    var resultsCount: Int = 0
    var filters: BazaarFilters = BazaarFilters()
    private val entries: MutableList<Auction> = mutableListOf()


    fun addEntry(entry: Auction) = apply { entries.add(entry) }

    @BuilderDsl
    fun auction(block: AuctionBuilder.() -> Unit) = apply { entries.add(AuctionBuilder().apply(block).build()) }

    fun build() = CharacterBazaar(
        type = type,
        filters = filters,
        currentPage = currentPage,
        totalPages = totalPages,
        resultsCount = resultsCount,
        entries = entries,
    )
}