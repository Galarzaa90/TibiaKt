package com.galarzaa.tibiakt.core.builders

import com.galarzaa.tibiakt.core.models.Leaderboards
import com.galarzaa.tibiakt.core.models.LeaderboardsEntry
import com.galarzaa.tibiakt.core.utils.BuilderDsl
import java.time.Instant

@BuilderDsl
inline fun leaderboards(block: LeaderboardsBuilder.() -> Unit) = LeaderboardsBuilder().apply(block).build()

@BuilderDsl
inline fun leaderboardsBuilder(block: LeaderboardsBuilder.() -> Unit) = LeaderboardsBuilder().apply(block)

@BuilderDsl
class LeaderboardsBuilder {
    var world: String? = null
    var rotation: Int? = null
    var lastUpdated: Instant? = null
    var currentPage: Int? = null
    var totalPages: Int? = null
    var resultsCount: Int? = null
    var entries: MutableList<LeaderboardsEntry> = mutableListOf()

    fun addEntry(entry: LeaderboardsEntry) = apply { entries.add(entry) }

    fun build() = Leaderboards(
        world = world ?: throw IllegalArgumentException("world is required"),
        rotation = rotation ?: throw IllegalArgumentException("rotation is required"),
        lastUpdated = lastUpdated,
        currentPage = currentPage ?: throw IllegalArgumentException("currentPage is required"),
        totalPages = totalPages ?: throw IllegalArgumentException("totalPages is required"),
        resultsCount = resultsCount ?: throw IllegalArgumentException("resultsCount is required"),
        entries = entries
    )

}