package com.galarzaa.tibiakt.core.models

import com.galarzaa.tibiakt.core.enums.BazaarType
import kotlinx.serialization.Serializable

@Serializable
data class CharacterBazaar(
    val type: BazaarType,
    override val currentPage: Int,
    override val totalPages: Int,
    override val resultsCount: Int,
    override val entries: List<Auction> = emptyList(),
    val filters: BazaarFilters = BazaarFilters(),
) : Paginated<Auction>