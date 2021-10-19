package com.galarzaa.tibiakt.core.parsers

import com.galarzaa.tibiakt.core.builders.AuctionBuilder
import com.galarzaa.tibiakt.core.builders.BazaarFiltersBuilder
import com.galarzaa.tibiakt.core.builders.CharacterBazaarBuilder
import com.galarzaa.tibiakt.core.enums.IntEnum
import com.galarzaa.tibiakt.core.enums.StringEnum
import com.galarzaa.tibiakt.core.enums.Vocation
import com.galarzaa.tibiakt.core.models.CharacterBazaar
import com.galarzaa.tibiakt.core.models.DisplayItem
import com.galarzaa.tibiakt.core.utils.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import java.io.File
import java.net.URL

object CharacterBazaarParser : Parser<CharacterBazaar> {
    private val charInfoRegex = Regex("""Level: (\d+) \| Vocation: ([\w\s]+)\| (\w+) \| World: (\w+)""")
    private val idAddonsRegex = Regex("""/(\d+)_(\d+)""")
    private val amountRegex = Regex("""([\d,]+)x """)

    override fun fromContent(content: String): CharacterBazaar {
        val builder = CharacterBazaarBuilder()
        val document = Jsoup.parse(content)
        val tables = document.parseTablesMap("table.Table3")
        tables["Filter Auctions"]?.apply { parseAuctionFilters(this, builder) }
        tables["Current Auctions"]?.apply {
            val auctions = this.select("div.Auction")
            auctions.forEach { parseAuctionContainer(it, builder) }
        }
        return builder.build()
    }

    private fun parseAuctionFilters(filtersTable: Element, builder: CharacterBazaarBuilder) {
        val (searchForm, secondaryForm) = filtersTable.select("form")
        val searchData = searchForm.formData()
        val additionalData = secondaryForm.formData()
        val filterBuilder = BazaarFiltersBuilder()
        filterBuilder
            .world(searchData.data["filter_world"])
            .pvpType(IntEnum.fromValue(searchData.data["filter_worldpvptype"]))
            .battlEyeType(IntEnum.fromValue(searchData.data["filter_battleyestate"]))
            .vocation(IntEnum.fromValue(searchData.data["filter_profession"]))
            .skill(IntEnum.fromValue(searchData.data["filter_skillid"]))
            .orderBy(IntEnum.fromValue(searchData.data["order_column"]))
            .order(IntEnum.fromValue(searchData.data["order_direction"]))
            .minimumLevel(searchData.data["filter_levelrangefrom"]?.nullIfBlank()?.parseInteger())
            .maximumLevel(searchData.data["filter_levelrangeto"]?.nullIfBlank()?.parseInteger())
            .minimumSkillLevel(searchData.data["filter_skillrangefrom"]?.nullIfBlank()?.parseInteger())
            .maximumSkillLevel(searchData.data["filter_skillrangeto"]?.nullIfBlank()?.parseInteger())
            .searchString(additionalData.data["searchstring"]?.nullIfBlank())
            .searchType(IntEnum.fromValue(additionalData.data["searchstring"]))
        builder.filters(filterBuilder.build())
    }

    private fun parseAuctionContainer(auctionContainer: Element, builder: CharacterBazaarBuilder) {
        val auctionBuilder = AuctionBuilder()
        val headerContainer =
            auctionContainer.selectFirst("div.AuctionHeader") ?: throw ParsingException("auction header not found")
        val auctionLink = headerContainer.selectFirst("div.AuctionCharacterName > a")!!
        val auctionLinkInfo = auctionLink.getLinkInformation()
        val name = auctionLinkInfo!!.title.clean()
        val auctionId = auctionLinkInfo.queryParams["auctionid"]?.first()!!
        auctionBuilder.name(name).auctionId(auctionId.toInt())
        auctionLink.remove()
        charInfoRegex.find(headerContainer.cleanText())?.run {
            val (_, level, vocation, sex, world) = groupValues
            auctionBuilder
                .level(level.toInt())
                .vocation(Vocation.fromProperName(vocation.trim())!!)
                .sex(sex.trim())
                .world(world.trim())
        }
        val outfitImage = auctionContainer.selectFirst("img.AuctionOutfitImage")
        val outfitImageUrl = outfitImage!!.attr("src")
        val (_, outfitId, addons) = idAddonsRegex.find(outfitImageUrl)!!.groupValues

        auctionContainer.select(".CVIcon").map { parseDisplayedItem(it, auctionBuilder) }
        auctionBuilder.outfit(outfitId.toInt(), addons.toInt())

        val (startDate, endDate, _) = auctionContainer.select("div.ShortAuctionDataValue").map { it.cleanText() }
        auctionBuilder.auctionStart(parseTibiaDateTime(startDate)).auctionEnd(parseTibiaDateTime(endDate))
        auctionContainer.selectFirst("div.ShortAuctionDataBidRow")?.run {
            val bidTag = selectFirst("div.ShortAuctionDataValue") ?: throw ParsingException("Could not find bid")
            val bidTypeTag =
                selectFirst("div.ShortAuctionDataLabel") ?: throw ParsingException("could not find bid type")
            val bidTypeStr = bidTypeTag.cleanText().remove(":")
            auctionBuilder
                .bid(bidTag.text().parseInteger())
                .bidType(StringEnum.fromValue(bidTypeStr) ?: throw ParsingException("unknown bid type: $bidTypeStr"))
        }
        builder.addEntry(auctionBuilder.build())
    }

    fun parseDisplayedItem(displayItemContainer: Element, builder: AuctionBuilder) {
        val title = displayItemContainer.attr("title")
        val fileUrl = displayItemContainer.selectFirst("img")?.attr("src") ?: return
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
        builder.addDisplayedItem(DisplayItem(itemId, name, description, amount))
    }
}