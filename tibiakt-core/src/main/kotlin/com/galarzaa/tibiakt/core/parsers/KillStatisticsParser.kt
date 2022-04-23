package com.galarzaa.tibiakt.core.parsers


import com.galarzaa.tibiakt.core.builders.KillStatisticsBuilder
import com.galarzaa.tibiakt.core.builders.killStatistics
import com.galarzaa.tibiakt.core.exceptions.ParsingException
import com.galarzaa.tibiakt.core.models.KillStatistics
import com.galarzaa.tibiakt.core.utils.cellsText
import com.galarzaa.tibiakt.core.utils.formData
import com.galarzaa.tibiakt.core.utils.offsetStart
import com.galarzaa.tibiakt.core.utils.parseTablesMap
import com.galarzaa.tibiakt.core.utils.rows
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

object KillStatisticsParser : Parser<KillStatistics> {
    override fun fromContent(content: String): KillStatistics {
        val document: Document = Jsoup.parse(content)
        val boxContent =
            document.selectFirst("div.BoxContent") ?: throw ParsingException("BoxContent container not found")
        val tables = boxContent.parseTablesMap("table.Table1, table.Table3")
        return killStatistics {
            tables["Kill Statistics"]?.apply {
                parseKillStatisticsTable(this)
            } ?: throw ParsingException("kill statistics table not found")
            val form = boxContent.selectFirst("form") ?: throw ParsingException("could not find form in value")
            world = form.formData().values["world"] ?: throw ParsingException("could not find world value in form")
        }
    }

    private fun KillStatisticsBuilder.parseKillStatisticsTable(table: Element) {
        val innerTable = table.selectFirst("table.TableContent")
        for (row in innerTable.rows().offsetStart(2)) {
            val columnns = row.cellsText()
            if (columnns[0] == "Total") {
                total(
                    lastDayKilledPlayers = columnns[1].toInt(),
                    lastDayKilled = columnns[2].toInt(),
                    lastWeekKilledPlayers = columnns[3].toInt(),
                    lastWeekKilled = columnns[4].toInt(),
                )
            } else {
                addEntry(
                    race = columnns[0],
                    lastDayKilledPlayers = columnns[1].toInt(),
                    lastDayKilled = columnns[2].toInt(),
                    lastWeekKilledPlayers = columnns[3].toInt(),
                    lastWeekKilled = columnns[4].toInt(),
                )
            }
        }
    }
}