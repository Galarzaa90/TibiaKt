@file:UseSerializers(LocalDateSerializer::class)

package com.galarzaa.tibiakt.core.models.forums


import com.galarzaa.tibiakt.core.models.PaginatedWithUrl
import com.galarzaa.tibiakt.core.serializers.LocalDateSerializer
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
) : PaginatedWithUrl<CMPost> {
    val url get() = getCMPostArchiveUrl(startDate, endDate, currentPage)

    override fun getPageUrl(page: Int) = getCMPostArchiveUrl(startDate, endDate, page)
}

