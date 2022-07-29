package com.galarzaa.tibiakt.core.builders

import com.galarzaa.tibiakt.core.models.BaseLeaderboardEntry
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
    var entries: MutableList<BaseLeaderboardEntry> = mutableListOf()

    @BuilderDsl
    fun rotation(body: LeaderboardsRotationBuilder.() -> Unit) =
        apply { rotation = LeaderboardsRotationBuilder().apply(body).build() }

    @BuilderDsl
    fun addAvailableRotation(rotation: LeaderboardsRotation) = apply { availableRotations.add(rotation) }

    fun addEntry(entry: BaseLeaderboardEntry) = apply { entries.add(entry) }


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