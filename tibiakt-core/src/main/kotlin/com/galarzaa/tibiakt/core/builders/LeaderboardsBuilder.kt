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

import com.galarzaa.tibiakt.core.models.leaderboards.BaseLeaderboardsEntry
import com.galarzaa.tibiakt.core.models.leaderboards.Leaderboards
import com.galarzaa.tibiakt.core.models.leaderboards.LeaderboardsRotation
import com.galarzaa.tibiakt.core.utils.BuilderDsl
import kotlinx.datetime.Instant

@BuilderDsl
public inline fun leaderboards(block: LeaderboardsBuilder.() -> Unit): Leaderboards =
    LeaderboardsBuilder().apply(block).build()

@BuilderDsl
public inline fun leaderboardsBuilder(block: LeaderboardsBuilder.() -> Unit): LeaderboardsBuilder =
    LeaderboardsBuilder().apply(block)

@BuilderDsl
public class LeaderboardsBuilder : TibiaKtBuilder<Leaderboards> {
    public var world: String? = null
    public var rotation: LeaderboardsRotation? = null
    public val availableRotations: MutableList<LeaderboardsRotation> = mutableListOf()
    public var lastUpdated: Instant? = null
    public var currentPage: Int? = null
    public var totalPages: Int? = null
    public var resultsCount: Int? = null
    public val entries: MutableList<BaseLeaderboardsEntry> = mutableListOf()

    @BuilderDsl
    public fun rotation(body: LeaderboardsRotationBuilder.() -> Unit): LeaderboardsBuilder =
        apply { rotation = LeaderboardsRotationBuilder().apply(body).build() }

    @BuilderDsl
    public fun addAvailableRotation(rotation: LeaderboardsRotation): LeaderboardsBuilder =
        apply { availableRotations.add(rotation) }

    public fun addEntry(entry: BaseLeaderboardsEntry): LeaderboardsBuilder = apply { entries.add(entry) }


    override fun build(): Leaderboards = Leaderboards(
        world = world ?: error("world is required"),
        rotation = rotation ?: error("rotation is required"),
        availableRotations = availableRotations,
        lastUpdated = lastUpdated,
        currentPage = currentPage ?: error("currentPage is required"),
        totalPages = totalPages ?: error("totalPages is required"),
        resultsCount = resultsCount ?: error("resultsCount is required"),
        entries = entries
    )

    public class LeaderboardsRotationBuilder : TibiaKtBuilder<LeaderboardsRotation> {
        public var rotationId: Int? = null
        public var current: Boolean = false
        public var endDate: Instant? = null
        override fun build(): LeaderboardsRotation = LeaderboardsRotation(
            rotationId = rotationId ?: error("rotationId is required"),
            current = current,
            endDate = endDate ?: error("endDate is required"),
        )

    }

}
