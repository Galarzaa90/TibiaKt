package com.galarzaa.tibiakt.core.parsers

import com.galarzaa.tibiakt.core.builders.WorldOverviewBuilder
import com.galarzaa.tibiakt.core.builders.worldOverviewBuilder
import com.galarzaa.tibiakt.core.enums.BattlEyeType
import com.galarzaa.tibiakt.core.enums.StringEnum
import com.galarzaa.tibiakt.core.enums.TransferType
import com.galarzaa.tibiakt.core.exceptions.ParsingException
import com.galarzaa.tibiakt.core.models.world.WorldOverview
import com.galarzaa.tibiakt.core.utils.parseInteger
import com.galarzaa.tibiakt.core.utils.parsePopup
import com.galarzaa.tibiakt.core.utils.parseTibiaDateTime
import com.galarzaa.tibiakt.core.utils.parseTibiaFullDate
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import java.time.format.DateTimeParseException

object WorldOverviewParser : Parser<WorldOverview> {
    private val recordRegex = Regex("""(?<count>[\d.,]+) players \(on (?<date>[^)]+)\)""")
    private val battlEyeRegex = Regex("""since ([^.]+).""")

    override fun fromContent(content: String): WorldOverview {
        val document: Document = Jsoup.parse(content, "", org.jsoup.parser.Parser.xmlParser())
        val boxContent =
            document.selectFirst("div.BoxContent") ?: throw ParsingException("BoxContent container not found")
        val tables = boxContent.select("table.TableContent")
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
                try {
                    onlineCount = columns[1].text().parseInteger()
                    isOnline = true
                } catch (nfe: NumberFormatException) {
                    onlineCount = 0
                    isOnline = false
                }
                location = columns[2].text()
                pvpType = StringEnum.fromValue(columns[3].text())
                    ?: throw ParsingException("Unknown PvP type found: ${columns[3].text()}")

                battlEyeType = BattlEyeType.UNPROTECTED
                columns[4].selectFirst("span.HelperDivIndicator")?.apply {
                    val (_, popUp) = parsePopup(attr("onmouseover"))
                    battlEyeRegex.find(popUp.text())?.apply {
                        try {
                            battlEyeStartDate = parseTibiaFullDate(groups[1]!!.value)
                        } catch (e: DateTimeParseException) {
                            // Leave value as none
                        }
                    }
                    battlEyeType = if (battlEyeStartDate == null) BattlEyeType.GREEN else BattlEyeType.YELLOW
                }
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
}