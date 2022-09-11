/*
 * Copyright © 2022 Allan Galarza
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
public inline fun highscores(block: HighscoresBuilder.() -> Unit): Highscores = HighscoresBuilder().apply(block).build()

@BuilderDsl
public inline fun highscoresBuilder(block: HighscoresBuilder.() -> Unit): HighscoresBuilder =
    HighscoresBuilder().apply(block)

/** Builder for [Highscores] instances. */
@BuilderDsl
public class HighscoresBuilder : TibiaKtBuilder<Highscores> {
    public var world: String? = null
    public var category: HighscoresCategory? = null
    public var vocation: HighscoresProfession? = null
    public val worldTypes: MutableSet<PvpType> = mutableSetOf()
    public var battlEyeType: HighscoresBattlEyeType? = null
    public var lastUpdate: Instant? = null
    public var currentPage: Int = 1
    public var totalPages: Int = 1
    public var resultsCount: Int = 0
    public val entries: MutableList<HighscoresEntry> = mutableListOf()

    public fun addEntry(
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
    public fun addEntry(block: HighscoresEntryBuilder.() -> Unit): Boolean =
        entries.add(HighscoresEntryBuilder().apply(block).build())

    override fun build(): Highscores = Highscores(
        world = world,
        category = category ?: HighscoresCategory.EXPERIENCE_POINTS,
        vocation = vocation ?: HighscoresProfession.ALL,
        worldTypes = worldTypes,
        battlEyeType = battlEyeType ?: HighscoresBattlEyeType.ANY_WORLD,
        lastUpdate = lastUpdate ?: error("lastUpdate is required"),
        currentPage = currentPage,
        totalPages = totalPages,
        resultsCount = resultsCount,
        entries = entries
    )

    @BuilderDsl
    public class HighscoresEntryBuilder : TibiaKtBuilder<HighscoresEntry> {
        public var rank: Int = 0
        public lateinit var name: String
        public lateinit var vocation: Vocation
        public lateinit var world: String
        public var level: Int = 0
        public var value: Long = 0
        public var additionalValue: String? = null

        override fun build(): HighscoresEntry = HighscoresEntry(
            rank = rank,
            name = if (::name.isInitialized) name else error("name is required"),
            vocation = if (::vocation.isInitialized) vocation else error("vocation is required"),
            world = if (::world.isInitialized) world else error("world is required"),
            level = level,
            value = value,
            additionalValue = additionalValue,
        )
    }
}
