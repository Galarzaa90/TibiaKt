package com.galarzaa.tibiakt.core.builders

import com.galarzaa.tibiakt.core.enums.AuctionStatus
import com.galarzaa.tibiakt.core.enums.BidType
import com.galarzaa.tibiakt.core.enums.Sex
import com.galarzaa.tibiakt.core.enums.Vocation
import com.galarzaa.tibiakt.core.models.bazaar.AchievementEntry
import com.galarzaa.tibiakt.core.models.bazaar.Auction
import com.galarzaa.tibiakt.core.models.bazaar.AuctionDetails
import com.galarzaa.tibiakt.core.models.bazaar.AuctionSkills
import com.galarzaa.tibiakt.core.models.bazaar.BlessingEntry
import com.galarzaa.tibiakt.core.models.bazaar.CharmEntry
import com.galarzaa.tibiakt.core.models.bazaar.CreatureEntry
import com.galarzaa.tibiakt.core.models.bazaar.Familiars
import com.galarzaa.tibiakt.core.models.bazaar.ItemEntry
import com.galarzaa.tibiakt.core.models.bazaar.ItemSummary
import com.galarzaa.tibiakt.core.models.bazaar.Mounts
import com.galarzaa.tibiakt.core.models.bazaar.OutfitImage
import com.galarzaa.tibiakt.core.models.bazaar.Outfits
import com.galarzaa.tibiakt.core.models.bazaar.SalesArgument
import com.galarzaa.tibiakt.core.utils.BuilderDsl
import java.time.Instant

@BuilderDsl
inline fun auction(block: AuctionBuilder.() -> Unit) = AuctionBuilder().apply(block).build()

@BuilderDsl
inline fun auctionBuilder(block: AuctionBuilder.() -> Unit) = AuctionBuilder().apply(block)

@BuilderDsl
class AuctionBuilder : TibiaKtBuilder<Auction>() {
    lateinit var name: String
    var auctionId: Int? = null
    var level: Int? = null
    lateinit var world: String
    lateinit var vocation: Vocation
    lateinit var sex: Sex
    lateinit var outfit: OutfitImage
    val displayedItems: MutableList<ItemEntry> = mutableListOf()
    val salesArguments: MutableList<SalesArgument> = mutableListOf()
    lateinit var auctionStart: Instant
    lateinit var auctionEnd: Instant
    var bid: Int = 0
    lateinit var bidType: BidType
    lateinit var status: AuctionStatus
    var details: AuctionDetails? = null

    fun addDisplayedItem(displayedItem: ItemEntry) = apply { displayedItems.add(displayedItem) }

    @BuilderDsl
    fun displayedItem(block: ItemEntryBuilder.() -> Unit) =
        apply { displayedItems.add(ItemEntryBuilder().apply(block).build()) }

    fun addSalesArgument(salesArgument: SalesArgument) = apply { salesArguments.add(salesArgument) }

    @BuilderDsl
    fun salesArgument(block: SalesArgumentBuilder.() -> Unit) =
        apply { salesArguments.add(SalesArgumentBuilder().apply(block).build()) }

    @BuilderDsl
    fun details(block: AuctionDetailsBuilder.() -> Unit) =
        apply { details = AuctionDetailsBuilder().apply(block).build() }

    override fun build() = Auction(
        name = if (::name.isInitialized) name else throw IllegalStateException("name is required"),
        auctionId = auctionId ?: throw IllegalStateException("auctionId is required"),
        level = level ?: throw IllegalStateException("level is required"),
        world = if (::world.isInitialized) world else throw IllegalStateException("world is required"),
        vocation = if (::vocation.isInitialized) vocation else throw IllegalStateException("vocation is required"),
        sex = if (::sex.isInitialized) sex else throw IllegalStateException("sex is required"),
        outfit = if (::outfit.isInitialized) outfit else throw IllegalStateException("outfit is required"),
        displayedItems = displayedItems,
        salesArguments = salesArguments,
        auctionStart = if (::auctionStart.isInitialized) auctionStart else throw IllegalStateException("auctionStart is required"),
        auctionEnd = if (::auctionEnd.isInitialized) auctionEnd else throw IllegalStateException("auctionEnd is required"),
        bid = bid,
        bidType = if (::bidType.isInitialized) bidType else throw IllegalStateException("bidType is required"),
        status = if (::status.isInitialized) status else throw IllegalStateException("status is required"),
        details = details,
    )

    @BuilderDsl
    class ItemEntryBuilder : TibiaKtBuilder<ItemEntry>() {
        var itemId: Int? = null
        lateinit var name: String
        lateinit var description: String
        var count: Int = 1

        override fun build() = ItemEntry(itemId = itemId ?: throw IllegalStateException("itemId is required"),
            name = if (::name.isInitialized) name else throw IllegalStateException("name is required"),
            description = if (::description.isInitialized) description else throw IllegalStateException("description is required"),
            count = count)
    }

    @BuilderDsl
    class SalesArgumentBuilder : TibiaKtBuilder<SalesArgument>() {
        var categoryId: Int? = null
        lateinit var content: String

        override fun build() = SalesArgument(
            categoryId = categoryId ?: throw IllegalStateException("categoryId is required"),
            content = if (::content.isInitialized) content else throw IllegalStateException("content is required"),
        )
    }

    @BuilderDsl
    class AuctionDetailsBuilder : TibiaKtBuilder<AuctionDetails>() {
        var hitPoints: Int? = null
        var mana: Int? = null
        var capacity: Int? = null
        var speed: Int? = null
        var blessingsCount: Int? = null
        var mountsCount: Int? = null
        var outfitsCount: Int? = null
        var titlesCount: Int? = null
        var skills: AuctionSkills? = null
        var creationDate: Instant? = null
        var experience: Long? = null
        var gold: Long? = null
        var achievementPoints: Int? = null
        var regularWorldTransfersAvailable: Instant? = null
        var charmExpansion: Boolean? = null
        var availableCharmPoints: Int? = null
        var spentCharmPoints: Int? = null
        var dailyRewardStreak: Int? = null
        var huntingTaskPoints: Int? = null
        var permanentHuntingTaskSlots: Int? = null
        var permanentPreySlots: Int? = null
        var preyWildcards: Int? = null
        var hirelings: Int? = null
        var hirelingJobs: Int? = null
        var hirelingOutfits: Int? = null
        var exaltedDust: Int? = null
        var exaltedDustLimit: Int? = null
        var bossPoints: Int? = null
        var items: ItemSummary? = null
        var storeItems: ItemSummary? = null
        var mounts: Mounts? = null
        var storeMounts: Mounts? = null
        var outfits: Outfits? = null
        var storeOutfits: Outfits? = null
        var familiars: Familiars? = null
        val blessings: MutableList<BlessingEntry> = mutableListOf()
        val imbuements: MutableList<String> = mutableListOf()
        val charms: MutableList<CharmEntry> = mutableListOf()
        val completedCyclopediaMapAreas: MutableList<String> = mutableListOf()
        val completedQuestLines: MutableList<String> = mutableListOf()
        val titles: MutableList<String> = mutableListOf()
        val achievements: MutableList<AchievementEntry> = mutableListOf()
        val bestiaryProgress: MutableList<CreatureEntry> = mutableListOf()
        val bosstiaryProgress: MutableList<CreatureEntry> = mutableListOf()


        fun addBlessing(blessingEntry: BlessingEntry) = apply { blessings.add(blessingEntry) }
        fun addImbuement(imbuement: String) = apply { imbuements.add(imbuement) }
        fun addCharm(charm: CharmEntry) = apply { charms.add(charm) }

        fun addCompletedQuestLine(completedQuestLine: String) = apply { completedQuestLines.add(completedQuestLine) }
        fun addCompletedCyclopediaMapArea(completedCyclopediaMapArea: String) =
            apply { completedCyclopediaMapAreas.add(completedCyclopediaMapArea) }

        fun addTitle(title: String) = apply { titles.add(title) }
        fun addAchievement(achievement: AchievementEntry) = apply { achievements.add(achievement) }
        fun addBestiaryEntry(bestiaryEntry: CreatureEntry) = apply { bestiaryProgress.add(bestiaryEntry) }

        fun addBosstiaryEntry(bosstiaryEntry: CreatureEntry) = apply { bosstiaryProgress.add(bosstiaryEntry) }

        override fun build() =
            AuctionDetails(
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
                availableCharmPoints = availableCharmPoints
                    ?: throw IllegalStateException("availableCharmPoints is required"),
                spentCharmPoints = spentCharmPoints ?: throw IllegalStateException("spentCharmPoints is required"),
                dailyRewardStreak = dailyRewardStreak ?: throw IllegalStateException("dailyRewardStreak is required"),
                permanentHuntingTaskSlots = permanentHuntingTaskSlots
                    ?: throw IllegalStateException("permanentHuntingTaskSlots is required"),
                permanentPreySlots = permanentPreySlots
                    ?: throw IllegalStateException("permanentPreySlots is required"),
                huntingTaskPoints = huntingTaskPoints ?: throw IllegalStateException("huntingTaskPoints is required"),
                preyWildcards = preyWildcards ?: throw IllegalStateException("preyWildcards is required"),
                hirelings = hirelings ?: throw IllegalStateException("hirelings is required"),
                hirelingJobs = hirelingJobs ?: throw IllegalStateException("hirelingJobs is required"),
                hirelingOutfits = hirelingOutfits ?: throw IllegalStateException("hirelingOutfits is required"),
                exaltedDust = exaltedDust ?: throw IllegalStateException("exaltedDust is required"),
                exaltedDustLimit = exaltedDustLimit ?: throw IllegalStateException("exaltedDustLimit is required"),
                bossPoints = bossPoints ?: throw IllegalStateException("bossPoints is required"),
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
                bestiaryProgress = bestiaryProgress,
                bosstiaryProgress = bosstiaryProgress,
            )
    }
}