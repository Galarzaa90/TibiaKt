/*
 * Copyright © 2024 Allan Galarza
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
import com.galarzaa.tibiakt.core.models.bazaar.AuctionCreatureEntry
import com.galarzaa.tibiakt.core.models.bazaar.BestiaryEntry
import com.galarzaa.tibiakt.core.models.bazaar.BosstiaryEntry
import com.galarzaa.tibiakt.core.models.bazaar.Familiars
import com.galarzaa.tibiakt.core.models.bazaar.ItemEntry
import com.galarzaa.tibiakt.core.models.bazaar.ItemSummary
import com.galarzaa.tibiakt.core.models.bazaar.Mounts
import com.galarzaa.tibiakt.core.models.bazaar.OutfitImage
import com.galarzaa.tibiakt.core.models.bazaar.Outfits
import com.galarzaa.tibiakt.core.models.bazaar.RevealedGem
import com.galarzaa.tibiakt.core.models.bazaar.SalesArgument
import kotlin.time.Instant

@BuilderDsl
public inline fun auction(block: AuctionBuilder.() -> Unit): Auction = AuctionBuilder().apply(block).build()

@BuilderDsl
public inline fun auctionBuilder(block: AuctionBuilder.() -> Unit): AuctionBuilder = AuctionBuilder().apply(block)

/** Builder for [AuctionBuilder] instances. */
@BuilderDsl
public class AuctionBuilder : TibiaKtBuilder<Auction> {
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
        name = if (::name.isInitialized) name else error("name is required"),
        auctionId = auctionId ?: error("auctionId is required"),
        level = level ?: error("level is required"),
        world = if (::world.isInitialized) world else error("world is required"),
        vocation = if (::vocation.isInitialized) vocation else error("vocation is required"),
        sex = if (::sex.isInitialized) sex else error("sex is required"),
        outfit = if (::outfit.isInitialized) outfit else error("outfit is required"),
        displayedItems = displayedItems,
        salesArguments = salesArguments,
        startsAt = if (::auctionStart.isInitialized) auctionStart else error("auctionStart is required"),
        endsAt = if (::auctionEnd.isInitialized) auctionEnd else error("auctionEnd is required"),
        bid = bid,
        bidType = if (::bidType.isInitialized) bidType else error("bidType is required"),
        status = if (::status.isInitialized) status else error("status is required"),
        details = details,
    )

    @BuilderDsl
    public class ItemEntryBuilder : TibiaKtBuilder<ItemEntry> {
        public var itemId: Int? = null
        public lateinit var name: String
        public lateinit var description: String
        public var count: Int = 1
        public var tier: Int = 0

        override fun build(): ItemEntry = ItemEntry(
            itemId = itemId ?: error("itemId is required"),
            name = if (::name.isInitialized) name else error("name is required"),
            description = if (::description.isInitialized) description else error("description is required"),
            count = count,
            tier = tier
        )
    }

    @BuilderDsl
    public class SalesArgumentBuilder : TibiaKtBuilder<SalesArgument> {
        public var categoryId: Int? = null
        public lateinit var content: String

        override fun build(): SalesArgument = SalesArgument(
            categoryId = categoryId ?: error("categoryId is required"),
            content = if (::content.isInitialized) content else error("content is required"),
        )
    }

    @BuilderDsl
    public class AuctionDetailsBuilder : TibiaKtBuilder<AuctionDetails> {
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
        public var hasCharmExpansion: Boolean? = null
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
        public var bonusPromotionPoints: Int? = null
        public var items: ItemSummary? = null
        public var storeItems: ItemSummary? = null
        public var mounts: Mounts? = null
        public var storeMounts: Mounts? = null
        public var outfits: Outfits? = null
        public var storeOutfits: Outfits? = null
        public var familiars: Familiars? = null
        public var animusMasteriesUnlocked: Int = 0
        public val blessings: MutableList<BlessingEntry> = mutableListOf()
        public val imbuements: MutableList<String> = mutableListOf()
        public val charms: MutableList<CharmEntry> = mutableListOf()
        public val completedCyclopediaMapAreas: MutableList<String> = mutableListOf()
        public val completedQuestLines: MutableList<String> = mutableListOf()
        public val titles: MutableList<String> = mutableListOf()
        public val achievements: MutableList<AchievementEntry> = mutableListOf()
        public val bestiaryProgress: MutableList<BestiaryEntry> = mutableListOf()
        public val bosstiaryProgress: MutableList<BosstiaryEntry> = mutableListOf()
        public val revealedGems: MutableList<RevealedGem> = mutableListOf()


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

        public fun addBestiaryEntry(bestiaryEntry: BestiaryEntry): AuctionDetailsBuilder =
            apply { bestiaryProgress.add(bestiaryEntry) }

        public fun addBosstiaryEntry(bosstiaryEntry: BosstiaryEntry): AuctionDetailsBuilder =
            apply { bosstiaryProgress.add(bosstiaryEntry) }

        public fun addRevealedGem(revealedGem: RevealedGem): AuctionDetailsBuilder =
            apply { revealedGems.add(revealedGem) }

        public override fun build(): AuctionDetails =
            AuctionDetails(
                hitPoints = hitPoints ?: error("hitPoints is required"),
                mana = mana ?: error("mana is required"),
                capacity = capacity ?: error("capacity is required"),
                speed = speed ?: error("speed is required"),
                blessingsCount = blessingsCount ?: error("blessingsCount is required"),
                mountsCount = mountsCount ?: error("mountsCount is required"),
                outfitsCount = outfitsCount ?: error("outfitsCount is required"),
                titlesCount = titlesCount ?: error("titlesCount is required"),
                skills = skills ?: error("skills is required"),
                characterCreatedAt = creationDate ?: error("creationDate is required"),
                experience = experience ?: error("experience is required"),
                gold = gold ?: error("gold is required"),
                achievementPoints = achievementPoints ?: error("achievementPoints is required"),
                regularWorldTransfersUnlockAt = regularWorldTransfersAvailable,
                hasCharmExpansion = hasCharmExpansion ?: error("charmExpansion is required"),
                availableCharmPoints = availableCharmPoints
                    ?: error("availableCharmPoints is required"),
                spentCharmPoints = spentCharmPoints ?: error("spentCharmPoints is required"),
                dailyRewardStreak = dailyRewardStreak ?: error("dailyRewardStreak is required"),
                permanentHuntingTaskSlots = permanentHuntingTaskSlots
                    ?: error("permanentHuntingTaskSlots is required"),
                permanentPreySlots = permanentPreySlots
                    ?: error("permanentPreySlots is required"),
                huntingTaskPoints = huntingTaskPoints ?: error("huntingTaskPoints is required"),
                preyWildcards = preyWildcards ?: error("preyWildcards is required"),
                hirelings = hirelings ?: error("hirelings is required"),
                hirelingJobs = hirelingJobs ?: error("hirelingJobs is required"),
                hirelingOutfits = hirelingOutfits ?: error("hirelingOutfits is required"),
                exaltedDust = exaltedDust ?: error("exaltedDust is required"),
                exaltedDustLimit = exaltedDustLimit ?: error("exaltedDustLimit is required"),
                bossPoints = bossPoints ?: error("bossPoints is required"),
                bonusPromotionPoints = bonusPromotionPoints ?: error("bonusPromotionPoints is required"),
                animusMasteriesUnlocked = animusMasteriesUnlocked ?: error("animusMasteriesUnlocked is required"),
                items = items ?: error("items is required"),
                storeItems = storeItems ?: error("storeItems is required"),
                mounts = mounts ?: error("mounts is required"),
                storeMounts = storeMounts ?: error("storeMounts is required"),
                outfits = outfits ?: error("outfits is required"),
                storeOutfits = storeOutfits ?: error("storeOutfits is required"),
                familiars = familiars ?: error("familiars is required"),
                blessings = blessings,
                imbuements = imbuements,
                charms = charms,
                completedCyclopediaMapAreas = completedCyclopediaMapAreas,
                completedQuestLines = completedQuestLines,
                titles = titles,
                achievements = achievements,
                bestiaryProgress = bestiaryProgress,
                bosstiaryProgress = bosstiaryProgress,
                revealedGems = revealedGems,
            )
    }
}
