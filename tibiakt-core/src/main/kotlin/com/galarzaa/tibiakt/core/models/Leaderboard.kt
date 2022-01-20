package com.galarzaa.tibiakt.core.models

data class Leaderboard(
    val world: String,
    val rotation: Int,
    override val currentPage: Int,
    override val totalPages: Int,
    override val resultsCount: Int,
    override val entries: List<LeaderboardEntry> = emptyList(),
) : Paginated<LeaderboardEntry>
