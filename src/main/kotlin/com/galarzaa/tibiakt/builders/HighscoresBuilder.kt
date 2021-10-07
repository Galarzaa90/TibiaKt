package com.galarzaa.tibiakt.builders

import com.galarzaa.tibiakt.enums.*
import com.galarzaa.tibiakt.models.Highscores
import com.galarzaa.tibiakt.models.HighscoresEntry
import java.time.Instant

class HighscoresBuilder {
    private var world: String? = null
    private var category: HighscoresCategory? = null
    private var vocation: HighscoresProfession? = null
    private val worldTypes: MutableSet<HighscoresPvpType> = mutableSetOf()
    private var battlEyeType: HighscoresBattlEyeType? = null
    private var lastUpdate: Instant? = null
    private var currentPage: Int = 1
    private var totalPages: Int = 1
    private var resultsCount: Int = 0
    private val entries: MutableList<HighscoresEntry> = mutableListOf()

    fun world(world: String?) = apply { this.world = world }
    fun category(category: HighscoresCategory?) = apply { this.category = category }
    fun vocation(vocation: HighscoresProfession?) = apply { this.vocation = vocation }
    fun addWorldType(worldType: HighscoresPvpType) = apply { this.worldTypes.add(worldType) }
    fun battlEyeType(battlEyeType: HighscoresBattlEyeType?) = apply { this.battlEyeType = battlEyeType }
    fun lastUpdate(lastUpdate: Instant) = apply { this.lastUpdate = lastUpdate }
    fun currentPage(currentPage: Int) = apply { this.currentPage = currentPage }
    fun totalPages(totalPages: Int) = apply { this.totalPages = totalPages }
    fun resultsCount(resultsCount: Int) = apply { this.resultsCount = resultsCount }
    fun addEntry(
        rank: Int,
        name: String,
        vocation: Vocation,
        world: String,
        level: Int,
        points: Long,
        extra: String? = null
    ) = apply {
        entries.add(HighscoresEntry(rank, name, level, world, vocation, points, extra))
    }

    fun build() = Highscores(
        world = world,
        category = category ?: HighscoresCategory.EXPERIENCE_POINTS,
        vocation = vocation ?: HighscoresProfession.ALL,
        worldTypes = worldTypes,
        battlEyeType = battlEyeType ?: HighscoresBattlEyeType.ANY_WORLD,
        lastUpdate = lastUpdate ?: throw IllegalStateException("lastUpdate is required"),
        currentPage = currentPage,
        totalPages = totalPages,
        resultsCount = resultsCount,
        entries = entries
    )
}