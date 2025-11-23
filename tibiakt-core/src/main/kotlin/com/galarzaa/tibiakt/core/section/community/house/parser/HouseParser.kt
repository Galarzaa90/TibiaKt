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

package com.galarzaa.tibiakt.core.section.community.house.parser

import com.galarzaa.tibiakt.core.enums.StringEnum
import com.galarzaa.tibiakt.core.exceptions.ParsingException
import com.galarzaa.tibiakt.core.html.cells
import com.galarzaa.tibiakt.core.html.cleanText
import com.galarzaa.tibiakt.core.html.replaceBrs
import com.galarzaa.tibiakt.core.parser.Parser
import com.galarzaa.tibiakt.core.section.community.house.builder.HouseBuilder
import com.galarzaa.tibiakt.core.section.community.house.builder.house
import com.galarzaa.tibiakt.core.section.community.house.model.House
import com.galarzaa.tibiakt.core.section.community.house.model.HouseStatus
import com.galarzaa.tibiakt.core.text.clean
import com.galarzaa.tibiakt.core.text.findInteger
import com.galarzaa.tibiakt.core.text.parseInteger
import com.galarzaa.tibiakt.core.text.parseThousandSuffix
import com.galarzaa.tibiakt.core.text.remove
import com.galarzaa.tibiakt.core.time.parseTibiaDateTime

/** Parser for House information pages. */
public object HouseParser : Parser<House?> {
    @Suppress("MaxLineLength")
    private val rentedPattern =
        Regex("""The (?<type>\w+) has been rented by (?<owner>[^.]+)\. (?<pronoun>\w+) has paid the rent until (?<paidUntil>[^.]+)\.""")
    private val auctionedPattern = Regex("""The (?<type>\w+) is currently being auctioned.""")

    @Suppress("MaxLineLength")
    private val bidPattern =
        Regex("""The auction will end at (?<auctionEnd>[^.]+)\. The highest bid so far is (?<highestBid>\d+) gold and has been submitted by (?<bidder>[^.]+)""")
    private val moveOutPattern = Regex("""S?[Hh]e will move out on (?<moveDate>[^.]+) \(time of daily""")

    @Suppress("MaxLineLength")
    private val transferPattern =
        Regex("""and (?<action>wants to|will) pass the house to (?<transferee>[\w\s]+) for (?<transferPrice>\d+) gold coin""")

    override fun fromContent(content: String): House? {
        val boxContent = boxContent(content)
        val infoTable = boxContent.selectFirst("table")
        return house {
            val (imageRow, descriptionRow) = infoTable.cells()
            if (imageRow.text().contains("No information about this house found")) return null
            val imageUrl = imageRow.selectFirst("img")?.attr("src")
            houseId = imageUrl?.findInteger() ?: throw ParsingException("House image not found")
            val (title, sizeStr, rentStr, worldStr) = descriptionRow.select("b").map { it.cleanText() }
            name = title
            size = sizeStr.remove("square meters").clean().toInt()
            rent = rentStr.remove("gold").parseThousandSuffix()
            world = worldStr
            val descriptionLines = descriptionRow.replaceBrs().wholeText().lines()
            beds = descriptionLines[1].findInteger()
            val statusLine = descriptionLines.last().clean()
            parseStatusDescription(statusLine)
        }
    }

    private fun HouseBuilder.parseStatusDescription(stateLine: String) {
        rentedPattern.find(stateLine)?.apply {
            status = HouseStatus.RENTED
            ownerName = groups["owner"]!!.value.clean()
            paidUntil = parseTibiaDateTime(groups["paidUntil"]!!.value)
            houseType = StringEnum.fromValue("${groups["type"]!!.value}s")
                ?: throw ParsingException("Unknown house type found: ${groups["type"]!!.value}")
        }
        auctionedPattern.find(stateLine)?.apply {
            status = HouseStatus.AUCTIONED
            houseType = StringEnum.fromValue("${groups["type"]!!.value}s")
                ?: throw ParsingException("Unknown house type found: ${groups["type"]!!.value}")
        }
        bidPattern.find(stateLine)?.apply {
            status = HouseStatus.AUCTIONED
            auctionEndsAt = parseTibiaDateTime(groups["auctionEnd"]!!.value)
            highestBid = groups["highestBid"]!!.value.parseInteger()
            highestBidder = groups["bidder"]!!.value.clean()
        }
        moveOutPattern.find(stateLine)?.apply {
            transferScheduledAt = parseTibiaDateTime(groups["moveDate"]!!.value)
        }
        transferPattern.find(stateLine)?.apply {
            isTransferAccepted = groups["action"]!!.value == "will"
            transferRecipient = groups["transferee"]!!.value.clean()
            transferPrice = groups["transferPrice"]!!.value.toInt()
        }
    }
}
