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

package com.galarzaa.tibiakt.core.parsers

import com.galarzaa.tibiakt.core.builders.AuctionBuilder
import com.galarzaa.tibiakt.core.builders.auction
import com.galarzaa.tibiakt.core.enums.AuctionStatus
import com.galarzaa.tibiakt.core.enums.StringEnum
import com.galarzaa.tibiakt.core.exceptions.ParsingException
import com.galarzaa.tibiakt.core.models.bazaar.AchievementEntry
import com.galarzaa.tibiakt.core.models.bazaar.Auction
import com.galarzaa.tibiakt.core.models.bazaar.AuctionSkills
import com.galarzaa.tibiakt.core.models.bazaar.BlessingEntry
import com.galarzaa.tibiakt.core.models.bazaar.CharmEntry
import com.galarzaa.tibiakt.core.models.bazaar.CreatureEntry
import com.galarzaa.tibiakt.core.models.bazaar.FamiliarEntry
import com.galarzaa.tibiakt.core.models.bazaar.Familiars
import com.galarzaa.tibiakt.core.models.bazaar.ItemEntry
import com.galarzaa.tibiakt.core.models.bazaar.ItemSummary
import com.galarzaa.tibiakt.core.models.bazaar.MountEntry
import com.galarzaa.tibiakt.core.models.bazaar.Mounts
import com.galarzaa.tibiakt.core.models.bazaar.OutfitEntry
import com.galarzaa.tibiakt.core.models.bazaar.OutfitImage
import com.galarzaa.tibiakt.core.models.bazaar.Outfits
import com.galarzaa.tibiakt.core.models.bazaar.RevealedGem
import com.galarzaa.tibiakt.core.utils.TABLE_SELECTOR
import com.galarzaa.tibiakt.core.utils.cellsText
import com.galarzaa.tibiakt.core.utils.clean
import com.galarzaa.tibiakt.core.utils.cleanText
import com.galarzaa.tibiakt.core.utils.getLinkInformation
import com.galarzaa.tibiakt.core.utils.offsetStart
import com.galarzaa.tibiakt.core.utils.parseInteger
import com.galarzaa.tibiakt.core.utils.parseLong
import com.galarzaa.tibiakt.core.utils.parsePagination
import com.galarzaa.tibiakt.core.utils.parseTablesMap
import com.galarzaa.tibiakt.core.utils.parseTibiaDateTime
import com.galarzaa.tibiakt.core.utils.remove
import com.galarzaa.tibiakt.core.utils.rows
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import java.io.File
import java.net.URL

/** Parser for auction pages. */
public object AuctionParser : Parser<Auction?> {
    private const val PERCENTAGE = 100f

    @PublishedApi
    internal const val ICON_CSS_SELECTOR: String = "div.CVIcon"
    internal const val PAGINATOR_SELECTOR: String = "div.BlockPageNavigationRow"

    private val charInfoRegex = Regex("""Level: (\d+) \| Vocation: ([\w\s]+)\| (\w+) \| World: (\w+)""")
    private val idAddonsRegex = Regex("""/(\d+)_(\d+)""")
    private val amountRegex = Regex("""([\d,]+)x """)
    private val tierRegex = Regex("""(.*)\s\(tier (\d)\)""")
    private val idRegex = Regex("""(\d+).(?:gif|png)""")

    override fun fromContent(content: String): Auction? = fromContent(content, 0, true)
    public fun fromContent(content: String, auctionId: Int, parseDetails: Boolean = true): Auction? {
        val document = Jsoup.parse(content)
        return auction {
            this.auctionId = auctionId
            document.selectFirst("div.Auction")?.let { parseAuctionContainer(it) } ?: return null
            val detailsTables = document.parseTablesMap("table.Table3, table.Table5")
            if (!parseDetails) return@auction

            details {
                detailsTables["General"]?.apply { parseGeneralTable(this) }
                detailsTables["Item Summary"]?.apply { items = parseItemsTable(this) }
                detailsTables["Store Item Summary"]?.apply { storeItems = parseItemsTable(this) }
                detailsTables["Mounts"]?.apply { mounts = parseMountsTable(this) }
                detailsTables["Store Mounts"]?.apply { storeMounts = parseMountsTable(this) }
                detailsTables["Outfits"]?.apply { outfits = parseOutfitsTable(this) }
                detailsTables["Store Outfits"]?.apply { storeOutfits = parseOutfitsTable(this) }
                detailsTables["Familiars"]?.apply { familiars = parseFamiliarsTable(this) }
                detailsTables["Blessings"]?.apply { parseBlessingsTable(this) }
                detailsTables["Imbuements"]?.apply { parseSingleColumnTable(this).forEach { addImbuement(it) } }
                detailsTables["Charms"]?.apply { parseCharmsTable(this) }
                detailsTables["Completed Cyclopedia Map Areas"]?.apply {
                    parseSingleColumnTable(this).forEach { addCompletedCyclopediaMapArea(it) }
                }
                detailsTables["Completed Quest Lines"]?.apply {
                    parseSingleColumnTable(this).forEach { addCompletedQuestLine(it) }
                }
                detailsTables["Titles"]?.run { parseSingleColumnTable(this).forEach { addTitle(it) } }
                detailsTables["Achievements"]?.run { parseAchievementsTable(this) }
                detailsTables["Bestiary Progress"]?.run { parseBestiaryTable(this, false) }
                detailsTables["Bosstiary Progress"]?.run { parseBestiaryTable(this, true) }
                detailsTables["Revealed Gems"]?.run { parseRevealedGemsTable(this) }
            }
        }
    }

    private fun AuctionBuilder.AuctionDetailsBuilder.parseCharmsTable(table: Element) {
        for (row in table.selectFirst(TABLE_SELECTOR).rows().offsetStart(1)) {
            val colsText = row.cellsText()
            if (colsText.size != 2) continue
            val (cost, name) = colsText
            addCharm(CharmEntry(name, cost.parseInteger()))
        }
    }

    private fun AuctionBuilder.AuctionDetailsBuilder.parseAchievementsTable(table: Element) {
        for (row in table.selectFirst(TABLE_SELECTOR).rows().offsetStart(1)) {
            val text = row.cleanText()
            if (text.contains("more entries")) continue
            val isSecret = row.selectFirst("img") != null
            addAchievement(AchievementEntry(text, isSecret))
        }
    }

    private fun AuctionBuilder.AuctionDetailsBuilder.parseBestiaryTable(table: Element, bosstiary: Boolean = false) {
        for (row in table.selectFirst(TABLE_SELECTOR).rows().offsetStart(1)) {
            val columnsText = row.cellsText()
            if (columnsText.size != 3) continue
            val (step, kills, name) = columnsText
            val entry = CreatureEntry(name, kills.remove("x").parseLong(), step.toInt())
            if (bosstiary) addBosstiaryEntry(entry) else addBestiaryEntry(entry)
        }
    }

    private fun AuctionBuilder.AuctionDetailsBuilder.parseRevealedGemsTable(table: Element) {
        for (row in table.selectFirst(TABLE_SELECTOR).rows().offsetStart(1)) {
            val gemTag = row.selectFirst("div.Gem") ?: continue
            val gemTYpe = gemTag.attr("title")
            val effects = row.select("span").map { it.cleanText() }
            addRevealedGem(RevealedGem(gemTYpe, effects))
        }
    }

    private fun AuctionBuilder.AuctionDetailsBuilder.parseBlessingsTable(table: Element) {
        for (row in table.selectFirst(TABLE_SELECTOR).rows().offsetStart(1)) {
            val colsText = row.cellsText()
            if (colsText.size != 2) continue
            val (amount, name) = colsText
            addBlessing(BlessingEntry(name, amount.remove("x").parseInteger()))
        }
    }

    private fun parseSingleColumnTable(table: Element): List<String> {
        val innerTable = table.select(TABLE_SELECTOR).last()
        val values = mutableListOf<String>()
        for (row in innerTable.rows().offsetStart(1)) {
            val text = row.selectFirst("td")?.cleanText().orEmpty()
            if (text.contains("more entries")) continue
            values.add(text)
        }
        return values
    }

    private fun parseFamiliarsTable(table: Element): Familiars {
        val paginationData = table.selectFirst(PAGINATOR_SELECTOR)?.parsePagination() ?: return Familiars(
            1,
            0,
            0,
            emptyList(),
            false
        )
        val familiarBoxes = table.select(ICON_CSS_SELECTOR)
        val familiars = mutableListOf<FamiliarEntry>()
        for (mountBox in familiarBoxes) {
            val name = mountBox.attr("title").split("(").first().clean()
            val (_, id) = idRegex.find(mountBox.selectFirst("img")?.attr("src").orEmpty())?.groupValues
                ?: throw ParsingException("familiar image did not match expected pattern")
            familiars.add(FamiliarEntry(name, id.toInt()))
        }
        return Familiars(
            paginationData.currentPage,
            paginationData.totalPages,
            paginationData.resultsCount,
            familiars,
            false
        )
    }

    private fun parseOutfitsTable(table: Element): Outfits {
        val paginationData = table.selectFirst(PAGINATOR_SELECTOR)?.parsePagination() ?: return Outfits(
            1,
            0,
            0,
            emptyList(),
            false
        )
        val outfitBoxes = table.select(ICON_CSS_SELECTOR)
        val outfits = outfitBoxes.map { parseDisplayedOutfit(it) }
        return Outfits(
            paginationData.currentPage,
            paginationData.totalPages,
            paginationData.resultsCount,
            outfits,
            false
        )
    }

    private fun parseMountsTable(mountsTable: Element): Mounts {
        val paginationData =
            mountsTable.selectFirst(PAGINATOR_SELECTOR)?.parsePagination() ?: return Mounts(
                1,
                0,
                0,
                emptyList(),
                false
            )
        val mountsBoxes = mountsTable.select(ICON_CSS_SELECTOR)
        val mounts = mountsBoxes.map { parseDisplayedMount(it) }
        return Mounts(paginationData.currentPage, paginationData.totalPages, paginationData.resultsCount, mounts, false)
    }

    private fun parseItemsTable(itemsTable: Element): ItemSummary {
        val paginationData =
            itemsTable.selectFirst(PAGINATOR_SELECTOR)?.parsePagination() ?: return ItemSummary(
                1,
                0,
                0,
                emptyList(),
                false
            )
        val itemBoxes = itemsTable.select(ICON_CSS_SELECTOR)
        val items = mutableListOf<ItemEntry>()
        for (itemBox in itemBoxes) {
            parseDisplayedItem(itemBox)?.run { items.add(this) }
        }
        return ItemSummary(
            paginationData.currentPage,
            paginationData.totalPages,
            paginationData.resultsCount,
            items,
            false
        )
    }

    private fun getAuctionTableFieldValue(row: Element): Pair<String, String> {
        val field =
            row.selectFirst("span")?.cleanText()?.remove(":") ?: throw ParsingException("could not find field's span")
        val value = row.selectFirst("div")?.cleanText() ?: throw ParsingException("could not find value's div")
        return field to value
    }

    private fun AuctionBuilder.AuctionDetailsBuilder.parseGeneralTable(table: Element) {
        val contentContainers = table.select(TABLE_SELECTOR)
        contentContainers[0].rows().forEach { row ->
            val (field, value) = getAuctionTableFieldValue(row)
            when (field) {
                "Hit Points" -> hitPoints = value.parseInteger()
                "Mana" -> mana = value.parseInteger()
                "Capacity" -> capacity = value.parseInteger()
                "Speed" -> speed = value.parseInteger()
                "Blessings" -> blessingsCount = value.split("/").first().parseInteger()
                "Mounts" -> mountsCount = value.parseInteger()
                "Outfits" -> outfitsCount = value.parseInteger()
                "Titles" -> titlesCount = value.parseInteger()
            }
        }
        val skillsMap = mutableMapOf<String, Double>().withDefault { 0.0 }
        contentContainers[1].rows().forEach { row ->
            val (name, level, progress) = row.cellsText()
            skillsMap[name] = level.parseInteger() + (progress.remove("%").toDouble() / PERCENTAGE)
        }
        skills = AuctionSkills(
            axeFighting = skillsMap.getValue("Axe Fighting"),
            clubFighting = skillsMap.getValue("Club Fighting"),
            swordFighting = skillsMap.getValue("Sword Fighting"),
            distanceFighting = skillsMap.getValue("Distance Fighting"),
            fishing = skillsMap.getValue("Fishing"),
            magicLevel = skillsMap.getValue("Magic Level"),
            fistFighting = skillsMap.getValue("Fist Fighting"),
            shielding = skillsMap.getValue("Shielding"),
        )
        contentContainers[2].rows().forEach { row ->
            val (field, value) = getAuctionTableFieldValue(row)
            when (field) {
                "Creation Date" -> creationDate = parseTibiaDateTime(value)
                "Experience" -> experience = value.parseLong()
                "Gold" -> gold = value.parseLong()
                "Achievement Points" -> achievementPoints = value.parseInteger()
            }
        }
        contentContainers[3].selectFirst("div")?.cleanText()?.run {
            if (contains("after")) {
                regularWorldTransfersAvailable = parseTibiaDateTime(split("after")[1])
            }
        }

        contentContainers[4].rows().forEach { row ->
            val (field, value) = getAuctionTableFieldValue(row)
            when (field) {
                "Charm Expansion" -> hasCharmExpansion = value.contains("yes")
                "Available Charm Points" -> availableCharmPoints = value.parseInteger()
                "Spent Charm Points" -> spentCharmPoints = value.parseInteger()
            }
        }
        dailyRewardStreak = contentContainers[5].selectFirst("div")?.cleanText()?.parseInteger() ?: 0
        contentContainers[6].rows().forEach { row ->
            val (field, value) = getAuctionTableFieldValue(row)
            when (field) {
                "Hunting Task Points" -> huntingTaskPoints = value.parseInteger()
                "Permanent Hunting Task Slots" -> permanentHuntingTaskSlots = value.parseInteger()
                "Permanent Prey Slots" -> permanentPreySlots = value.parseInteger()
                "Prey Wildcards" -> preyWildcards = value.parseInteger()
            }
        }

        contentContainers[7].rows().forEach { row ->
            val (field, value) = getAuctionTableFieldValue(row)
            when (field) {
                "Hirelings" -> hirelings = value.parseInteger()
                "Hireling Jobs" -> hirelingJobs = value.parseInteger()
                "Hireling Outfits" -> hirelingOutfits = value.parseInteger()
            }
        }

        contentContainers.getOrNull(8)?.rows().orEmpty().forEach { row ->
            val (field, value) = getAuctionTableFieldValue(row)
            when (field) {
                "Exalted Dust" -> {
                    val (current, limit) = value.split("/").map { it.parseInteger() }
                    exaltedDust = current
                    exaltedDustLimit = limit
                }
            }
        }

        contentContainers.getOrNull(9)?.rows().orEmpty().forEach { row ->
            val (field, value) = getAuctionTableFieldValue(row)
            when (field) {
                "Boss Points" -> bossPoints = value.parseInteger()
            }
        }
        contentContainers.getOrNull(10)?.rows().orEmpty().forEach { row ->
            val (field, value) = getAuctionTableFieldValue(row)
            when (field) {
                "Bonus Promotion Points" -> bonusPromotionPoints = value.parseInteger()
            }
        }
    }

    internal fun AuctionBuilder.parseAuctionContainer(auctionContainer: Element) {
        val headerContainer =
            auctionContainer.selectFirst("div.AuctionHeader") ?: throw ParsingException("auction header not found")
        name = headerContainer.selectFirst("div.AuctionCharacterName")?.cleanText()
            ?: throw ParsingException("character name not found")
        headerContainer.selectFirst("div.AuctionCharacterName > a")?.apply {
            val auctionLinkInfo = getLinkInformation()!!
            auctionId = auctionLinkInfo.queryParams["auctionid"]?.first()?.toInt()
                ?: throw ParsingException("auctionId not found in link")
            remove()
        }

        charInfoRegex.find(headerContainer.cleanText())?.run {
            val (_, levelStr, vocationStr, sexStr, worldStr) = groupValues
            level = levelStr.toInt()
            vocation =
                StringEnum.fromValue(vocationStr.trim())
                    ?: throw ParsingException("Unknown vocation found: $vocationStr")
            sex =
                StringEnum.fromValue(sexStr.lowercase().trim()) ?: throw ParsingException("Unknown sex found: $sexStr")
            world = worldStr.trim()
        }
        val outfitImageUrl = auctionContainer.selectFirst("img.AuctionOutfitImage")?.attr("src")
            ?: throw ParsingException("outfit image not found")
        val (_, outfitId, addons) = idAddonsRegex.find(outfitImageUrl)?.groupValues
            ?: throw ParsingException("image URL does not match expected pattern")

        auctionContainer.select(".CVIcon").forEach {
            parseDisplayedItem(it)?.run { addDisplayedItem(this) }
        }

        outfit = OutfitImage(outfitId = outfitId.toInt(), addons = addons.toInt())

        auctionContainer.select("div.Entry").forEach {
            val img = it.select("img")
            val imgUrl = img.attr("src")
            val (_, id) = idRegex.find(imgUrl)?.groupValues
                ?: throw ParsingException("sales argument image does not match pattern.")
            salesArgument {
                categoryId = id.toInt()
                content = it.cleanText()
            }
        }

        val (startDate, endDate, _) = auctionContainer.select("div.ShortAuctionDataValue").map { it.cleanText() }
        auctionStart = parseTibiaDateTime(startDate)
        auctionEnd = parseTibiaDateTime(endDate)
        auctionContainer.selectFirst("div.ShortAuctionDataBidRow")?.run {
            val bidTag = selectFirst("div.ShortAuctionDataValue") ?: throw ParsingException("Could not find bid")
            val bidTypeTag =
                selectFirst("div.ShortAuctionDataLabel") ?: throw ParsingException("could not find bid type")
            val bidTypeStr = bidTypeTag.cleanText().remove(":")
            bid = bidTag.text().parseInteger()
            bidType = StringEnum.fromValue(bidTypeStr) ?: throw ParsingException("unknown bid type: $bidTypeStr")
        }

        status = auctionContainer.selectFirst("div.AuctionInfo")?.cleanText()?.let { StringEnum.fromValue(it) }
            ?: AuctionStatus.IN_PROGRESS
    }

    @PublishedApi
    internal fun parseDisplayedItem(displayItemContainer: Element): ItemEntry? {
        val title = displayItemContainer.attr("title")
        val fileUrl = displayItemContainer.selectFirst("img")?.attr("src") ?: return null
        val fileName = File(URL(fileUrl).path).name
        val itemId = fileName.remove(".gif").toInt()
        val titleLines = title.split("\n")
        var name = titleLines.first()
        val description = if (titleLines.size > 1) {
            titleLines.offsetStart(1).joinToString("\n")
        } else {
            null
        }
        var amount = 1
        amountRegex.find(name)?.apply {
            amount = groups[1]!!.value.parseInteger()
            name = amountRegex.replace(name, "")
        }
        var tier = 0
        tierRegex.find(name)?.apply {
            tier = groups[2]!!.value.parseInteger()
            name = groups[1]!!.value
        }
        return ItemEntry(itemId, name, description, amount, tier)
    }

    @PublishedApi
    internal fun parseDisplayedOutfit(container: Element): OutfitEntry {
        val name = container.attr("title").split("(").first().clean()
        val (_, id, addons) = idAddonsRegex.find(container.selectFirst("img")?.attr("src").orEmpty())?.groupValues
            ?: throw ParsingException("outfit image did not match expected pattern")
        return OutfitEntry(name, id.toInt(), addons.toInt())
    }

    @PublishedApi
    internal fun parseDisplayedMount(container: Element): MountEntry {
        val name = container.attr("title")
        val (_, id) = idRegex.find(container.selectFirst("img")?.attr("src").orEmpty())?.groupValues
            ?: throw ParsingException("mount image did not match expected pattern")
        return MountEntry(name, id.toInt())
    }

    public inline fun <reified T> parsePageItems(content: String): List<T> {
        val document = Jsoup.parse(content)
        val cvIcon = document.select(ICON_CSS_SELECTOR)
        return when (T::class) {
            ItemEntry::class -> cvIcon.mapNotNull { parseDisplayedItem(it) as T }
            OutfitEntry::class -> cvIcon.mapNotNull { parseDisplayedOutfit(it) as T }
            MountEntry::class -> cvIcon.mapNotNull { parseDisplayedMount(it) as T }
            else -> emptyList()
        }
    }
}
