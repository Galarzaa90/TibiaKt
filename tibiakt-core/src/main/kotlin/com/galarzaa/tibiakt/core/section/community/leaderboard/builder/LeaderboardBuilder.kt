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

package com.galarzaa.tibiakt.core.section.community.leaderboard.builder

import com.galarzaa.tibiakt.core.builder.BuilderDsl
import com.galarzaa.tibiakt.core.builder.TibiaKtBuilder
import com.galarzaa.tibiakt.core.builder.requireField
import com.galarzaa.tibiakt.core.section.community.leaderboard.model.Leaderboard
import com.galarzaa.tibiakt.core.section.community.leaderboard.model.LeaderboardEntry
import com.galarzaa.tibiakt.core.section.community.leaderboard.model.LeaderboardRotation
import kotlin.time.Instant

@BuilderDsl
internal inline fun leaderboard(block: LeaderboardBuilder.() -> Unit): Leaderboard =
    LeaderboardBuilder().apply(block).build()

@BuilderDsl
internal inline fun leaderboardBuilder(block: LeaderboardBuilder.() -> Unit): LeaderboardBuilder =
    LeaderboardBuilder().apply(block)

/** Builder for [Leaderboard] instances. */
@BuilderDsl
internal class LeaderboardBuilder : TibiaKtBuilder<Leaderboard> {
    var world: String? = null
    var rotation: LeaderboardRotation? = null
    val availableRotations: MutableList<LeaderboardRotation> = mutableListOf()
    var lastUpdatedAt: Instant? = null
    var currentPage: Int? = null
    var totalPages: Int? = null
    var resultsCount: Int? = null
    val entries: MutableList<LeaderboardEntry> = mutableListOf()

    @BuilderDsl
    fun rotation(body: LeaderboardRotationBuilder.() -> Unit): LeaderboardBuilder =
        apply { rotation = LeaderboardRotationBuilder().apply(body).build() }

    @BuilderDsl
    fun addAvailableRotation(rotation: LeaderboardRotation): LeaderboardBuilder =
        apply { availableRotations.add(rotation) }

    fun addEntry(entry: LeaderboardEntry): LeaderboardBuilder = apply { entries.add(entry) }


    override fun build(): Leaderboard = Leaderboard(
        world = requireField(world, "world"),
        rotation = requireField(rotation, "rotation"),
        availableRotations = availableRotations,
        lastUpdatedAt = lastUpdatedAt,
        currentPage = requireField(currentPage, "currentPage"),
        totalPages = requireField(totalPages, "totalPages"),
        resultsCount = requireField(resultsCount, "resultsCount"),
        entries = entries
    )

    class LeaderboardRotationBuilder : TibiaKtBuilder<LeaderboardRotation> {
        var rotationId: Int? = null
        var isCurrent: Boolean = false
        var endsAt: Instant? = null
        override fun build(): LeaderboardRotation = LeaderboardRotation(
            rotationId = requireField(rotationId, "rotationId"),
            isCurrent = isCurrent,
            endsAt = requireField(endsAt, "endsAt"),
        )

    }

}
