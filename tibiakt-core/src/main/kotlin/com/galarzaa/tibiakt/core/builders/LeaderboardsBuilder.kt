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

import com.galarzaa.tibiakt.core.models.BaseLeaderboardsEntry
import com.galarzaa.tibiakt.core.models.Leaderboards
import com.galarzaa.tibiakt.core.models.LeaderboardsRotation
import com.galarzaa.tibiakt.core.utils.BuilderDsl
import kotlinx.datetime.Instant

@BuilderDsl
inline fun leaderboards(block: LeaderboardsBuilder.() -> Unit) = LeaderboardsBuilder().apply(block).build()

@BuilderDsl
inline fun leaderboardsBuilder(block: LeaderboardsBuilder.() -> Unit) = LeaderboardsBuilder().apply(block)

@BuilderDsl
class LeaderboardsBuilder : TibiaKtBuilder<Leaderboards>() {
    var world: String? = null
    var rotation: LeaderboardsRotation? = null
    val availableRotations: MutableList<LeaderboardsRotation> = mutableListOf()
    var lastUpdated: Instant? = null
    var currentPage: Int? = null
    var totalPages: Int? = null
    var resultsCount: Int? = null
    var entries: MutableList<BaseLeaderboardsEntry> = mutableListOf()

    @BuilderDsl
    fun rotation(body: LeaderboardsRotationBuilder.() -> Unit) =
        apply { rotation = LeaderboardsRotationBuilder().apply(body).build() }

    @BuilderDsl
    fun addAvailableRotation(rotation: LeaderboardsRotation) = apply { availableRotations.add(rotation) }

    fun addEntry(entry: BaseLeaderboardsEntry) = apply { entries.add(entry) }


    override fun build() = Leaderboards(
        world = world ?: throw IllegalStateException("world is required"),
        rotation = rotation ?: throw IllegalStateException("rotation is required"),
        availableRotations = availableRotations,
        lastUpdated = lastUpdated,
        currentPage = currentPage ?: throw IllegalStateException("currentPage is required"),
        totalPages = totalPages ?: throw IllegalStateException("totalPages is required"),
        resultsCount = resultsCount ?: throw IllegalStateException("resultsCount is required"),
        entries = entries
    )

    class LeaderboardsRotationBuilder : TibiaKtBuilder<LeaderboardsRotation>() {
        var rotationId: Int? = null
        var current: Boolean = false
        var endDate: Instant? = null
        override fun build() = LeaderboardsRotation(
            rotationId = rotationId ?: throw IllegalStateException("rotationId is required"),
            current = current,
            endDate = endDate ?: throw IllegalStateException("endDate is required"),
        )

    }

}