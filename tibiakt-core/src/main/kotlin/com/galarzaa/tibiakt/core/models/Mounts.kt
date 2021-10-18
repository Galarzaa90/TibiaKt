package com.galarzaa.tibiakt.core.models

import kotlinx.serialization.Serializable

@Serializable
data class Mounts(
    override val currentPage: Int,
    override val totalPages: Int,
    override val resultsCount: Int,
    override val entries: List<DisplayMount>,
    val fullyFetched: Boolean = false,
) : Paginated<DisplayMount>