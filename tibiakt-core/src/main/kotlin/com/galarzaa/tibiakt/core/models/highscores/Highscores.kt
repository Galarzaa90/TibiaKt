/*
 * Copyright © 2024 Allan Galarza
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.galarzaa.tibiakt.core.models.highscores

import com.galarzaa.tibiakt.core.enums.HighscoresBattlEyeType
import com.galarzaa.tibiakt.core.enums.HighscoresCategory
import com.galarzaa.tibiakt.core.enums.HighscoresProfession
import com.galarzaa.tibiakt.core.enums.PvpType
import com.galarzaa.tibiakt.core.models.PaginatedWithUrl
import com.galarzaa.tibiakt.core.utils.getHighscoresUrl
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

/**
 * The Tibia highscores, containing the highest levels of a given [category].
 *
 * @property world The world the highscores belong to, or null if these are global highscores.
 * @property category The displayed highscores category.
 * @property vocation The selected vocation filter for the entries.
 * @property worldTypes The selected world types to show for global highscores.
 * @property battlEyeType The BattlEye type of worlds to show for global highscores.
 * @property lastUpdated The time when the currently displayed results were last updated.
 */
@Serializable
public data class Highscores(
    val world: String?,
    val category: HighscoresCategory,
    val vocation: HighscoresProfession,
    val worldTypes: Set<PvpType>,
    val battlEyeType: HighscoresBattlEyeType,
    val lastUpdated: Instant,
    override val currentPage: Int,
    override val totalPages: Int,
    override val resultsCount: Int,
    override val entries: List<HighscoresEntry>,
) : PaginatedWithUrl<HighscoresEntry> {

    val url: String get() = getHighscoresUrl(world, category, vocation, currentPage, battlEyeType, worldTypes)

    override fun getPageUrl(page: Int): String =
        getHighscoresUrl(world, category, vocation, page, battlEyeType, worldTypes)

}
