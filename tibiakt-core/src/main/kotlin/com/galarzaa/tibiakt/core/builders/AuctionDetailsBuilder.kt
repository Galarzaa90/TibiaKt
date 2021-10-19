package com.galarzaa.tibiakt.core.builders

import com.galarzaa.tibiakt.core.models.*
import java.time.Instant

class AuctionDetailsBuilder {
    private var hitPoints: Int? = null
    private var mana: Int? = null
    private var capacity: Int? = null
    private var speed: Int? = null
    private var blessingsCount: Int? = null
    private var mountsCount: Int? = null
    private var outfitsCount: Int? = null
    private var titlesCount: Int? = null
    private var skills: AuctionSkills? = null
    private var creationDate: Instant? = null
    private var experience: Long? = null
    private var gold: Long? = null
    private var achievementPoints: Int? = null
    private var regularWorldTransfersAvailable: Instant? = null
    private var charmExpansion: Boolean? = null
    private var availableCharmPoints: Int? = null
    private var spentCharmPoints: Int? = null
    private var dailyRewardStreak: Int? = null
    private var huntingTaskPoints: Int? = null
    private var permanentHuntingTaskSlots: Int? = null
    private var permanentPreySlots: Int? = null
    private var preyWildcards: Int? = null
    private var hirelings: Int? = null
    private var hirelingJobs: Int? = null
    private var hirelingOutfits: Int? = null
    private var items: ItemSummary? = null
    private var storeItems: ItemSummary? = null
    private var mounts: Mounts? = null
    private var storeMounts: Mounts? = null
    private var outfits: Outfits? = null
    private var storeOutfits: Outfits? = null
    private var familiars: Familiars? = null
    private val blessings: MutableList<BlessingEntry> = mutableListOf()
    private val imbuements: MutableList<String> = mutableListOf()
    private val charms: MutableList<CharmEntry> = mutableListOf()
    private val completedCyclopediaMapAreas: MutableList<String> = mutableListOf()
    private val completedQuestLines: MutableList<String> = mutableListOf()
    private val titles: MutableList<String> = mutableListOf()
    private val achievements: MutableList<AchievementEntry> = mutableListOf()
    private val bestiaryProgress: MutableList<BestiaryEntry> = mutableListOf()

    fun hitPoints(hitPoints: Int) = apply { this.hitPoints = hitPoints }
    fun mana(mana: Int) = apply { this.mana = mana }
    fun capacity(capacity: Int) = apply { this.capacity = capacity }
    fun speed(speed: Int) = apply { this.speed = speed }
    fun blessingsCount(blessingsCount: Int) = apply { this.blessingsCount = blessingsCount }
    fun mountsCount(mountsCount: Int) = apply { this.mountsCount = mountsCount }
    fun outfitsCount(outfitsCount: Int) = apply { this.outfitsCount = outfitsCount }
    fun titlesCount(titlesCount: Int) = apply { this.titlesCount = titlesCount }
    fun skills(skills: AuctionSkills) = apply { this.skills = skills }
    fun creationDate(creationDate: Instant) = apply { this.creationDate = creationDate }
    fun experience(experience: Long) = apply { this.experience = experience }
    fun gold(gold: Long) = apply { this.gold = gold }
    fun achievementPoints(achievementPoints: Int) = apply { this.achievementPoints = achievementPoints }
    fun regularWorldTransfersAvailable(regularWorldTransfersAvailable: Instant) =
        apply { this.regularWorldTransfersAvailable = regularWorldTransfersAvailable }

    fun charmExpansion(charmExpansion: Boolean) = apply { this.charmExpansion = charmExpansion }
    fun availableCharmPoints(availableCharmPoints: Int) = apply { this.availableCharmPoints = availableCharmPoints }
    fun spentCharmPoints(spentCharmPoints: Int) = apply { this.spentCharmPoints = spentCharmPoints }
    fun dailyRewardStreak(dailyRewardStreak: Int) = apply { this.dailyRewardStreak = dailyRewardStreak }
    fun huntingTaskPoints(huntingTaskPoints: Int) = apply { this.huntingTaskPoints = huntingTaskPoints }
    fun permanentHuntingTaskSlots(permanentHuntingTaskSlots: Int) =
        apply { this.permanentHuntingTaskSlots = permanentHuntingTaskSlots }

    fun permanentPreySlots(permanentPreySlots: Int) = apply { this.permanentPreySlots = permanentPreySlots }
    fun preyWildcards(preyWildcards: Int) = apply { this.preyWildcards = preyWildcards }
    fun hirelings(hirelings: Int) = apply { this.hirelings = hirelings }
    fun hirelingJobs(hirelingJobs: Int) = apply { this.hirelingJobs = hirelingJobs }
    fun hirelingOutfits(hirelingOutfits: Int) = apply { this.hirelingOutfits = hirelingOutfits }
    fun items(items: ItemSummary) = apply { this.items = items }
    fun storeItems(storeItems: ItemSummary) = apply { this.storeItems = storeItems }
    fun mounts(mounts: Mounts) = apply { this.mounts = mounts }
    fun storeMounts(storeMounts: Mounts) = apply { this.storeMounts = storeMounts }
    fun outfits(outfits: Outfits) = apply { this.outfits = outfits }
    fun storeOutfits(storeOutfits: Outfits) = apply { this.storeOutfits = storeOutfits }
    fun familiars(familiars: Familiars) = apply { this.familiars = familiars }
    fun addBlessing(blessingEntry: BlessingEntry) = apply { blessings.add(blessingEntry) }
    fun addImbuement(imbuement: String) = apply { imbuements.add(imbuement) }
    fun addCharm(charm: CharmEntry) = apply { charms.add(charm) }
    fun addCompletedQuestLine(completedQuestLine: String) = apply { completedQuestLines.add(completedQuestLine) }
    fun addCompletedCyclopediaMapArea(completedCyclopediaMapArea: String) =
        apply { completedCyclopediaMapAreas.add(completedCyclopediaMapArea) }

    fun addTitle(title: String) = apply { titles.add(title) }
    fun addAchievement(achievement: AchievementEntry) = apply { achievements.add(achievement) }
    fun addBestiaryEntry(bestiaryEntry: BestiaryEntry) = apply { bestiaryProgress.add(bestiaryEntry) }

    fun build() = AuctionDetails(
        hitPoints = hitPoints ?: throw IllegalStateException("hitPoints is required"),
        mana = mana ?: throw IllegalStateException("mana is required"),
        capacity = capacity ?: throw IllegalStateException("capacity is required"),
        speed = speed ?: throw IllegalStateException("speed is required"),
        blessingsCount = blessingsCount ?: throw IllegalStateException("blessingsCount is required"),
        mountsCount = mountsCount ?: throw IllegalStateException("mountsCount is required"),
        outfitsCount = outfitsCount ?: throw IllegalStateException("outfitsCount is required"),
        titlesCount = titlesCount ?: throw IllegalStateException("titlesCount is required"),
        skills = skills ?: throw IllegalStateException("skills is required"),
        creationDate = creationDate ?: throw IllegalStateException("creationDate is required"),
        experience = experience ?: throw IllegalStateException("experience is required"),
        gold = gold ?: throw IllegalStateException("gold is required"),
        achievementPoints = achievementPoints ?: throw IllegalStateException("achievementPoints is required"),
        regularWorldTransfersAvailable = regularWorldTransfersAvailable,
        charmExpansion = charmExpansion ?: throw IllegalStateException("charmExpansion is required"),
        availableCharmPoints = availableCharmPoints ?: throw IllegalStateException("availableCharmPoints is required"),
        spentCharmPoints = spentCharmPoints ?: throw IllegalStateException("spentCharmPoints is required"),
        dailyRewardStreak = dailyRewardStreak ?: throw IllegalStateException("dailyRewardStreak is required"),
        permanentHuntingTaskSlots = permanentHuntingTaskSlots
            ?: throw IllegalStateException("permanentHuntingTaskSlots is required"),
        permanentPreySlots = permanentPreySlots ?: throw IllegalStateException("permanentPreySlots is required"),
        huntingTaskPoints = huntingTaskPoints ?: throw IllegalStateException("huntingTaskPoints is required"),
        preyWildcards = preyWildcards ?: throw IllegalStateException("preyWildcards is required"),
        hirelings = hirelings ?: throw IllegalStateException("hirelings is required"),
        hirelingJobs = hirelingJobs ?: throw IllegalStateException("hirelingJobs is required"),
        hirelingOutfits = hirelingOutfits ?: throw IllegalStateException("hirelingOutfits is required"),
        items = items ?: throw IllegalStateException("items is required"),
        storeItems = storeItems ?: throw IllegalStateException("storeItems is required"),
        mounts = mounts ?: throw IllegalStateException("mounts is required"),
        storeMounts = storeMounts ?: throw IllegalStateException("storeMounts is required"),
        outfits = outfits ?: throw IllegalStateException("outfits is required"),
        storeOutfits = storeOutfits ?: throw IllegalStateException("storeOutfits is required"),
        familiars = familiars ?: throw IllegalStateException("familiars is required"),
        blessings = blessings,
        imbuements = imbuements,
        charms = charms,
        completedCyclopediaMapAreas = completedCyclopediaMapAreas,
        completedQuestLines = completedQuestLines,
        titles = titles,
        achievements = achievements,
        bestiaryProgress = bestiaryProgress
    )
}