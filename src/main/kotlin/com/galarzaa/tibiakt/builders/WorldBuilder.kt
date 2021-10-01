package com.galarzaa.tibiakt.builders

import com.galarzaa.tibiakt.enums.BattlEyeType
import com.galarzaa.tibiakt.enums.TransferType
import com.galarzaa.tibiakt.enums.Vocation
import com.galarzaa.tibiakt.models.OnlineCharacter
import com.galarzaa.tibiakt.models.World
import java.time.Instant
import java.time.LocalDate
import java.time.YearMonth

class WorldBuilder {
    private var name: String? = null
    private var isOnline: Boolean = false
    private var onlineCount: Int = 0
    private var location: String? = null
    private var pvpType: String? = null
    private var battlEyeType: BattlEyeType = BattlEyeType.UNPROTECTED
    private var battlEyeStartDate: LocalDate? = null
    private var transferType: TransferType = TransferType.REGULAR
    private var isPremiumRestricted: Boolean = false
    private var isExperimental: Boolean = false
    private var onlineRecordCount: Int = 0
    private var onlineRecordDateTime: Instant? = null
    private var creationDate: YearMonth? = null
    private val worldQuests: MutableList<String> = mutableListOf()
    private val playersOnline: MutableList<OnlineCharacter> = mutableListOf()

    fun name(name: String) = apply { this.name = name }
    fun isOnline(isOnline: Boolean) = apply { this.isOnline = isOnline }
    fun onlineCount(onlineCount: Int) = apply { this.onlineCount = onlineCount }
    fun onlineRecord(count: Int, dateTime: Instant) =
        apply { onlineRecordCount = count; onlineRecordDateTime = dateTime }

    fun creationDate(creationDate: YearMonth) = apply { this.creationDate = creationDate }
    fun location(location: String) = apply { this.location = location }
    fun pvpType(pvpType: String) = apply { this.pvpType = pvpType }
    fun addWorldQuest(worldQuest: String) = apply { worldQuests.add(worldQuest) }
    fun battlEyeType(battlEyeType: BattlEyeType) = apply { this.battlEyeType = battlEyeType }
    fun battlEyeStartDate(battlEyeStartDate: LocalDate?) = apply { this.battlEyeStartDate = battlEyeStartDate }
    fun transferType(transferType: TransferType) = apply { this.transferType = transferType }
    fun addOnlinePlayer(name: String, level: Int, vocation: Vocation) =
        apply { playersOnline.add(OnlineCharacter(name, level, vocation)) }

    fun experimental(experimental: Boolean) = apply { this.isExperimental = experimental }
    fun premiumRestricted(premiumRestricted: Boolean) = apply { isPremiumRestricted = premiumRestricted }

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