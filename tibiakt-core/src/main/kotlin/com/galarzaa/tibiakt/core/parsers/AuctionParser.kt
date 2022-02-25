package com.galarzaa.tibiakt.core.parsers

import com.galarzaa.tibiakt.core.builders.AuctionBuilder
import com.galarzaa.tibiakt.core.builders.AuctionDetailsBuilder
import com.galarzaa.tibiakt.core.enums.AuctionStatus
import com.galarzaa.tibiakt.core.enums.StringEnum
import com.galarzaa.tibiakt.core.exceptions.ParsingException
import com.galarzaa.tibiakt.core.models.bazaar.AchievementEntry
import com.galarzaa.tibiakt.core.models.bazaar.Auction
import com.galarzaa.tibiakt.core.models.bazaar.AuctionSkills
import com.galarzaa.tibiakt.core.models.bazaar.BestiaryEntry
import com.galarzaa.tibiakt.core.models.bazaar.BlessingEntry
import com.galarzaa.tibiakt.core.models.bazaar.CharmEntry
import com.galarzaa.tibiakt.core.models.bazaar.FamiliarEntry
import com.galarzaa.tibiakt.core.models.bazaar.Familiars
import com.galarzaa.tibiakt.core.models.bazaar.ItemEntry
import com.galarzaa.tibiakt.core.models.bazaar.ItemSummary
import com.galarzaa.tibiakt.core.models.bazaar.MountEntry
import com.galarzaa.tibiakt.core.models.bazaar.Mounts
import com.galarzaa.tibiakt.core.models.bazaar.OutfitEntry
import com.galarzaa.tibiakt.core.models.bazaar.Outfits
import com.galarzaa.tibiakt.core.models.bazaar.SalesArgument
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

object AuctionParser : Parser<Auction?> {
    private val charInfoRegex = Regex("""Level: (\d+) \| Vocation: ([\w\s]+)\| (\w+) \| World: (\w+)""")
    private val idAddonsRegex = Regex("""/(\d+)_(\d+)""")
    private val amountRegex = Regex("""([\d,]+)x """)
    private val idRegex = Regex("""(\d+).(?:gif|png)""")

    override fun fromContent(content: String) = fromContent(content, 0, true)
    fun fromContent(content: String, auctionId: Int, parseDetails: Boolean = true): Auction? {
        val document = Jsoup.parse(content)
        val builder = AuctionBuilder()
        document.selectFirst("div.Auction")?.apply {
            parseAuctionContainer(this, builder)
        } ?: return null
        val detailsTables = document.parseTablesMap("table.Table3, table.Table5")
        if (!parseDetails)
            return builder.build()
        val detailsBuilder = AuctionDetailsBuilder()
        detailsTables["General"]?.apply { parseGeneralTable(this, detailsBuilder) }
        detailsTables["Item Summary"]?.apply { detailsBuilder.items(parseItemsTable(this)) }
        detailsTables["Store Item Summary"]?.apply { detailsBuilder.storeItems(parseItemsTable(this)) }
        detailsTables["Mounts"]?.apply { detailsBuilder.mounts(parseMountsTable(this)) }
        detailsTables["Store Mounts"]?.apply { detailsBuilder.storeMounts(parseMountsTable(this)) }
        detailsTables["Outfits"]?.apply { detailsBuilder.outfits(parseOutfitsTable(this)) }
        detailsTables["Store Outfits"]?.apply { detailsBuilder.storeOutfits(parseOutfitsTable(this)) }
        detailsTables["Familiars"]?.apply { detailsBuilder.familiars(parseFamiliarsTable(this)) }
        detailsTables["Blessings"]?.apply { parseBlessingsTable(this, detailsBuilder) }
        detailsTables["Imbuements"]?.apply { parseSingleColumnTable(this).forEach { detailsBuilder.addImbuement(it) } }
        detailsTables["Charms"]?.apply { parseCharmsTable(this, detailsBuilder) }
        detailsTables["Completed Cyclopedia Map Areas"]?.apply {
            parseSingleColumnTable(this).forEach {
                detailsBuilder.addCompletedCyclopediaMapArea(it)
            }
        }
        detailsTables["Completed Quest Lines"]?.apply {
            parseSingleColumnTable(this).forEach {
                detailsBuilder.addCompletedQuestLine(it)
            }
        }
        detailsTables["Titles"]?.run { parseSingleColumnTable(this).forEach { detailsBuilder.addTitle(it) } }
        detailsTables["Achievements"]?.run { parseAchievementsTable(this, detailsBuilder) }
        detailsTables["Bestiary Progress"]?.run { parseBestiaryTable(this, detailsBuilder) }
        return builder
            .auctionId(auctionId)
            .details(detailsBuilder.build())
            .build()
    }

    private fun parseCharmsTable(table: Element, builder: AuctionDetailsBuilder) {
        for (row in table.selectFirst("table.TableContent").rows().offsetStart(1)) {
            val colsText = row.cellsText()
            if (colsText.size != 2)
                continue
            val (cost, name) = colsText
            builder.addCharm(CharmEntry(name, cost.parseInteger()))
        }
    }

    private fun parseAchievementsTable(table: Element, builder: AuctionDetailsBuilder) {
        for (row in table.selectFirst("table.TableContent").rows().offsetStart(1)) {
            val text = row.cleanText()
            if (text.contains("more entries"))
                continue
            val secret = row.selectFirst("img") != null
            builder.addAchievement(AchievementEntry(text, secret))
        }
    }

    private fun parseBestiaryTable(table: Element, builder: AuctionDetailsBuilder) {
        for (row in table.selectFirst("table.TableContent").rows().offsetStart(1)) {
            val columnsText = row.cellsText()
            if (columnsText.size != 3)
                continue
            val (step, kills, name) = columnsText
            builder.addBestiaryEntry(BestiaryEntry(name, kills.remove("x").parseLong(), step.toInt()))
        }
    }

    private fun parseBlessingsTable(table: Element, builder: AuctionDetailsBuilder) {
        for (row in table.selectFirst("table.TableContent").rows().offsetStart(1)) {
            val colsText = row.cellsText()
            if (colsText.size != 2)
                continue
            val (amount, name) = colsText
            builder.addBlessing(BlessingEntry(name, amount.remove("x").parseInteger()))
        }
    }

    private fun parseSingleColumnTable(table: Element): List<String> {
        val innerTable = table.select("table.TableContent").last()
        val values = mutableListOf<String>()
        for (row in innerTable.rows().offsetStart(1)) {
            val text = row.selectFirst("td")?.cleanText() ?: ""
            if (text.contains("more entries"))
                continue
            values.add(text)
        }
        return values
    }

    private fun parseFamiliarsTable(table: Element): Familiars {
        val paginationData =
            table.selectFirst("div.BlockPageNavigationRow")?.parsePagination() ?: return Familiars(1, 0, 0, emptyList(), false)
        val familiarBoxes = table.select("div.CVIcon")
        val familiars = mutableListOf<FamiliarEntry>()
        for (mountBox in familiarBoxes) {
            val name = mountBox.attr("title").split("(").first().clean()
            val (_, id) = idRegex.find(mountBox.selectFirst("img")?.attr("src") ?: "")?.groupValues
                ?: throw ParsingException("familiar image did not match expected pattern")
            familiars.add(FamiliarEntry(name, id.toInt()))
        }
        return Familiars(paginationData.currentPage, paginationData.totalPages, paginationData.resultsCount, familiars, false)
    }

    private fun parseOutfitsTable(table: Element): Outfits {
        val paginationData =
            table.selectFirst("div.BlockPageNavigationRow")?.parsePagination() ?: return Outfits(1, 0, 0, emptyList(), false)
        val outfitBoxes = table.select("div.CVIcon")
        val outfits = outfitBoxes.map { parseDisplayedOutfit(it) }
        return Outfits(paginationData.currentPage, paginationData.totalPages, paginationData.resultsCount, outfits, false)
    }

    private fun parseMountsTable(mountsTable: Element): Mounts {
        val paginationData =
            mountsTable.selectFirst("div.BlockPageNavigationRow")?.parsePagination() ?: return Mounts(1, 0, 0, emptyList(), false)
        val mountsBoxes = mountsTable.select("div.CVIcon")
        val mounts = mountsBoxes.map { parseDisplayedMount(it) }
        return Mounts(paginationData.currentPage, paginationData.totalPages, paginationData.resultsCount, mounts, false)
    }

    private fun parseItemsTable(itemsTable: Element): ItemSummary {
        val paginationData =
            itemsTable.selectFirst("div.BlockPageNavigationRow")?.parsePagination() ?: return ItemSummary(1, 0, 0, emptyList(), false)
        val itemBoxes = itemsTable.select("div.CVIcon")
        val items = mutableListOf<ItemEntry>()
        for (itemBox in itemBoxes) {
            parseDisplayedItem(itemBox)?.run { items.add(this) }
        }
        return ItemSummary(paginationData.currentPage, paginationData.totalPages, paginationData.resultsCount, items, false)
    }

    private fun getAuctionTableFieldValue(row: Element): Pair<String, String> {
        val field =
            row.selectFirst("span")?.cleanText()?.remove(":") ?: throw ParsingException("could not find field's span")
        val value = row.selectFirst("div")?.cleanText() ?: throw ParsingException("could not find value's div")
        return Pair(field, value)
    }

    private fun parseGeneralTable(table: Element, builder: AuctionDetailsBuilder) {
        val contentContainers = table.select("table.TableContent")
        for (row in contentContainers[0].rows()) {
            val (field, value) = getAuctionTableFieldValue(row)
            when (field) {
                "Hit Points" -> builder.hitPoints(value.parseInteger())
                "Mana" -> builder.mana(value.parseInteger())
                "Capacity" -> builder.capacity(value.parseInteger())
                "Speed" -> builder.speed(value.parseInteger())
                "Blessings" -> builder.blessingsCount(value.split("/").first().parseInteger())
                "Mounts" -> builder.mountsCount(value.parseInteger())
                "Outfits" -> builder.outfitsCount(value.parseInteger())
                "Titles" -> builder.titlesCount(value.parseInteger())
            }
        }
        val skillsMap = mutableMapOf<String, Float>().withDefault { 0f }
        for (row in contentContainers[1].rows()) {
            val (name, level, progress) = row.cellsText()
            skillsMap[name] = level.parseInteger() + (progress.remove("%").toFloat() / 100f)
        }
        val skills = AuctionSkills(
            axeFighting = skillsMap.getValue("Axe Fighting"),
            clubFighting = skillsMap.getValue("Club Fighting"),
            swordFighting = skillsMap.getValue("Sword Fighting"),
            distanceFighting = skillsMap.getValue("Distance Fighting"),
            fishing = skillsMap.getValue("Fishing"),
            magicLevel = skillsMap.getValue("Magic Level"),
            fistFighting = skillsMap.getValue("Fist Fighting"),
            shielding = skillsMap.getValue("Shielding"),
        )
        builder.skills(skills)
        for (row in contentContainers[2].rows()) {
            val (field, value) = getAuctionTableFieldValue(row)
            when (field) {
                "Creation Date" -> builder.creationDate(parseTibiaDateTime(value))
                "Experience" -> builder.experience(value.parseLong())
                "Gold" -> builder.gold(value.parseLong())
                "Achievement Points" -> builder.achievementPoints(value.parseInteger())
            }
        }
        contentContainers[3].selectFirst("div")?.cleanText()?.run {
            if (contains("after")) {
                builder.regularWorldTransfersAvailable(parseTibiaDateTime(split("after")[1]))
            }
        }

        for (row in contentContainers[4].rows()) {
            val (field, value) = getAuctionTableFieldValue(row)
            when (field) {
                "Charm Expansion" -> builder.charmExpansion(value.contains("yes"))
                "Available Charm Points" -> builder.availableCharmPoints(value.parseInteger())
                "Spent Charm Points" -> builder.spentCharmPoints(value.parseInteger())
            }
        }
        builder.dailyRewardStreak(contentContainers[5].selectFirst("div")?.cleanText()?.toInt() ?: 0)
        for (row in contentContainers[6].rows()) {
            val (field, value) = getAuctionTableFieldValue(row)
            when (field) {
                "Hunting Task Points" -> builder.huntingTaskPoints(value.parseInteger())
                "Permanent Hunting Task Slots" -> builder.permanentHuntingTaskSlots(value.parseInteger())
                "Permanent Prey Slots" -> builder.permanentPreySlots(value.parseInteger())
                "Prey Wildcards" -> builder.preyWildcards(value.parseInteger())
            }
        }

        for (row in contentContainers[7].rows()) {
            val (field, value) = getAuctionTableFieldValue(row)
            when (field) {
                "Hirelings" -> builder.hirelings(value.parseInteger())
                "Hireling Jobs" -> builder.hirelingJobs(value.parseInteger())
                "Hireling Outfits" -> builder.hirelingOutfits(value.parseInteger())
            }
        }
    }

    internal fun parseAuctionContainer(auctionContainer: Element, builder: AuctionBuilder) {
        val headerContainer =
            auctionContainer.selectFirst("div.AuctionHeader") ?: throw ParsingException("auction header not found")
        val name = headerContainer.selectFirst("div.AuctionCharacterName")?.cleanText()
            ?: throw ParsingException("character name not found")
        headerContainer.selectFirst("div.AuctionCharacterName > a")?.apply {
            val auctionLinkInfo = getLinkInformation()!!
            val auctionId = auctionLinkInfo.queryParams["auctionid"]?.first()
                ?: throw ParsingException("auctionId not found in link")
            builder.auctionId(auctionId.toInt())
            remove()
        }

        builder.name(name)
        charInfoRegex.find(headerContainer.cleanText())?.run {
            val (_, level, vocation, sex, world) = groupValues
            builder
                .level(level.toInt())
                .vocation(StringEnum.fromValue(vocation.trim())
                    ?: throw ParsingException("Unknow vocation found: $vocation"))
                .sex(sex.trim().lowercase()
                    .let { StringEnum.fromValue(it) ?: throw ParsingException("Unknown sex found: $it") })
                .world(world.trim())
        }
        val outfitImageUrl = auctionContainer.selectFirst("img.AuctionOutfitImage")?.attr("src")
            ?: throw ParsingException("outfit image not found")
        val (_, outfitId, addons) = idAddonsRegex.find(outfitImageUrl)?.groupValues
            ?: throw ParsingException("image URL does not match expected pattern")

        auctionContainer.select(".CVIcon").forEach {
            parseDisplayedItem(it)?.run { builder.addDisplayedItem(this) }
        }
        builder.outfit(outfitId.toInt(), addons.toInt())

        auctionContainer.select("div.Entry").forEach {
            val img = it.select("img")
            val imgUrl = img.attr("src")
            val (_, id) = idRegex.find(imgUrl)?.groupValues
                ?: throw ParsingException("sales argument image does not match pattern.")
            builder.addSalesArgument(
                SalesArgument(id.toInt(), it.cleanText())
            )
        }

        val (startDate, endDate, _) = auctionContainer.select("div.ShortAuctionDataValue").map { it.cleanText() }
        builder.auctionStart(parseTibiaDateTime(startDate)).auctionEnd(parseTibiaDateTime(endDate))
        auctionContainer.selectFirst("div.ShortAuctionDataBidRow")?.run {
            val bidTag = selectFirst("div.ShortAuctionDataValue") ?: throw ParsingException("Could not find bid")
            val bidTypeTag =
                selectFirst("div.ShortAuctionDataLabel") ?: throw ParsingException("could not find bid type")
            val bidTypeStr = bidTypeTag.cleanText().remove(":")
            builder
                .bid(bidTag.text().parseInteger())
                .bidType(StringEnum.fromValue(bidTypeStr)
                    ?: throw ParsingException("unknown bid type: $bidTypeStr"))
        }

        val status = auctionContainer.selectFirst("div.AuctionInfo")?.cleanText() ?: ""
        builder.status(StringEnum.fromValue<AuctionStatus>(status) ?: AuctionStatus.IN_PROGRESS)
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
        return ItemEntry(itemId, name, description, amount)
    }

    @PublishedApi
    internal fun parseDisplayedOutfit(container: Element): OutfitEntry {
        val name = container.attr("title").split("(").first().clean()
        val (_, id, addons) = idAddonsRegex.find(container.selectFirst("img")?.attr("src") ?: "")?.groupValues
            ?: throw ParsingException("outfit image did not match expected pattern")
        return OutfitEntry(name, id.toInt(), addons.toInt())
    }

    @PublishedApi
    internal fun parseDisplayedMount(container: Element): MountEntry {
        val name = container.attr("title")
        val (_, id) = idRegex.find(container.selectFirst("img")?.attr("src") ?: "")?.groupValues
            ?: throw ParsingException("mount image did not match expected pattern")
        return MountEntry(name, id.toInt())
    }

    inline fun <reified T> parsePageItems(content: String): List<T> {
        val document = Jsoup.parse(content)
        val cvIcon = document.select("div.CVIcon")
        return when (T::class) {
            ItemEntry::class -> cvIcon.mapNotNull { parseDisplayedItem(it) as T }
            OutfitEntry::class -> cvIcon.mapNotNull { parseDisplayedOutfit(it) as T }
            MountEntry::class -> cvIcon.mapNotNull { parseDisplayedMount(it) as T }
            else -> emptyList()
        }
    }
}