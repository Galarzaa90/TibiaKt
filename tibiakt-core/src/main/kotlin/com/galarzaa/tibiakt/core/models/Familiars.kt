package com.galarzaa.tibiakt.core.models

import kotlinx.serialization.Serializable

@Serializable
data class Familiars(
    override val currentPage: Int,
    override val totalPages: Int,
    override val resultsCount: Int,
    override val entries: List<DisplayFamiliar> = emptyList(),
    val fullyFetched: Boolean = false,
) : Paginated<DisplayFamiliar>