@file:UseSerializers(InstantSerializer::class, LocalDateSerializer::class, YearMonthSerializer::class)

package com.galarzaa.tibiakt.models

import com.galarzaa.tibiakt.InstantSerializer
import com.galarzaa.tibiakt.LocalDateSerializer
import com.galarzaa.tibiakt.YearMonthSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.time.Instant
import java.time.LocalDate
import java.time.YearMonth

@Serializable
data class WorldOverview(
    val overallMaximumCount: Int,
    val overallMaximumCountDateTime: Instant,
    val worlds: List<WorldEntry> = emptyList(),
    val tournamentWorlds: List<WorldEntry> = emptyList(),
)

@Serializable
data class WorldEntry(
    val name: String,
    val isOnline: Boolean,
    val onlineCount: Int,
    val location: String,
    val pvpType: String,
    val battlEyeType: BattlEyeType,
    val battlEyeStartDate: LocalDate?,
    val transferType: TransferType,
    val isPremiumRestricted: Boolean = false,
    val isExperimental: Boolean = false
)

@Serializable
data class World(
    val name: String,
    val isOnline: Boolean,
    val onlineCount: Int,
    val location: String,
    val pvpType: String,
    val battlEyeType: BattlEyeType,
    val battlEyeStartDate: LocalDate?,
    val transferType: TransferType,
    val isPremiumRestricted: Boolean = false,
    val isExperimental: Boolean = false,
    val onlineRecordCount: Int,
    val onlineRecordDateTime: Instant,
    val creationDate: YearMonth,
    val worldQuests: List<String> = emptyList(),
    val playersOnline: List<OnlineCharacter> = emptyList()
)

@Serializable
data class OnlineCharacter(
    override val name: String,
    val level: Int,
    val vocation: String
) : BaseCharacter