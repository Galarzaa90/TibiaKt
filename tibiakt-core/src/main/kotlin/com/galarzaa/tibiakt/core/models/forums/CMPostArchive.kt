@file:UseSerializers(LocalDateSerializer::class)

package com.galarzaa.tibiakt.core.models.forums


import com.galarzaa.tibiakt.core.models.Paginated
import com.galarzaa.tibiakt.core.utils.LocalDateSerializer
import com.galarzaa.tibiakt.core.utils.getCMPostArchiveUrl
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.time.LocalDate

/**
 * The CM Post Archive, displaying recent posts by Community Managers.
 */
@Serializable
data class CMPostArchive(
    val startDate: LocalDate,
    val endDate: LocalDate,
    override val currentPage: Int,
    override val totalPages: Int,
    override val resultsCount: Int,
    override val entries: List<CMPost>,
) : Paginated<CMPost> {
    val url
        get() = getCMPostArchiveUrl(startDate, endDate, currentPage)
}

