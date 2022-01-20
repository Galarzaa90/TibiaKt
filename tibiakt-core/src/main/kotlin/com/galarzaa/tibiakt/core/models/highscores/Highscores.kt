@file:UseSerializers(InstantSerializer::class)

package com.galarzaa.tibiakt.core.models.highscores

import com.galarzaa.tibiakt.core.enums.HighscoresBattlEyeType
import com.galarzaa.tibiakt.core.enums.HighscoresCategory
import com.galarzaa.tibiakt.core.enums.HighscoresProfession
import com.galarzaa.tibiakt.core.enums.HighscoresPvpType
import com.galarzaa.tibiakt.core.models.Paginated
import com.galarzaa.tibiakt.core.utils.InstantSerializer
import com.galarzaa.tibiakt.core.utils.getHighscoresUrl
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.time.Instant

@Serializable
data class Highscores(
    val world: String?,
    val category: HighscoresCategory,
    val vocation: HighscoresProfession,
    val worldTypes: Set<HighscoresPvpType> = emptySet(),
    val battlEyeType: HighscoresBattlEyeType,
    val lastUpdate: Instant,
    override val currentPage: Int,
    override val totalPages: Int,
    override val resultsCount: Int,
    override val entries: List<HighscoresEntry> = emptyList(),
) : Paginated<HighscoresEntry> {

    fun getPageUrl(page: Int) = getHighscoresUrl(world, category, vocation, page, battlEyeType, worldTypes)

    val url get() = getPageUrl(currentPage)

    val nextPageUrl: String?
        get() = if (currentPage == totalPages) null else getPageUrl(currentPage + 1)

    val previousPageUrl: String?
        get() = if (currentPage == 0) null else getPageUrl(currentPage - 1)
}
