@file:UseSerializers(LocalDateSerializer::class)

package com.galarzaa.tibiakt.core.models.world

import com.galarzaa.tibiakt.core.enums.BattlEyeType
import com.galarzaa.tibiakt.core.enums.PvpType
import com.galarzaa.tibiakt.core.enums.TransferType
import com.galarzaa.tibiakt.core.serializers.LocalDateSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.time.LocalDate

/**
 * A listed world in the [WorldOverview] section.
 *
 * @property isOnline Whether this world is currently online or not.
 * @property onlineCount The total of online players in this world.
 * @property location The geographical location of the server.
 * @property pvpType The type of PvP in this world.
 * @property battlEyeType The type of BattlEye protection present in this world.
 * @property battlEyeStartDate The date when BattlEye was implemented in this world.
 * @property transferType The type of transfer restrictions this world has.
 * @property isPremiumRestricted Whether the world can only be played by premium account characters or not.
 * @property isExperimental Whether the world is experimental or not.
 */
@Serializable
data class WorldEntry(
    override val name: String,
    val isOnline: Boolean,
    val onlineCount: Int,
    val location: String,
    val pvpType: PvpType,
    val battlEyeType: BattlEyeType,
    val battlEyeStartDate: LocalDate?,
    val transferType: TransferType,
    val isPremiumRestricted: Boolean,
    val isExperimental: Boolean,
) : BaseWorld
