package com.galarzaa.tibiakt.core.models.forums

import com.galarzaa.tibiakt.core.models.Paginated
import java.time.LocalDate

data class CMPostArchive(
    val startDate: LocalDate,
    val endDate: LocalDate,
    override val currentPage: Int,
    override val totalPages: Int,
    override val resultsCount: Int,
    override val entries: List<CMPost>,
) : Paginated<CMPost>

