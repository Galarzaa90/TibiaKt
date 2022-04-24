package com.galarzaa.tibiakt.core.parsers

import com.galarzaa.tibiakt.core.builders.HighscoresBuilder
import com.galarzaa.tibiakt.core.builders.highscores
import com.galarzaa.tibiakt.core.enums.IntEnum
import com.galarzaa.tibiakt.core.enums.PvpType
import com.galarzaa.tibiakt.core.enums.StringEnum
import com.galarzaa.tibiakt.core.exceptions.ParsingException
import com.galarzaa.tibiakt.core.models.highscores.Highscores
import com.galarzaa.tibiakt.core.utils.FormData
import com.galarzaa.tibiakt.core.utils.cellsText
import com.galarzaa.tibiakt.core.utils.cleanText
import com.galarzaa.tibiakt.core.utils.formData
import com.galarzaa.tibiakt.core.utils.getContaining
import com.galarzaa.tibiakt.core.utils.nullIfBlank
import com.galarzaa.tibiakt.core.utils.offsetStart
import com.galarzaa.tibiakt.core.utils.parseLong
import com.galarzaa.tibiakt.core.utils.parsePagination
import com.galarzaa.tibiakt.core.utils.parseTablesMap
import com.galarzaa.tibiakt.core.utils.rows
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import java.time.Instant

object HighscoresParser : Parser<Highscores?> {
    val numericMatch = Regex("""(\d+)""")

    override fun fromContent(content: String): Highscores {
        val document: Document = Jsoup.parse(content)
        val boxContent =
            document.selectFirst("div.BoxContent") ?: throw ParsingException("BoxContent container not found")
        val tables = boxContent.parseTablesMap("table.Table1, table.Table3")

        return highscores {
            tables["Highscores Filter"]?.also {
                val formData = boxContent.selectFirst("form")?.formData()
                    ?: throw ParsingException("could not find highscores form")
                parseHighscoresFilter(formData)
            }
            tables.getContaining("HighscoresLast")?.also {
                parseHighscoresTable(it)
            }
            val lastUpdateText = boxContent.selectFirst("span.RightArea")?.cleanText()
                ?: throw ParsingException("Could not find last update label")
            numericMatch.find(lastUpdateText)?.also {
                val minutes = it.groups[0]!!.value.toInt()
                lastUpdate = Instant.now().minusSeconds(60L * minutes)
            }
            val paginationData = boxContent.selectFirst("small")?.parsePagination()
                ?: throw ParsingException("could not find pagination block")

            currentPage = paginationData.currentPage
            totalPages = paginationData.totalPages
            resultsCount = paginationData.resultsCount
        }
    }

    private fun HighscoresBuilder.parseHighscoresFilter(formData: FormData) {

        world = formData.values["world"].nullIfBlank()
        category = IntEnum.fromValue(formData.values["category"]?.toInt())
            ?: throw ParsingException("could not find category form value")
        battlEyeType = IntEnum.fromValue(formData.values["beprotection"]?.toInt()
            ?: throw ParsingException("could not find beprotection form value"))
        vocation = IntEnum.fromValue(formData.values["profession"]?.toInt())
        for (pvpType in formData.valuesMultiple["${PvpType.highscoresQueryParam}[]"].orEmpty()) {
            PvpType.fromHighscoresFilterValue(pvpType.toInt())?.apply {
                worldTypes.add(this)
            }
        }
    }

    private fun HighscoresBuilder.parseHighscoresTable(element: Element) {
        val entriesTable = element.selectFirst("table.TableContent")
        for (row in entriesTable.rows().offsetStart(1)) {
            val columns = row.cellsText()
            if (columns.size < 6) {
                return
            }
            val columnOffset = if (columns.size == 7) 1 else 0
            val loyaltyTitle = if (columns.size == 7) columns[2] else null
            addEntry {
                rank = columns[0].toInt()
                name = columns[1 + columnOffset]
                vocation = StringEnum.fromValue(columns[2 + columnOffset])
                    ?: throw ParsingException("invalid vocation found: ${columns[2 + columnOffset]}")
                world = columns[3 + columnOffset]
                level = columns[4 + columnOffset].toInt()
                value = columns[5 + columnOffset].parseLong()
                additionalValue = loyaltyTitle
            }
        }
    }
}