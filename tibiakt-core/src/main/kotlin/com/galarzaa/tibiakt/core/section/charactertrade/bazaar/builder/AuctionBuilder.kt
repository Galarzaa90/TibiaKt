/*
 * Copyright © 2025 Allan Galarza
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

package com.galarzaa.tibiakt.core.section.charactertrade.bazaar.builder

import com.galarzaa.tibiakt.core.builder.BuilderDsl
import com.galarzaa.tibiakt.core.builder.TibiaKtBuilder
import com.galarzaa.tibiakt.core.builder.requireField
import com.galarzaa.tibiakt.core.domain.character.Sex
import com.galarzaa.tibiakt.core.domain.character.Vocation
import com.galarzaa.tibiakt.core.section.charactertrade.bazaar.model.AchievementEntry
import com.galarzaa.tibiakt.core.section.charactertrade.bazaar.model.Auction
import com.galarzaa.tibiakt.core.section.charactertrade.bazaar.model.AuctionDetails
import com.galarzaa.tibiakt.core.section.charactertrade.bazaar.model.AuctionSkills
import com.galarzaa.tibiakt.core.section.charactertrade.bazaar.model.AuctionStatus
import com.galarzaa.tibiakt.core.section.charactertrade.bazaar.model.BestiaryEntry
import com.galarzaa.tibiakt.core.section.charactertrade.bazaar.model.BidType
import com.galarzaa.tibiakt.core.section.charactertrade.bazaar.model.BlessingEntry
import com.galarzaa.tibiakt.core.section.charactertrade.bazaar.model.BosstiaryEntry
import com.galarzaa.tibiakt.core.section.charactertrade.bazaar.model.CharmEntry
import com.galarzaa.tibiakt.core.section.charactertrade.bazaar.model.Familiars
import com.galarzaa.tibiakt.core.section.charactertrade.bazaar.model.FragmentProgressEntry
import com.galarzaa.tibiakt.core.section.charactertrade.bazaar.model.ItemEntry
import com.galarzaa.tibiakt.core.section.charactertrade.bazaar.model.ItemSummary
import com.galarzaa.tibiakt.core.section.charactertrade.bazaar.model.Mounts
import com.galarzaa.tibiakt.core.section.charactertrade.bazaar.model.OutfitImage
import com.galarzaa.tibiakt.core.section.charactertrade.bazaar.model.Outfits
import com.galarzaa.tibiakt.core.section.charactertrade.bazaar.model.RevealedGem
import com.galarzaa.tibiakt.core.section.charactertrade.bazaar.model.SalesArgument
import com.galarzaa.tibiakt.core.section.charactertrade.bazaar.model.WeaponProficiency
import kotlin.time.Instant

@BuilderDsl
internal inline fun auction(block: AuctionBuilder.() -> Unit): Auction = AuctionBuilder().apply(block).build()

@BuilderDsl
internal inline fun auctionBuilder(block: AuctionBuilder.() -> Unit): AuctionBuilder = AuctionBuilder().apply(block)

/** Builder for [AuctionBuilder] instances. */
@BuilderDsl
internal class AuctionBuilder : TibiaKtBuilder<Auction> {
    lateinit var name: String
    var auctionId: Int? = null
    var level: Int? = null
    lateinit var world: String
    lateinit var vocation: Vocation
    lateinit var sex: Sex
    lateinit var outfit: OutfitImage
    private val displayedItems: MutableList<ItemEntry> = mutableListOf()
    private val salesArguments: MutableList<SalesArgument> = mutableListOf()
    lateinit var startsAt: Instant
    lateinit var endsAt: Instant
    var bid: Int = 0
    lateinit var bidType: BidType
    lateinit var status: AuctionStatus
    var details: AuctionDetails? = null

    fun addDisplayedItem(displayedItem: ItemEntry): AuctionBuilder = apply { displayedItems.add(displayedItem) }

    @BuilderDsl
    fun displayedItem(block: ItemEntryBuilder.() -> Unit): AuctionBuilder =
        apply { displayedItems.add(ItemEntryBuilder().apply(block).build()) }

    fun addSalesArgument(salesArgument: SalesArgument): AuctionBuilder =
        apply { salesArguments.add(salesArgument) }

    @BuilderDsl
    fun salesArgument(block: SalesArgumentBuilder.() -> Unit): AuctionBuilder =
        apply { salesArguments.add(SalesArgumentBuilder().apply(block).build()) }

    @BuilderDsl
    fun details(block: AuctionDetailsBuilder.() -> Unit): AuctionBuilder =
        apply { details = AuctionDetailsBuilder().apply(block).build() }

    override fun build(): Auction = Auction(
        name = if (::name.isInitialized) name else error("name is required"),
        auctionId = auctionId ?: error("auctionId is required"),
        level = level ?: error("level is required"),
        world = if (::world.isInitialized) world else error("world is required"),
        vocation = if (::vocation.isInitialized) vocation else error("vocation is required"),
        sex = if (::sex.isInitialized) sex else error("sex is required"),
        outfit = if (::outfit.isInitialized) outfit else error("outfit is required"),
        displayedItems = displayedItems,
        salesArguments = salesArguments,
        startsAt = if (::startsAt.isInitialized) startsAt else error("auctionStart is required"),
        endsAt = if (::endsAt.isInitialized) endsAt else error("auctionEnd is required"),
        bid = bid,
        bidType = if (::bidType.isInitialized) bidType else error("bidType is required"),
        status = if (::status.isInitialized) status else error("status is required"),
        details = details,
    )

    @BuilderDsl
    class ItemEntryBuilder : TibiaKtBuilder<ItemEntry> {
        var itemId: Int? = null
        lateinit var name: String
        lateinit var description: String
        var count: Int = 1
        var tier: Int = 0

        override fun build(): ItemEntry = ItemEntry(
            itemId = itemId ?: error("itemId is required"),
            name = if (::name.isInitialized) name else error("name is required"),
            description = if (::description.isInitialized) description else error("description is required"),
            count = count,
            tier = tier
        )
    }

    @BuilderDsl
    class SalesArgumentBuilder : TibiaKtBuilder<SalesArgument> {
        var categoryId: Int? = null
        lateinit var content: String

        override fun build(): SalesArgument = SalesArgument(
            categoryId = categoryId ?: error("categoryId is required"),
            content = if (::content.isInitialized) content else error("content is required"),
        )
    }

    @BuilderDsl
    class AuctionDetailsBuilder : TibiaKtBuilder<AuctionDetails> {
        var hitPoints: Int? = null
        var mana: Int? = null
        var capacity: Int? = null
        var speed: Int? = null
        var blessingsCount: Int? = null
        var mountsCount: Int? = null
        var outfitsCount: Int? = null
        var titlesCount: Int? = null
        var skills: AuctionSkills? = null
        var characterCreatedAt: Instant? = null
        var experience: Long? = null
        var gold: Long? = null
        var achievementPoints: Int? = null
        var regularWorldTransfersUnlockAt: Instant? = null
        var hasCharmExpansion: Boolean? = null
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
        var bonusPromotionPoints: Int? = null
        var items: ItemSummary? = null
        var storeItems: ItemSummary? = null
        var mounts: Mounts? = null
        var storeMounts: Mounts? = null
        var outfits: Outfits? = null
        var storeOutfits: Outfits? = null
        var familiars: Familiars? = null
        var animusMasteriesUnlocked: Int = 0
        val blessings: MutableList<BlessingEntry> = mutableListOf()
        val imbuements: MutableList<String> = mutableListOf()
        val charms: MutableList<CharmEntry> = mutableListOf()
        val completedCyclopediaMapAreas: MutableList<String> = mutableListOf()
        val completedQuestLines: MutableList<String> = mutableListOf()
        val titles: MutableList<String> = mutableListOf()
        val achievements: MutableList<AchievementEntry> = mutableListOf()
        val bestiaryProgress: MutableList<BestiaryEntry> = mutableListOf()
        val bosstiaryProgress: MutableList<BosstiaryEntry> = mutableListOf()
        val revealedGems: MutableList<RevealedGem> = mutableListOf()
        val fragmentProgress: MutableList<FragmentProgressEntry> = mutableListOf()
        val proficiencies: MutableList<WeaponProficiency> = mutableListOf()


        fun addBlessing(blessingEntry: BlessingEntry): AuctionDetailsBuilder =
            apply { blessings.add(blessingEntry) }

        fun addImbuement(imbuement: String): AuctionDetailsBuilder = apply { imbuements.add(imbuement) }
        fun addCharm(charm: CharmEntry): AuctionDetailsBuilder = apply { charms.add(charm) }

        fun addCompletedQuestLine(completedQuestLine: String): AuctionDetailsBuilder =
            apply { completedQuestLines.add(completedQuestLine) }

        fun addCompletedCyclopediaMapArea(completedCyclopediaMapArea: String): AuctionDetailsBuilder =
            apply { completedCyclopediaMapAreas.add(completedCyclopediaMapArea) }

        fun addTitle(title: String): AuctionDetailsBuilder = apply { titles.add(title) }
        fun addAchievement(achievement: AchievementEntry): AuctionDetailsBuilder =
            apply { achievements.add(achievement) }

        fun addBestiaryEntry(bestiaryEntry: BestiaryEntry): AuctionDetailsBuilder =
            apply { bestiaryProgress.add(bestiaryEntry) }

        fun addBosstiaryEntry(bosstiaryEntry: BosstiaryEntry): AuctionDetailsBuilder =
            apply { bosstiaryProgress.add(bosstiaryEntry) }

        fun addRevealedGem(revealedGem: RevealedGem): AuctionDetailsBuilder =
            apply { revealedGems.add(revealedGem) }

        fun addFragmentProgress(revealedGem: FragmentProgressEntry): AuctionDetailsBuilder =
            apply { fragmentProgress.add(revealedGem) }

        fun addProficiency(weaponProficiency: WeaponProficiency): AuctionDetailsBuilder =
            apply { proficiencies.add(weaponProficiency) }

        override fun build(): AuctionDetails =
            AuctionDetails(
                hitPoints = requireField(hitPoints, "hitPoints"),
                mana = requireField(mana, "mana"),
                capacity = requireField(capacity, "capacity"),
                speed = requireField(speed, "speed"),
                blessingsCount = requireField(blessingsCount, "blessingsCount"),
                mountsCount = requireField(mountsCount, "mountsCount"),
                outfitsCount = requireField(outfitsCount, "outfitsCount"),
                titlesCount = requireField(titlesCount, "titlesCount"),
                skills = requireField(skills, "skills"),
                characterCreatedAt = requireField(characterCreatedAt, "characterCreatedAt"),
                experience = requireField(experience, "experience"),
                gold = requireField(gold, "gold"),
                achievementPoints = requireField(achievementPoints, "achievementPoints"),
                regularWorldTransfersUnlockAt = requireField(
                    regularWorldTransfersUnlockAt,
                    "regularWorldTransfersUnlockAt"
                ),
                hasCharmExpansion = requireField(hasCharmExpansion, "hasCharmExpansion"),
                availableCharmPoints = requireField(availableCharmPoints, "availableCharmPoints"),
                spentCharmPoints = requireField(spentCharmPoints, "spentCharmPoints"),
                dailyRewardStreak = requireField(dailyRewardStreak, "dailyRewardStreak"),
                permanentHuntingTaskSlots = requireField(permanentHuntingTaskSlots, "permanentHuntingTaskSlots"),
                permanentPreySlots = requireField(permanentPreySlots, "permanentPreySlots"),
                huntingTaskPoints = requireField(huntingTaskPoints, "huntingTaskPoints"),
                preyWildcards = requireField(preyWildcards, "preyWildcards"),
                hirelings = requireField(hirelings, "hirelings"),
                hirelingJobs = requireField(hirelingJobs, "hirelingJobs"),
                hirelingOutfits = requireField(hirelingOutfits, "hirelingOutfits"),
                exaltedDust = requireField(exaltedDust, "exaltedDust"),
                exaltedDustLimit = requireField(exaltedDustLimit, "exaltedDustLimit"),
                bossPoints = requireField(bossPoints, "bossPoints"),
                bonusPromotionPoints = requireField(bonusPromotionPoints, "bonusPromotionPoints"),
                animusMasteriesUnlocked = animusMasteriesUnlocked,
                items = requireField(items, "items"),
                storeItems = requireField(storeItems, "storeItems"),
                mounts = requireField(mounts, "mounts"),
                storeMounts = requireField(storeMounts, "storeMounts"),
                outfits = requireField(outfits, "outfits"),
                storeOutfits = requireField(storeOutfits, "storeOutfits"),
                familiars = requireField(familiars, "familiars"),
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
                fragmentProgress = fragmentProgress,
                proficiencies = proficiencies,
            )
    }
}
