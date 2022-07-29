package com.galarzaa.tibiakt.core.builders

import com.galarzaa.tibiakt.core.enums.HighscoresBattlEyeType
import com.galarzaa.tibiakt.core.enums.HighscoresCategory
import com.galarzaa.tibiakt.core.enums.HighscoresProfession
import com.galarzaa.tibiakt.core.enums.PvpType
import com.galarzaa.tibiakt.core.enums.Vocation
import com.galarzaa.tibiakt.core.models.highscores.Highscores
import com.galarzaa.tibiakt.core.models.highscores.HighscoresEntry
import com.galarzaa.tibiakt.core.utils.BuilderDsl
import kotlinx.datetime.Instant

@BuilderDsl
inline fun highscores(block: HighscoresBuilder.() -> Unit) = HighscoresBuilder().apply(block).build()

@BuilderDsl
inline fun highscoresBuilder(block: HighscoresBuilder.() -> Unit) = HighscoresBuilder().apply(block)

@BuilderDsl
class HighscoresBuilder : TibiaKtBuilder<Highscores>() {
    var world: String? = null
    var category: HighscoresCategory? = null
    var vocation: HighscoresProfession? = null
    val worldTypes: MutableSet<PvpType> = mutableSetOf()
    var battlEyeType: HighscoresBattlEyeType? = null
    var lastUpdate: Instant? = null
    var currentPage: Int = 1
    var totalPages: Int = 1
    var resultsCount: Int = 0
    val entries: MutableList<HighscoresEntry> = mutableListOf()

    fun addEntry(
        rank: Int,
        name: String,
        vocation: Vocation,
        world: String,
        level: Int,
        points: Long,
        extra: String? = null,
    ) = apply {
        entries.add(HighscoresEntry(rank, name, level, world, vocation, points, extra))
    }

    @BuilderDsl
    fun addEntry(block: HighscoresEntryBuilder.() -> Unit) = entries.add(HighscoresEntryBuilder().apply(block).build())

    override fun build() = Highscores(world = world,
        category = category ?: HighscoresCategory.EXPERIENCE_POINTS,
        vocation = vocation ?: HighscoresProfession.ALL,
        worldTypes = worldTypes,
        battlEyeType = battlEyeType ?: HighscoresBattlEyeType.ANY_WORLD,
        lastUpdate = lastUpdate ?: throw IllegalStateException("lastUpdate is required"),
        currentPage = currentPage,
        totalPages = totalPages,
        resultsCount = resultsCount,
        entries = entries)

    @BuilderDsl
    class HighscoresEntryBuilder : TibiaKtBuilder<HighscoresEntry>() {
        var rank: Int = 0
        lateinit var name: String
        lateinit var vocation: Vocation
        lateinit var world: String
        var level: Int = 0
        var value: Long = 0
        var additionalValue: String? = null

        override fun build() = HighscoresEntry(
            rank = rank,
            name = if (::name.isInitialized) name else throw IllegalStateException("name is required"),
            vocation = if (::vocation.isInitialized) vocation else throw IllegalStateException("vocation is required"),
            world = if (::world.isInitialized) world else throw IllegalStateException("world is required"),
            level = level,
            value = value,
            additionalValue = additionalValue,
        )
    }
}