package com.galarzaa.tibiakt.core.models

data class Familiars(
    override val currentPage: Int,
    override val totalPages: Int,
    override val resultsCount: Int,
    override val entries: List<DisplayFamiliar>,
    val fullyFetched: Boolean = false,
) : Paginated<DisplayFamiliar>