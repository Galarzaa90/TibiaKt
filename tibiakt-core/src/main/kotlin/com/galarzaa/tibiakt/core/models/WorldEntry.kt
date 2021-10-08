@file:UseSerializers(LocalDateSerializer::class)

package com.galarzaa.tibiakt.core.models

import com.galarzaa.tibiakt.core.LocalDateSerializer
import com.galarzaa.tibiakt.core.enums.BattlEyeType
import com.galarzaa.tibiakt.core.enums.TransferType
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.time.LocalDate

@Serializable
data class WorldEntry(
    override val name: String,
    val isOnline: Boolean,
    val onlineCount: Int,
    val location: String,
    val pvpType: String,
    val battlEyeType: BattlEyeType,
    val battlEyeStartDate: LocalDate?,
    val transferType: TransferType,
    val isPremiumRestricted: Boolean = false,
    val isExperimental: Boolean = false
) : BaseWorld