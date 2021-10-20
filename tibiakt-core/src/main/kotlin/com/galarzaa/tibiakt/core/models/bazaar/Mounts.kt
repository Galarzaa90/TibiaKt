package com.galarzaa.tibiakt.core.models.bazaar

import com.galarzaa.tibiakt.core.models.Paginated
import kotlinx.serialization.Serializable

@Serializable
data class Mounts(
    override val currentPage: Int,
    override val totalPages: Int,
    override val resultsCount: Int,
    override val entries: List<DisplayMount> = emptyList(),
    val fullyFetched: Boolean = false,
) : Paginated<DisplayMount>