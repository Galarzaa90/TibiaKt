@file:UseSerializers(InstantSerializer::class)

package com.galarzaa.tibiakt.core.models

import com.galarzaa.tibiakt.core.utils.InstantSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.time.Instant

@Serializable
data class AuctionDetails(
    val hitPoints: Int,
    val mana: Int,
    val capacity: Int,
    val speed: Int,
    val blessingsCount: Int,
    val outfitsCount: Int,
    val titlesCount: Int,
    val skills: AuctionSkills,
    val creationDate: Instant,
    val experience: Long,
    val gold: Long,
    val achievementPoints: Int,
    val regularWolrdTransfersAvailable: Instant,
    val charmExpansion: Boolean,
    val availableCharmPoints: Int,
    val spentCharmPoints: Int,
    val preyWildcards: Int,
    val dailyRewardStreak: Int,
    val permanentHuntingTaskSlots: Int,
    val permanentPreySlots: Int,
    val hirelings: Int,
    val hirelingJobs: Int,
    val hirelingOutfits: Int,
    val items: ItemSummary,
    val storeItems: ItemSummary,
    val mounts: Mounts,
    val storeMounts: Mounts,
    val outfits: Outfits,
    val storeOutfits: Outfits,
    val familiars: Familiars,
    val blessings: List<BlessingEntry>,
    val imbuements: List<String>,
    val charms: List<CharmEntry>,
    val completedCyclopediaMapAreas: List<String>,
    val completedQuestLines: List<String>,
    val titles: List<String>,
    val achievements: List<AchievementEntry>,
    val bestiaryProgress: List<BestiaryEntry>,
)