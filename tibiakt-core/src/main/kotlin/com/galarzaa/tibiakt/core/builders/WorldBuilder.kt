package com.galarzaa.tibiakt.core.builders

import com.galarzaa.tibiakt.core.enums.BattlEyeType
import com.galarzaa.tibiakt.core.enums.PvpType
import com.galarzaa.tibiakt.core.enums.TransferType
import com.galarzaa.tibiakt.core.enums.Vocation
import com.galarzaa.tibiakt.core.models.world.OnlineCharacter
import com.galarzaa.tibiakt.core.models.world.World
import com.galarzaa.tibiakt.core.utils.BuilderDsl
import kotlinx.datetime.Instant
import java.time.LocalDate
import java.time.YearMonth

@BuilderDsl
public inline fun worldBuilder(block: WorldBuilder.() -> Unit): WorldBuilder = WorldBuilder().apply(block)

@BuilderDsl
public inline fun world(block: WorldBuilder.() -> Unit): World = worldBuilder(block).build()

@BuilderDsl
public class WorldBuilder : TibiaKtBuilder<World>() {
    public var name: String? = null
    public var isOnline: Boolean = false
    public var onlineCount: Int = 0
    public var location: String? = null
    public var pvpType: PvpType? = null
    public var battlEyeType: BattlEyeType = BattlEyeType.UNPROTECTED
    public var battlEyeStartDate: LocalDate? = null
    public var transferType: TransferType = TransferType.REGULAR
    public var isPremiumRestricted: Boolean = false
    public var isExperimental: Boolean = false
    public var onlineRecordCount: Int = 0
    public var onlineRecordDateTime: Instant? = null
    public var creationDate: YearMonth? = null
    public var worldQuests: MutableList<String> = mutableListOf()
    public var playersOnline: MutableList<OnlineCharacter> = mutableListOf()

    public fun worldQuest(quest: String): Boolean = worldQuests.add(quest)
    public fun addOnlinePlayer(name: String, level: Int, vocation: Vocation): Boolean =
        playersOnline.add(OnlineCharacter(name, level, vocation))


    override fun build(): World {
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
