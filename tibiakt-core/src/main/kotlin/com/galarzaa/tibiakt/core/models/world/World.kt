@file:UseSerializers(InstantSerializer::class, LocalDateSerializer::class, YearMonthSerializer::class)

package com.galarzaa.tibiakt.core.models.world

import com.galarzaa.tibiakt.core.enums.BattlEyeType
import com.galarzaa.tibiakt.core.enums.PvpType
import com.galarzaa.tibiakt.core.enums.TransferType
import com.galarzaa.tibiakt.core.utils.InstantSerializer
import com.galarzaa.tibiakt.core.utils.LocalDateSerializer
import com.galarzaa.tibiakt.core.utils.YearMonthSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.time.Instant
import java.time.LocalDate
import java.time.YearMonth

/**
 * A game world.
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
 * @property onlineRecordCount The maximum online players recorded on this world.
 * @property onlineRecordDateTime The date and time when the maximum online players record was set.
 * @property creationDate The year and month when this world was created.
 * @property worldQuests The list of world quests completed.
 * @property playersOnline The list of online players in this world.
 */
@Serializable
data class World(
    override val name: String,
    val isOnline: Boolean,
    val onlineCount: Int,
    val location: String,
    val pvpType: PvpType,
    val battlEyeType: BattlEyeType,
    val battlEyeStartDate: LocalDate?,
    val transferType: TransferType,
    val isPremiumRestricted: Boolean = false,
    val isExperimental: Boolean = false,
    val onlineRecordCount: Int,
    val onlineRecordDateTime: Instant,
    val creationDate: YearMonth,
    val worldQuests: List<String> = emptyList(),
    val playersOnline: List<OnlineCharacter> = emptyList(),
) : BaseWorld {

    /** Check if a character from this world can be transferred to [target] world. */
    fun transferableTo(target: World): Boolean {
        return pvpType.weight >= target.pvpType.weight &&
                isExperimental == target.isExperimental &&
                transferType != TransferType.LOCKED &&
                target.transferType != TransferType.BLOCKED &&
                battlEyeType.weight >= target.battlEyeType.weight
    }

    /** Check if a character from the [origin] world can transfer to this world */
    fun transferableFrom(origin: World): Boolean = origin.transferableTo(this)
}