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

import com.galarzaa.tibiakt.core.builders.WorldOverviewBuilder
import com.galarzaa.tibiakt.core.builders.worldOverviewBuilder
import com.galarzaa.tibiakt.core.enums.BattlEyeType
import com.galarzaa.tibiakt.core.enums.StringEnum
import com.galarzaa.tibiakt.core.enums.TransferType
import com.galarzaa.tibiakt.core.exceptions.ParsingException
import com.galarzaa.tibiakt.core.models.world.WorldOverview
import com.galarzaa.tibiakt.core.utils.TABLE_SELECTOR
import com.galarzaa.tibiakt.core.utils.parseInteger
import com.galarzaa.tibiakt.core.utils.parsePopup
import com.galarzaa.tibiakt.core.time.parseTibiaDateTime
import com.galarzaa.tibiakt.core.time.parseTibiaFullDate
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

/** Parses content from the world overview section. */
public object WorldOverviewParser : Parser<WorldOverview> {
    private val recordRegex = Regex("""(?<count>[\d.,]+) players \(on (?<date>[^)]+)\)""")
    private val battlEyeRegex = Regex("""since ([^.]+).""")

    override fun fromContent(content: String): WorldOverview {
        val document: Document = Jsoup.parse(content, "", org.jsoup.parser.Parser.xmlParser())
        val boxContent =
            document.selectFirst("div.BoxContent") ?: throw ParsingException("BoxContent container not found")
        val tables = boxContent.select(TABLE_SELECTOR)
        val builder = worldOverviewBuilder {
            recordRegex.find(tables.first()?.text() ?: throw ParsingException("No content tables found."))?.apply {
                val (_, recordCount, recordDate) = groupValues
                overallMaximumCount = recordCount.parseInteger()
                overallMaximumCountDateTime = parseTibiaDateTime(recordDate)
            }
            for (i in 1..tables.lastIndex step 2) {
                val headerTable = tables[i]
                val worldsTable = tables[i + 1]
                if (headerTable.text().contains("Regular")) parseWorldsTable(worldsTable)
            }
        }
        return builder.build()
    }

    private fun WorldOverviewBuilder.parseWorldsTable(table: Element) {
        val rows = table.select("tr")
        for (row in rows.subList(1, rows.size)) {
            addWorld {
                val columns = row.select("td")
                name = columns.first()?.text() ?: throw ParsingException("No columns in world row.")
                parseOnlineStatus(columns)
                location = columns[2].text()
                pvpType = StringEnum.fromValue(columns[3].text())
                    ?: throw ParsingException("Unknown PvP type found: ${columns[3].text()}")

                parseBattlEyeStatus(columns)
                val additionalProperties = columns[5].text()
                transferType = if (additionalProperties.contains("blocked")) {
                    TransferType.BLOCKED
                } else if (additionalProperties.contains("locked")) {
                    TransferType.LOCKED
                } else {
                    TransferType.REGULAR
                }
                isPremiumRestricted = additionalProperties.contains("premium")
                isExperimental = additionalProperties.contains("experimental")
            }
        }
    }

    private fun WorldOverviewBuilder.WorldEntryBuilder.parseBattlEyeStatus(columns: Elements) {
        battlEyeType = BattlEyeType.UNPROTECTED
        columns[4].selectFirst("span.HelperDivIndicator")?.apply {
            val (_, popUp) = parsePopup(attr("onmouseover"))
            battlEyeRegex.find(popUp.text())?.apply {
                try {
                    battlEyeStartDate = parseTibiaFullDate(groups[1]!!.value)
                } catch (e: IllegalArgumentException) {
                    // Leave value as none
                }
            }
            battlEyeType = if (battlEyeStartDate == null) BattlEyeType.GREEN else BattlEyeType.YELLOW
        }
    }

    private fun WorldOverviewBuilder.WorldEntryBuilder.parseOnlineStatus(columns: Elements) {
        try {
            onlineCount = columns[1].text().parseInteger()
            isOnline = true
        } catch (nfe: NumberFormatException) {
            onlineCount = 0
            isOnline = false
        }
    }
}
