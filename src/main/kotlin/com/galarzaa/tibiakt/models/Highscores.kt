package com.galarzaa.tibiakt.models

import kotlinx.serialization.Serializable

@Serializable
data class Highscores(
    val world: String?,
    val category: String,
    val vocation: String,
    val pageCurrent: Int,
    val pageTotal: Int,
    val resultsCount: Int,
    val entries: List<HighscoresEntry>
)

