package com.galarzaa.tibiakt.core.builders

import com.galarzaa.tibiakt.core.enums.BattlEyeType
import com.galarzaa.tibiakt.core.enums.PvpType
import com.galarzaa.tibiakt.core.enums.TransferType
import com.galarzaa.tibiakt.core.enums.Vocation
import com.galarzaa.tibiakt.core.models.world.OnlineCharacter
import com.galarzaa.tibiakt.core.models.world.World
import java.time.Instant
import java.time.LocalDate
import java.time.YearMonth

fun worldBuilder(block: WorldBuilder.() -> Unit) = WorldBuilder().apply(block)
fun world(block: WorldBuilder.() -> Unit) = WorldBuilder().apply(block).build()

class WorldBuilder {
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
    var onlineRecordCount: Int = 0
    var onlineRecordDateTime: Instant? = null
    var creationDate: YearMonth? = null
    var worldQuests: MutableList<String> = mutableListOf()
    var playersOnline: MutableList<OnlineCharacter> = mutableListOf()

    fun worldQuest(quest: String) = worldQuests.add(quest)
    fun onlinePlayer(name: String, level: Int, vocation: Vocation) =
        playersOnline.add(OnlineCharacter(name, level, vocation))

    fun build(): World {
        return World(
            name = name ?: throw IllegalStateException("name is required"),
            isOnline = isOnline,
            onlineCount = onlineCount,
            location = location ?: throw IllegalStateException("location is required"),
            pvpType = pvpType ?: throw IllegalStateException("pvpType is required"),
            battlEyeType = battlEyeType,
            battlEyeStartDate = battlEyeStartDate,
            transferType = transferType,
            isPremiumRestricted = isPremiumRestricted,
            onlineRecordCount = onlineRecordCount,
            onlineRecordDateTime = onlineRecordDateTime
                ?: throw IllegalStateException("onlineRecordDateTime is required"),
            isExperimental = isExperimental,
            creationDate = creationDate ?: throw IllegalStateException("creationDate is required"),
            playersOnline = playersOnline,
            worldQuests = worldQuests,
        )
    }
}