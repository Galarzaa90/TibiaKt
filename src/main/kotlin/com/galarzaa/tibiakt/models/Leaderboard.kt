package com.galarzaa.tibiakt.models

data class Leaderboard(
    val world: String,
    val rotation: Int,
    val pageCurrent: Int,
    val pageTotal: Int,
    val resultsCount: Int,
    val entries: List<HighscoresEntry>
)
