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
import com.galarzaa.tibiakt.core.section.community.leaderboard.model.BaseLeaderboardEntry
import com.galarzaa.tibiakt.core.section.community.leaderboard.model.Leaderboard
import com.galarzaa.tibiakt.core.section.community.leaderboard.model.LeaderboardRotation
import kotlin.time.Instant

@BuilderDsl
public inline fun leaderboard(block: LeaderboardBuilder.() -> Unit): Leaderboard =
    LeaderboardBuilder().apply(block).build()

@BuilderDsl
public inline fun leaderboardBuilder(block: LeaderboardBuilder.() -> Unit): LeaderboardBuilder =
    LeaderboardBuilder().apply(block)

/** Builder for [Leaderboard] instances. */
@BuilderDsl
public class LeaderboardBuilder : TibiaKtBuilder<Leaderboard> {
    public var world: String? = null
    public var rotation: LeaderboardRotation? = null
    public val availableRotations: MutableList<LeaderboardRotation> = mutableListOf()
    public var lastUpdatedAt: Instant? = null
    public var currentPage: Int? = null
    public var totalPages: Int? = null
    public var resultsCount: Int? = null
    public val entries: MutableList<BaseLeaderboardEntry> = mutableListOf()

    @BuilderDsl
    public fun rotation(body: LeaderboardRotationBuilder.() -> Unit): LeaderboardBuilder =
        apply { rotation = LeaderboardRotationBuilder().apply(body).build() }

    @BuilderDsl
    public fun addAvailableRotation(rotation: LeaderboardRotation): LeaderboardBuilder =
        apply { availableRotations.add(rotation) }

    public fun addEntry(entry: BaseLeaderboardEntry): LeaderboardBuilder = apply { entries.add(entry) }


    override fun build(): Leaderboard = Leaderboard(
        world = world ?: error("world is required"),
        rotation = rotation ?: error("rotation is required"),
        availableRotations = availableRotations,
        lastUpdatedAt = lastUpdatedAt,
        currentPage = currentPage ?: error("currentPage is required"),
        totalPages = totalPages ?: error("totalPages is required"),
        resultsCount = resultsCount ?: error("resultsCount is required"),
        entries = entries
    )

    public class LeaderboardRotationBuilder : TibiaKtBuilder<LeaderboardRotation> {
        public var rotationId: Int? = null
        public var isCurrent: Boolean = false
        public var endDate: Instant? = null
        override fun build(): LeaderboardRotation = LeaderboardRotation(
            rotationId = rotationId ?: error("rotationId is required"),
            isCurrent = isCurrent,
            endsAt = endDate ?: error("endDate is required"),
        )

    }

}
