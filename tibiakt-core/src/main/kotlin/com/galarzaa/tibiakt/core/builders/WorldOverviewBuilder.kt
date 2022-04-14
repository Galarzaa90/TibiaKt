package com.galarzaa.tibiakt.core.builders

import com.galarzaa.tibiakt.core.models.world.WorldEntry
import com.galarzaa.tibiakt.core.models.world.WorldOverview
import com.galarzaa.tibiakt.core.utils.BuilderDsl
import java.time.Instant

inline fun worldOverviewBuilder(block: WorldOverviewBuilder.() -> Unit) = WorldOverviewBuilder().apply(block)
inline fun worldOverview(block: WorldOverviewBuilder.() -> Unit) = worldOverviewBuilder(block).build()

@BuilderDsl
class WorldOverviewBuilder {
    var overallMaximumCount: Int? = null
    var overallMaximumCountDateTime: Instant? = null
    var worlds: MutableList<WorldEntry> = mutableListOf()
    var tournamentWorlds: MutableList<WorldEntry> = mutableListOf()

    fun world(block: WorldEntryBuilder.() -> Unit) = worlds.add(worldEntry(block))

    fun build(): WorldOverview {
        return WorldOverview(overallMaximumCount = overallMaximumCount
            ?: throw IllegalStateException("overallMaximumCount is required"),
            overallMaximumCountDateTime = overallMaximumCountDateTime
                ?: throw IllegalStateException("overallMaximumCountDateTime is required"),
            worlds = worlds,
            tournamentWorlds = tournamentWorlds)
    }
}