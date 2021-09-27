@file:UseSerializers(InstantSerializer::class)
package com.galarzaa.tibiakt.models

import com.galarzaa.tibiakt.InstantSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.time.Instant

@Serializable
data class Highscores(
    val world: String?,
    val category: HighscoresCategory,
    val vocation: HighscoresProfession,
    val worldTypes: Set<HighscoresPvpType> = emptySet(),
    val battlEyeType: BattlEyeType,
    val lastUpdate: Instant,
    val pageCurrent: Int,
    val pageTotal: Int,
    val resultsCount: Int,
    val entries: List<LeaderboardEntry>
)

