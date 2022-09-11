@file:UseSerializers(InstantSerializer::class)

package com.galarzaa.tibiakt.core.models.highscores

import com.galarzaa.tibiakt.core.enums.HighscoresBattlEyeType
import com.galarzaa.tibiakt.core.enums.HighscoresCategory
import com.galarzaa.tibiakt.core.enums.HighscoresProfession
import com.galarzaa.tibiakt.core.enums.PvpType
import com.galarzaa.tibiakt.core.models.PaginatedWithUrl
import com.galarzaa.tibiakt.core.serializers.InstantSerializer
import com.galarzaa.tibiakt.core.utils.getHighscoresUrl
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers

/**
 * The Tibia highscores, containing the highest levels of a given [category]
 *
 * @property world The world the highscores belong to, or null if these are global highscores.
 * @property category The displayed highscores category.
 * @property vocation The selected vocation filter for the entries.
 * @property worldTypes The selected world types to show for global highscores.
 * @property battlEyeType The BattlEye type of worlds to show for global highscores.
 * @property lastUpdate The time when the currently displayed results were last updated.
 */
@Serializable
public data class Highscores(
    val world: String?,
    val category: HighscoresCategory,
    val vocation: HighscoresProfession,
    val worldTypes: Set<PvpType>,
    val battlEyeType: HighscoresBattlEyeType,
    val lastUpdate: Instant,
    override val currentPage: Int,
    override val totalPages: Int,
    override val resultsCount: Int,
    override val entries: List<HighscoresEntry>,
) : PaginatedWithUrl<HighscoresEntry> {

    val url: String get() = getHighscoresUrl(world, category, vocation, currentPage, battlEyeType, worldTypes)
    override fun getPageUrl(page: Int): String =
        getHighscoresUrl(world, category, vocation, page, battlEyeType, worldTypes)
}
