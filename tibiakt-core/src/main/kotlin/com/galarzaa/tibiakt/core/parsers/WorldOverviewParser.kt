package com.galarzaa.tibiakt.core.parsers

import com.galarzaa.tibiakt.core.builders.WorldOverviewBuilder
import com.galarzaa.tibiakt.core.enums.BattlEyeType
import com.galarzaa.tibiakt.core.enums.StringEnum
import com.galarzaa.tibiakt.core.enums.TransferType
import com.galarzaa.tibiakt.core.models.WorldOverview
import com.galarzaa.tibiakt.core.utils.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import java.time.LocalDate
import java.time.format.DateTimeParseException

object WorldOverviewParser : Parser<WorldOverview> {
    private val recordRegex = Regex("""(?<count>[\d.,]+) players \(on (?<date>[^)]+)\)""")
    private val battlEyeRegex = Regex("""since ([^.]+).""")

    override fun fromContent(content: String): WorldOverview {
        val document: Document = Jsoup.parse(content, "", org.jsoup.parser.Parser.xmlParser())
        val boxContent =
            document.selectFirst("div.BoxContent") ?: throw ParsingException("BoxContent container not found")
        val tables = boxContent.select("table.TableContent")
        val builder = WorldOverviewBuilder()
        recordRegex.find(tables.first()?.text() ?: throw ParsingException("No content tables found."))?.apply {
            val (_, recordCount, recordDate) = groupValues
            builder.overallMaximum(recordCount.parseInteger(), parseTibiaDateTime(recordDate))
        }
        for (i in 1..tables.lastIndex step 2) {
            val headerTable = tables[i]
            val worldsTable = tables[i + 1]
            if (headerTable.text().contains("Regular"))
                parseWorldsTable(builder, worldsTable)
        }
        return builder.build()
    }

    private fun parseWorldsTable(builder: WorldOverviewBuilder, table: Element) {
        val rows = table.select("tr")
        for (row in rows.subList(1, rows.size)) {
            val columns = row.select("td")
            val name = columns.first()?.text() ?: throw ParsingException("No columns in world row.")
            var isOnline: Boolean
            var onlineCount: Int
            try {
                onlineCount = columns[1].text().parseInteger()
                isOnline = true
            } catch (nfe: NumberFormatException) {
                onlineCount = 0
                isOnline = false
            }
            val location = columns[2].text()
            val pvpType = columns[3].text()

            var battlEyeType: BattlEyeType = BattlEyeType.UNPROTECTED
            var battlEyeDate: LocalDate? = null
            columns[4].selectFirst("span.HelperDivIndicator")?.apply {
                val (_, popUp) = parsePopup(attr("onmouseover"))
                battlEyeRegex.find(popUp.text())?.apply {
                    try {
                        battlEyeDate = parseTibiaFullDate(groups[1]!!.value)
                    } catch (e: DateTimeParseException) {
                        // Leave value as none
                    }
                }
                battlEyeType = if (battlEyeDate == null) BattlEyeType.GREEN else BattlEyeType.YELLOW
            }
            val additionalProperties = columns[5].text()
            val transferType: TransferType = if (additionalProperties.contains("blocked")) {
                TransferType.BLOCKED
            } else if (additionalProperties.contains("locked")) {
                TransferType.LOCKED
            } else {
                TransferType.REGULAR
            }
            val isPremiumRestricted = additionalProperties.contains("premium")
            val experimental = additionalProperties.contains("experimental")
            builder.addWorld(
                name,
                isOnline,
                onlineCount,
                location,
                StringEnum.fromValue(pvpType) ?: throw ParsingException("Unknown PvP type found: $pvpType"),
                battlEyeType,
                battlEyeDate,
                transferType,
                isPremiumRestricted,
                experimental
            )
        }
    }
}