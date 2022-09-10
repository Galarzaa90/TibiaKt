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
import kotlinx.datetime.Instant

@BuilderDsl
public inline fun auction(block: AuctionBuilder.() -> Unit): Auction = AuctionBuilder().apply(block).build()

@BuilderDsl
public inline fun auctionBuilder(block: AuctionBuilder.() -> Unit): AuctionBuilder = AuctionBuilder().apply(block)

@BuilderDsl
public class AuctionBuilder : TibiaKtBuilder<Auction>() {
    public lateinit var name: String
    public var auctionId: Int? = null
    public var level: Int? = null
    public lateinit var world: String
    public lateinit var vocation: Vocation
    public lateinit var sex: Sex
    public lateinit var outfit: OutfitImage
    private val displayedItems: MutableList<ItemEntry> = mutableListOf()
    private val salesArguments: MutableList<SalesArgument> = mutableListOf()
    public lateinit var auctionStart: Instant
    public lateinit var auctionEnd: Instant
    public var bid: Int = 0
    public lateinit var bidType: BidType
    public lateinit var status: AuctionStatus
    public var details: AuctionDetails? = null

    public fun addDisplayedItem(displayedItem: ItemEntry): AuctionBuilder = apply { displayedItems.add(displayedItem) }

    @BuilderDsl
    public fun displayedItem(block: ItemEntryBuilder.() -> Unit): AuctionBuilder =
        apply { displayedItems.add(ItemEntryBuilder().apply(block).build()) }

    public fun addSalesArgument(salesArgument: SalesArgument): AuctionBuilder =
        apply { salesArguments.add(salesArgument) }

    @BuilderDsl
    public fun salesArgument(block: SalesArgumentBuilder.() -> Unit): AuctionBuilder =
        apply { salesArguments.add(SalesArgumentBuilder().apply(block).build()) }

    @BuilderDsl
    public fun details(block: AuctionDetailsBuilder.() -> Unit): AuctionBuilder =
        apply { details = AuctionDetailsBuilder().apply(block).build() }

    public override fun build(): Auction = Auction(
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
    public class ItemEntryBuilder : TibiaKtBuilder<ItemEntry>() {
        public var itemId: Int? = null
        public lateinit var name: String
        public lateinit var description: String
        public var count: Int = 1

        override fun build(): ItemEntry = ItemEntry(
            itemId = itemId ?: throw IllegalStateException("itemId is required"),
            name = if (::name.isInitialized) name else throw IllegalStateException("name is required"),
            description = if (::description.isInitialized) description else throw IllegalStateException("description is required"),
            count = count
        )
    }

    @BuilderDsl
    public class SalesArgumentBuilder : TibiaKtBuilder<SalesArgument>() {
        public var categoryId: Int? = null
        public lateinit var content: String

        override fun build(): SalesArgument = SalesArgument(
            categoryId = categoryId ?: throw IllegalStateException("categoryId is required"),
            content = if (::content.isInitialized) content else throw IllegalStateException("content is required"),
        )
    }

    @BuilderDsl
    public class AuctionDetailsBuilder : TibiaKtBuilder<AuctionDetails>() {
        public var hitPoints: Int? = null
        public var mana: Int? = null
        public var capacity: Int? = null
        public var speed: Int? = null
        public var blessingsCount: Int? = null
        public var mountsCount: Int? = null
        public var outfitsCount: Int? = null
        public var titlesCount: Int? = null
        public var skills: AuctionSkills? = null
        public var creationDate: Instant? = null
        public var experience: Long? = null
        public var gold: Long? = null
        public var achievementPoints: Int? = null
        public var regularWorldTransfersAvailable: Instant? = null
        public var charmExpansion: Boolean? = null
        public var availableCharmPoints: Int? = null
        public var spentCharmPoints: Int? = null
        public var dailyRewardStreak: Int? = null
        public var huntingTaskPoints: Int? = null
        public var permanentHuntingTaskSlots: Int? = null
        public var permanentPreySlots: Int? = null
        public var preyWildcards: Int? = null
        public var hirelings: Int? = null
        public var hirelingJobs: Int? = null
        public var hirelingOutfits: Int? = null
        public var exaltedDust: Int? = null
        public var exaltedDustLimit: Int? = null
        public var bossPoints: Int? = null
        public var items: ItemSummary? = null
        public var storeItems: ItemSummary? = null
        public var mounts: Mounts? = null
        public var storeMounts: Mounts? = null
        public var outfits: Outfits? = null
        public var storeOutfits: Outfits? = null
        public var familiars: Familiars? = null
        public val blessings: MutableList<BlessingEntry> = mutableListOf()
        public val imbuements: MutableList<String> = mutableListOf()
        public val charms: MutableList<CharmEntry> = mutableListOf()
        public val completedCyclopediaMapAreas: MutableList<String> = mutableListOf()
        public val completedQuestLines: MutableList<String> = mutableListOf()
        public val titles: MutableList<String> = mutableListOf()
        public val achievements: MutableList<AchievementEntry> = mutableListOf()
        public val bestiaryProgress: MutableList<CreatureEntry> = mutableListOf()
        public val bosstiaryProgress: MutableList<CreatureEntry> = mutableListOf()


        public fun addBlessing(blessingEntry: BlessingEntry): AuctionDetailsBuilder =
            apply { blessings.add(blessingEntry) }

        public fun addImbuement(imbuement: String): AuctionDetailsBuilder = apply { imbuements.add(imbuement) }
        public fun addCharm(charm: CharmEntry): AuctionDetailsBuilder = apply { charms.add(charm) }

        public fun addCompletedQuestLine(completedQuestLine: String): AuctionDetailsBuilder =
            apply { completedQuestLines.add(completedQuestLine) }

        public fun addCompletedCyclopediaMapArea(completedCyclopediaMapArea: String): AuctionDetailsBuilder =
            apply { completedCyclopediaMapAreas.add(completedCyclopediaMapArea) }

        public fun addTitle(title: String): AuctionDetailsBuilder = apply { titles.add(title) }
        public fun addAchievement(achievement: AchievementEntry): AuctionDetailsBuilder =
            apply { achievements.add(achievement) }

        public fun addBestiaryEntry(bestiaryEntry: CreatureEntry): AuctionDetailsBuilder =
            apply { bestiaryProgress.add(bestiaryEntry) }

        public fun addBosstiaryEntry(bosstiaryEntry: CreatureEntry): AuctionDetailsBuilder =
            apply { bosstiaryProgress.add(bosstiaryEntry) }

        public override fun build(): AuctionDetails =
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
