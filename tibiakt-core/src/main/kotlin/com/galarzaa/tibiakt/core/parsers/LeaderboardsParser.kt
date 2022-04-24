package com.galarzaa.tibiakt.core.parsers

import com.galarzaa.tibiakt.core.builders.LeaderboardsBuilder
import com.galarzaa.tibiakt.core.builders.leaderboards
import com.galarzaa.tibiakt.core.exceptions.ParsingException
import com.galarzaa.tibiakt.core.models.Leaderboards
import com.galarzaa.tibiakt.core.models.LeaderboardsEntry
import com.galarzaa.tibiakt.core.utils.PaginationData
import com.galarzaa.tibiakt.core.utils.cells
import com.galarzaa.tibiakt.core.utils.clean
import com.galarzaa.tibiakt.core.utils.formData
import com.galarzaa.tibiakt.core.utils.getLinkInformation
import com.galarzaa.tibiakt.core.utils.offsetStart
import com.galarzaa.tibiakt.core.utils.parsePagination
import com.galarzaa.tibiakt.core.utils.remove
import com.galarzaa.tibiakt.core.utils.rows
import org.jsoup.nodes.Element
import java.time.Instant

object LeaderboardsParser : Parser<Leaderboards?> {
    override fun fromContent(content: String): Leaderboards? {
        val boxContent = boxContent(content)
        val tables = boxContent.select("table.TableContent")

        return leaderboards {
            val formData = tables[1].selectFirst("form")?.formData() ?: throw ParsingException("form not found")
            world = formData.values["world"]
                ?: if ("world" in formData.availableOptions) return null else throw ParsingException("world form parameter not found")
            rotation =
                formData.values["rotation"]?.toInt() ?: throw ParsingException("rotation form parameter not found")

            if (tables.size == 4) {
                val lastUpdateString = tables[2].text()
                val minutes =
                    Regex("""(\d+)""").find(lastUpdateString)?.groups?.get(0)?.value?.toInt() ?: throw ParsingException(
                        "unexpected last update text: $lastUpdateString")
                lastUpdated = Instant.now().minusSeconds(60L * minutes)
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
            addEntry(LeaderboardsEntry(rank = cells[0].text().remove(".").toInt(),
                name = name,
                dromeLevel = cells[2].text().toInt()))
        }
    }

}