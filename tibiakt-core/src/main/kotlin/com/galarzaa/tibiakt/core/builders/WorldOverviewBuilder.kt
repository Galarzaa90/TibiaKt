package com.galarzaa.tibiakt.core.builders

import com.galarzaa.tibiakt.core.enums.BattlEyeType
import com.galarzaa.tibiakt.core.enums.PvpType
import com.galarzaa.tibiakt.core.enums.TransferType
import com.galarzaa.tibiakt.core.models.world.WorldEntry
import com.galarzaa.tibiakt.core.models.world.WorldOverview
import com.galarzaa.tibiakt.core.utils.BuilderDsl
import java.time.Instant
import java.time.LocalDate

@BuilderDsl
inline fun worldOverviewBuilder(block: WorldOverviewBuilder.() -> Unit) = WorldOverviewBuilder().apply(block)

@BuilderDsl
inline fun worldOverview(block: WorldOverviewBuilder.() -> Unit) = worldOverviewBuilder(block).build()

@BuilderDsl
class WorldOverviewBuilder : TibiaKtBuilder<WorldOverview>() {
    var overallMaximumCount: Int? = null
    var overallMaximumCountDateTime: Instant? = null
    var worlds: MutableList<WorldEntry> = mutableListOf()
    var tournamentWorlds: MutableList<WorldEntry> = mutableListOf()


    fun addWorld(world: WorldEntry) = worlds.add(world)

    @BuilderDsl
    fun addWorld(block: WorldEntryBuilder.() -> Unit) = worlds.add(WorldEntryBuilder().apply(block).build())

    override fun build(): WorldOverview {
        return WorldOverview(overallMaximumCount = overallMaximumCount
            ?: throw IllegalStateException("overallMaximumCount is required"),
            overallMaximumCountDateTime = overallMaximumCountDateTime
                ?: throw IllegalStateException("overallMaximumCountDateTime is required"),
            worlds = worlds,
            tournamentWorlds = tournamentWorlds)
    }

    @BuilderDsl
    class WorldEntryBuilder : TibiaKtBuilder<WorldEntry>() {
        var name: String? = null
        var isOnline: Boolean = false
        var onlineCount: Int = 0
        var location: String? = null
        var pvpType: PvpType? = null
        var battlEyeType: BattlEyeType = BattlEyeType.UNPROTECTED
        var battlEyeStartDate: LocalDate? = null
        var transferType: TransferType = TransferType.REGULAR
        var isPremiumRestricted: Boolean = false
        var isExperimental: Boolean = false
        var onlineRecordDateTime: Instant? = null


        override fun build(): WorldEntry {
            return WorldEntry(
                name = name ?: throw IllegalStateException("name is required"),
                isOnline = isOnline,
                onlineCount = onlineCount,
                location = location ?: throw IllegalStateException("location is required"),
                pvpType = pvpType ?: throw IllegalStateException("pvpType is required"),
                battlEyeType = battlEyeType,
                battlEyeStartDate = battlEyeStartDate,
                transferType = transferType,
                isPremiumRestricted = isPremiumRestricted,
                isExperimental = isExperimental,
            )
        }
    }
}