package com.galarzaa.tibiakt.core.builders

import com.galarzaa.tibiakt.core.enums.BattlEyeType
import com.galarzaa.tibiakt.core.enums.PvpType
import com.galarzaa.tibiakt.core.enums.TransferType
import com.galarzaa.tibiakt.core.models.WorldEntry
import com.galarzaa.tibiakt.core.models.WorldOverview
import java.time.Instant
import java.time.LocalDate

class WorldOverviewBuilder {
    var overallMaximumCount: Int? = null
    var overallMaximumCountDateTime: Instant? = null
    val worlds: MutableList<WorldEntry> = mutableListOf()
    val tournamentWorlds: MutableList<WorldEntry> = mutableListOf()

    fun overallMaximum(count: Int, dateTime: Instant) = apply {
        overallMaximumCount = count
        overallMaximumCountDateTime = dateTime
    }

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
        worlds.add(
            WorldEntry(
                name,
                isOnline,
                onlineCount,
                location,
                pvpType,
                battlEyeType,
                battlEyeStart,
                transferType,
                premiumRestricted,
                experimental
            )
        )
    }

    fun build(): WorldOverview {
        return WorldOverview(
            overallMaximumCount = overallMaximumCount
                ?: throw IllegalStateException("overallMaximumCount is required"),
            overallMaximumCountDateTime = overallMaximumCountDateTime
                ?: throw IllegalStateException("overallMaximumCountDateTime is required"),
            worlds = worlds
        )
    }
}