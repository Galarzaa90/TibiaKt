package com.galarzaa.tibiakt.core.parsers

import com.galarzaa.tibiakt.core.builders.LeaderboardsBuilder
import com.galarzaa.tibiakt.core.builders.leaderboards
import com.galarzaa.tibiakt.core.exceptions.ParsingException
import com.galarzaa.tibiakt.core.models.DeletedLeaderboardsEntry
import com.galarzaa.tibiakt.core.models.Leaderboards
import com.galarzaa.tibiakt.core.models.LeaderboardsEntry
import com.galarzaa.tibiakt.core.models.LeaderboardsRotation
import com.galarzaa.tibiakt.core.utils.PaginationData
import com.galarzaa.tibiakt.core.utils.cells
import com.galarzaa.tibiakt.core.utils.clean
import com.galarzaa.tibiakt.core.utils.cleanText
import com.galarzaa.tibiakt.core.utils.formData
import com.galarzaa.tibiakt.core.utils.getLinkInformation
import com.galarzaa.tibiakt.core.utils.offsetStart
import com.galarzaa.tibiakt.core.utils.parsePagination
import com.galarzaa.tibiakt.core.utils.parseTibiaDateTime
import com.galarzaa.tibiakt.core.utils.remove
import com.galarzaa.tibiakt.core.utils.rows
import kotlinx.datetime.Clock
import org.jsoup.nodes.Element
import kotlin.time.Duration.Companion.minutes

object LeaderboardsParser : Parser<Leaderboards?> {
    val rotationEndPattern = Regex("""ends on ([^)]+)""")

    override fun fromContent(content: String): Leaderboards? {
        val boxContent = boxContent(content)
        val tables = boxContent.select("table.TableContent")

        return leaderboards {
            val formData = tables[1].selectFirst("form")?.formData() ?: throw ParsingException("form not found")
            val rotationOptions = tables[1].select("select[name=rotation] > option")
                .associate { it.attr("value").toInt() to it.cleanText() }
            for ((rotationId, label) in rotationOptions) {
                var cleanLabel = label
                var current = false
                if ("Current" in label) {
                    cleanLabel = rotationEndPattern.find(label)?.groupValues?.last()
                        ?: throw ParsingException("rotation option label doesn't match expected format")
                    current = true
                }
                val rotationEnd = parseTibiaDateTime(cleanLabel)
                val rotation = LeaderboardsRotation(
                    rotationId = rotationId,
                    current = current,
                    endDate = rotationEnd
                )
                if (current) {
                    this.rotation = rotation
                }
                addAvailableRotation(rotation)
            }
            world = formData.values["world"]
                ?: if ("world" in formData.availableOptions) return null else throw ParsingException("world form parameter not found")

            if (tables.size == 4) {
                val lastUpdateString = tables[2].text()
                val minutes =
                    Regex("""(\d+)""").find(lastUpdateString)?.groups?.get(0)?.value?.toInt() ?: throw ParsingException(
                        "unexpected last update text: $lastUpdateString"
                    )
                lastUpdated = Clock.System.now().minus(minutes.minutes)
            }

            val entriesTable = tables.last()
            parseLeaderboardEntries(entriesTable)

            val paginationData = boxContent.selectFirst("small")?.parsePagination() ?: PaginationData.default()
            currentPage = paginationData.currentPage
            resultsCount = paginationData.resultsCount
            totalPages = paginationData.totalPages
        }

    }

    private fun LeaderboardsBuilder.parseLeaderboardEntries(entriesTable: Element?) {
        for (row in entriesTable.rows().offsetStart(1)) {
            val cells = row.cells()
            val name = cells[1].selectFirst("a")?.getLinkInformation()?.title?.clean()
            val rank = cells[0].text().remove(".").toInt()
            val dromeLevel = cells[2].text().toInt()
            addEntry(
                if (name != null) LeaderboardsEntry(rank, name, dromeLevel) else DeletedLeaderboardsEntry(
                    rank, dromeLevel
                )
            )
        }
    }

}