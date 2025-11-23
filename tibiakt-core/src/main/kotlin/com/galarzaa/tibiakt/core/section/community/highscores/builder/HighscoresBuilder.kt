/*
 * Copyright © 2025 Allan Galarza
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

package com.galarzaa.tibiakt.core.section.community.highscores.builder

import com.galarzaa.tibiakt.core.builder.BuilderDsl
import com.galarzaa.tibiakt.core.builder.TibiaKtBuilder
import com.galarzaa.tibiakt.core.builder.requireField
import com.galarzaa.tibiakt.core.domain.character.Vocation
import com.galarzaa.tibiakt.core.domain.world.PvpType
import com.galarzaa.tibiakt.core.section.community.highscores.model.Highscores
import com.galarzaa.tibiakt.core.section.community.highscores.model.HighscoresBattlEyeType
import com.galarzaa.tibiakt.core.section.community.highscores.model.HighscoresCategory
import com.galarzaa.tibiakt.core.section.community.highscores.model.HighscoresEntry
import com.galarzaa.tibiakt.core.section.community.highscores.model.HighscoresProfession
import kotlin.time.Instant

@BuilderDsl
internal inline fun highscores(block: HighscoresBuilder.() -> Unit): Highscores =
    HighscoresBuilder().apply(block).build()

@BuilderDsl
internal inline fun highscoresBuilder(block: HighscoresBuilder.() -> Unit): HighscoresBuilder =
    HighscoresBuilder().apply(block)

/** Builder for [Highscores] instances. */
@BuilderDsl
internal class HighscoresBuilder : TibiaKtBuilder<Highscores> {
    var world: String? = null
    var category: HighscoresCategory? = null
    var vocation: HighscoresProfession? = null
    val worldTypes: MutableSet<PvpType> = mutableSetOf()
    var battlEyeType: HighscoresBattlEyeType? = null
    var lastUpdatedAt: Instant? = null
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
    ): HighscoresBuilder = apply {
        entries.add(HighscoresEntry(rank, name, level, world, vocation, points, extra))
    }

    @BuilderDsl
    fun addEntry(block: HighscoresEntryBuilder.() -> Unit): Boolean =
        entries.add(HighscoresEntryBuilder().apply(block).build())

    override fun build(): Highscores = Highscores(
        world = world,
        category = category ?: HighscoresCategory.EXPERIENCE_POINTS,
        vocation = vocation ?: HighscoresProfession.ALL,
        worldTypes = worldTypes,
        battlEyeType = battlEyeType ?: HighscoresBattlEyeType.ANY_WORLD,
        lastUpdatedAt = requireField(lastUpdatedAt, "lastUpdatedAt"),
        currentPage = currentPage,
        totalPages = totalPages,
        resultsCount = resultsCount,
        entries = entries
    )

    @BuilderDsl
    class HighscoresEntryBuilder : TibiaKtBuilder<HighscoresEntry> {
        var rank: Int = 0
        lateinit var name: String
        lateinit var vocation: Vocation
        lateinit var world: String
        var level: Int = 0
        var value: Long = 0
        var additionalValue: String? = null

        override fun build(): HighscoresEntry = HighscoresEntry(
            rank = rank,
            name = requireField(::name.isInitialized, "name") { name },
            vocation = requireField(::vocation.isInitialized, "vocation") { vocation },
            world = requireField(::world.isInitialized, "world") { world },
            level = level,
            value = value,
            additionalValue = additionalValue,
        )
    }
}
