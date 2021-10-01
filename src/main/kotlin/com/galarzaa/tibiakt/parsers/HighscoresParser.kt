package com.galarzaa.tibiakt.parsers

import com.galarzaa.tibiakt.builders.HighscoresBuilder
import com.galarzaa.tibiakt.core.ParsingException
import com.galarzaa.tibiakt.enums.IntEnum
import com.galarzaa.tibiakt.enums.Vocation
import com.galarzaa.tibiakt.models.Highscores
import com.galarzaa.tibiakt.utils.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

object HighscoresParser : Parser<Highscores?> {
    val numericMatch = Regex("""(\d+)""")

    override fun fromContent(content: String): Highscores? {
        val document: Document = Jsoup.parse(content)
        val boxContent =
            document.selectFirst("div.BoxContent") ?: throw ParsingException("BoxContent container not found")
        val tables = boxContent.parseTablesMap("table.Table1, table.Table3")

        val builder = HighscoresBuilder()
        tables["Highscores Filter"]?.apply {
            val formData = boxContent.selectFirst("form")?.formData()
            parseHighscoresFilter(formData!!, builder)
            print("")
        }
        tables["Highscores"]?.apply {
            parseHighscoresTable(this, builder)
            val lastUpdateText = boxContent.selectFirst("span.RightArea")?.cleanText()
            numericMatch.find(lastUpdateText!!)?.apply {
                val minutes = this.groups[0]?.value
            }
        }
        return builder.build()
    }

    private fun parseHighscoresFilter(formData: FormData, builder: HighscoresBuilder) {
        builder
            .world(formData.data["world"].nullIfBlank())
            .category(IntEnum.fromValue(formData.data["category"]?.toInt())!!)
            .battlEyeType(IntEnum.fromValue(formData.data["beprotection"]?.toInt())!!)
            .vocation(IntEnum.fromValue(formData.data["profession"]?.toInt()))
    }

    private fun parseHighscoresTable(element: Element, builder: HighscoresBuilder) {
        val entriesTable = element.selectFirst("table.TableContent")
        for (row in entriesTable.rows().offsetStart(1)) {
            val columns = row.columnsText()
            builder.addEntry(
                columns[0].toInt(),
                columns[1],
                Vocation.fromProperName(columns[2]) ?: throw ParsingException("invalid vocation found: ${columns[2]}"),
                columns[3],
                columns[4].toInt(),
                columns[5].parseLong()
            )
        }
    }
}