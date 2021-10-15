package com.galarzaa.tibiakt.core.parsers

import com.galarzaa.tibiakt.core.builders.HouseBuilder
import com.galarzaa.tibiakt.core.enums.HouseStatus
import com.galarzaa.tibiakt.core.enums.StringEnum
import com.galarzaa.tibiakt.core.models.House
import com.galarzaa.tibiakt.core.utils.*

object HouseParser : Parser<House?> {
    private val rentedPattern =
        Regex("""The (?<type>\w+) has been rented by (?<owner>[^.]+)\. (?<pronoun>\w+) has paid the rent until (?<paidUntil>[^.]+)\.""")
    private val auctionedPattern =
        Regex("""The (?<type>\w+) is currently being auctioned.""")
    private val bidPattern =
        Regex("""The auction will end at (?<auctionEnd>[^.]+)\. The highest bid so far is (?<highestBid>\d+) gold and has been submitted by (?<bidder>[^.]+)""")
    private val moveOutPattern = Regex("""S?[Hh]e will move out on (?<moveDate>[^.]+) \(time of daily""")
    private val transferPattern =
        Regex("""and (?<action>wants to|will) pass the house to (?<transferee>[\w\s]+) for (?<transferPrice>\d+) gold coin""")

    override fun fromContent(content: String): House? {
        val boxContent = boxContent(content)
        val infoTable = boxContent.selectFirst("table")
        val builder = HouseBuilder()
        val (imageRow, descriptionRow) = infoTable.columns()
        val imageUrl = imageRow.selectFirst("img")?.attr("src")
        val houseId = imageUrl!!.findInteger()
        val (title, sizeStr, rentStr, world) = descriptionRow.select("b").map { it.cleanText() }
        builder.name(title)
            .size(sizeStr.remove("square meters").clean().toInt())
            .rent(rentStr.remove("gold").parseThousandSuffix())
            .world(world)
            .houseId(houseId)
        val descriptionLines = descriptionRow.replaceBrs().wholeText().lines()
        builder.beds(descriptionLines[1].findInteger())
        val stateLine = descriptionLines.last().clean()
        rentedPattern.find(stateLine)?.apply {
            builder.status(HouseStatus.RENTED)
                .owner(groups["owner"]!!.value.clean())
                .paidUntil(parseTibiaDateTime(groups["paidUntil"]!!.value))
                .type(StringEnum.fromValue("${groups["type"]!!.value}s")
                    ?: throw ParsingException("Unknown house type found: ${groups["type"]!!.value}"))
        }
        auctionedPattern.find(stateLine)?.apply {
            builder.status(HouseStatus.AUCTIONED)
                .type(StringEnum.fromValue("${groups["type"]!!.value}s")
                    ?: throw ParsingException("Unknown house type found: ${groups["type"]!!.value}"))
        }
        bidPattern.find(stateLine)?.apply {
            builder.status(HouseStatus.AUCTIONED)
                .auctionEnd(parseTibiaDateTime(groups["auctionEnd"]!!.value))
                .highestBid(groups["highestBid"]!!.value.parseInteger())
                .highestBidder(groups["bidder"]!!.value.clean())
        }
        moveOutPattern.find(stateLine)?.apply {
            builder.transferDate(parseTibiaDateTime(groups["moveDate"]!!.value))
        }
        transferPattern.find(stateLine)?.apply {
            builder
                .transferAccepted(groups["action"]!!.value == "will")
                .transferee(groups["transferee"]!!.value.clean())
                .transferPrice(groups["transferPrice"]!!.value.toInt())
        }
        return builder.build()
    }
}