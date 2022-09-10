package com.galarzaa.tibiakt.core.builders

import com.galarzaa.tibiakt.core.enums.BazaarType
import com.galarzaa.tibiakt.core.models.bazaar.Auction
import com.galarzaa.tibiakt.core.models.bazaar.BazaarFilters
import com.galarzaa.tibiakt.core.models.bazaar.CharacterBazaar
import com.galarzaa.tibiakt.core.utils.BuilderDsl


@BuilderDsl
public inline fun characterBazaar(block: CharacterBazaarBuilder.() -> Unit): CharacterBazaar =
    characterBazaarBuilder(block).build()

@BuilderDsl
public inline fun characterBazaarBuilder(block: CharacterBazaarBuilder.() -> Unit): CharacterBazaarBuilder =
    CharacterBazaarBuilder().apply(block)

public class CharacterBazaarBuilder : TibiaKtBuilder<CharacterBazaar>() {
    public var type: BazaarType = BazaarType.CURRENT
    public var currentPage: Int = 0
    public var totalPages: Int = 1
    public var resultsCount: Int = 0
    public var filters: BazaarFilters = BazaarFilters()
    private val entries: MutableList<Auction> = mutableListOf()


    public fun addEntry(entry: Auction): CharacterBazaarBuilder = apply { entries.add(entry) }

    @BuilderDsl
    public fun auction(block: AuctionBuilder.() -> Unit): CharacterBazaarBuilder =
        apply { entries.add(AuctionBuilder().apply(block).build()) }

    override fun build(): CharacterBazaar = CharacterBazaar(
        type = type,
        filters = filters,
        currentPage = currentPage,
        totalPages = totalPages,
        resultsCount = resultsCount,
        entries = entries,
    )
}
