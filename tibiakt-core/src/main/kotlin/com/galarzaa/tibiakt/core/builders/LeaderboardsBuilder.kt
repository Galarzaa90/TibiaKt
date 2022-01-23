package com.galarzaa.tibiakt.core.builders

import com.galarzaa.tibiakt.core.models.Leaderboards
import com.galarzaa.tibiakt.core.models.LeaderboardsEntry
import java.time.Instant

class LeaderboardsBuilder {
    private var world: String? = null
    private var rotation: Int? = null
    private var lastUpdated: Instant? = null
    private var currentPage: Int? = null
    private var totalPages: Int? = null
    private var resultsCount: Int? = null
    private var entries: MutableList<LeaderboardsEntry> = mutableListOf()

    fun rotation(rotation: Int) = apply { this.rotation = rotation }
    fun world(world: String) = apply { this.world = world }
    fun lastUpdated(lastUpdated: Instant) = apply { this.lastUpdated = lastUpdated }
    fun currentPage(currentPage: Int) = apply { this.currentPage = currentPage }
    fun totalPages(totalPages: Int) = apply { this.totalPages = totalPages }
    fun resultsCount(resultsCount: Int) = apply { this.resultsCount = resultsCount }
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