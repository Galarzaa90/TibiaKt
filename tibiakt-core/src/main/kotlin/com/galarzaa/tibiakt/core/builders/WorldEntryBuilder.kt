package com.galarzaa.tibiakt.core.builders

import com.galarzaa.tibiakt.core.enums.BattlEyeType
import com.galarzaa.tibiakt.core.enums.PvpType
import com.galarzaa.tibiakt.core.enums.TransferType
import com.galarzaa.tibiakt.core.models.world.WorldEntry
import com.galarzaa.tibiakt.core.utils.BuilderDsl
import java.time.Instant
import java.time.LocalDate

inline fun worldEntryBuilder(block: WorldEntryBuilder.() -> Unit) = WorldEntryBuilder().apply(block)
inline fun worldEntry(block: WorldEntryBuilder.() -> Unit) = worldEntryBuilder(block).build()

@BuilderDsl
class WorldEntryBuilder {
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


    fun build(): WorldEntry {
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