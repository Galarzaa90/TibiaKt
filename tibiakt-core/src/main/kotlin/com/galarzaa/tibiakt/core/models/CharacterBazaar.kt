package com.galarzaa.tibiakt.core.models

import com.galarzaa.tibiakt.core.enums.BazaarType

data class CharacterBazaar(
    val type: BazaarType,
    override val currentPage: Int,
    override val totalPages: Int,
    override val resultsCount: Int,
    override val entries: List<AuctionEntry>,
) : Paginated<AuctionEntry>



