package com.galarzaa.tibiakt.core.builders

import com.galarzaa.tibiakt.core.enums.BattlEyeType
import com.galarzaa.tibiakt.core.enums.PvpType
import com.galarzaa.tibiakt.core.enums.TransferType
import com.galarzaa.tibiakt.core.models.world.WorldEntry
import com.galarzaa.tibiakt.core.models.world.WorldOverview
import java.time.Instant
import java.time.LocalDate

inline fun worldOverviewBuilder(block: WorldOverviewBuilder.() -> Unit) = WorldOverviewBuilder().apply(block)
inline fun worldOverview(block: WorldOverviewBuilder.() -> Unit) = worldOverviewBuilder(block).build()

@TibiaKtDsl
class WorldOverviewBuilder {
    var overallMaximumCount: Int? = null
    var overallMaximumCountDateTime: Instant? = null
    var worlds: MutableList<WorldEntry> = mutableListOf()
    var tournamentWorlds: MutableList<WorldEntry> = mutableListOf()

    fun overallMaximum(count: Int, dateTime: Instant) = apply {
        overallMaximumCount = count
        overallMaximumCountDateTime = dateTime
    }

    fun world(block: WorldEntryBuilder.() -> Unit) = worlds.add(worldEntry(block))

    fun addWorld(
        name: String,
        isOnline: Boolean,
        onlineCount: Int,
        location: String,
        pvpType: PvpType,
        battlEyeType: BattlEyeType,
        battlEyeStart: LocalDate?,
        transferType: TransferType,
        premiumRestricted: Boolean,
        experimental: Boolean,
    ) = apply {
        worlds.add(WorldEntry(name,
            isOnline,
            onlineCount,
            location,
            pvpType,
            battlEyeType,
            battlEyeStart,
            transferType,
            premiumRestricted,
            experimental))
    }

    fun build(): WorldOverview {
        return WorldOverview(overallMaximumCount = overallMaximumCount
            ?: throw IllegalStateException("overallMaximumCount is required"),
            overallMaximumCountDateTime = overallMaximumCountDateTime
                ?: throw IllegalStateException("overallMaximumCountDateTime is required"),
            worlds = worlds,
            tournamentWorlds = tournamentWorlds)
    }
}