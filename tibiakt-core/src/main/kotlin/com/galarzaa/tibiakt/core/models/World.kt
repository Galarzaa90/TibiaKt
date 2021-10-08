@file:UseSerializers(InstantSerializer::class, LocalDateSerializer::class, YearMonthSerializer::class)

package com.galarzaa.tibiakt.core.models

import com.galarzaa.tibiakt.core.InstantSerializer
import com.galarzaa.tibiakt.core.LocalDateSerializer
import com.galarzaa.tibiakt.core.YearMonthSerializer
import com.galarzaa.tibiakt.core.enums.BattlEyeType
import com.galarzaa.tibiakt.core.enums.TransferType
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.time.Instant
import java.time.LocalDate
import java.time.YearMonth

@Serializable
data class World(
    override val name: String,
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
) : BaseWorld

