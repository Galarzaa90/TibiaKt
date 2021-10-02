package com.galarzaa.tibiakt.parsers


import com.galarzaa.tibiakt.builders.KillStatisticsBuilder
import com.galarzaa.tibiakt.core.ParsingException
import com.galarzaa.tibiakt.models.KillStatistics
import com.galarzaa.tibiakt.utils.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

object KillStatisticsParser : Parser<KillStatistics> {
    override fun fromContent(content: String): KillStatistics {
        val document: Document = Jsoup.parse(content)
        val boxContent =
            document.selectFirst("div.BoxContent") ?: throw ParsingException("BoxContent container not found")
        val tables = boxContent.parseTablesMap("table.Table1, table.Table3")
        val builder = KillStatisticsBuilder()
        tables["Kill Statistics"]?.apply {
            parseKillStatisticsTable(this, builder)
        } ?: throw ParsingException("kill statistics table not found")
        val form = boxContent.selectFirst("form")
        form?.formData()?.apply {
            builder.world(this.data["world"]!!)
        }
        return builder.build()
    }

    private fun parseKillStatisticsTable(table: Element, builder: KillStatisticsBuilder) {
        val innerTable = table.selectFirst("table.TableContent")
        for (row in innerTable.rows().offsetStart(2)) {
            val columnns = row.columnsText()
            if (columnns[0] == "Total") {
                builder.total(
                    columnns[1].toInt(),
                    columnns[2].toInt(),
                    columnns[3].toInt(),
                    columnns[4].toInt(),
                )
            } else {
                builder.addEntry(
                    columnns[0],
                    columnns[1].toInt(),
                    columnns[2].toInt(),
                    columnns[3].toInt(),
                    columnns[4].toInt(),
                )
            }
        }
    }
}