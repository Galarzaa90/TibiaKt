package com.galarzaa.tibiakt.core.parsers

import com.galarzaa.tibiakt.core.builders.forumsSection
import com.galarzaa.tibiakt.core.exceptions.ParsingException
import com.galarzaa.tibiakt.core.models.forums.ForumsSection
import com.galarzaa.tibiakt.core.utils.cells
import com.galarzaa.tibiakt.core.utils.cleanText
import com.galarzaa.tibiakt.core.utils.getLinkInformation
import com.galarzaa.tibiakt.core.utils.parseTablesMap
import com.galarzaa.tibiakt.core.utils.remove


object ForumsSectionParser : Parser<ForumsSection> {
    override fun fromContent(content: String): ForumsSection {
        val boxContent = boxContent(content)
        val tables = boxContent.parseTablesMap()
        if ("Boards" !in tables)
            throw ParsingException("Boards table not found")

        return forumsSection {
            val boardRows = tables["Boards"]!!.selectFirst("table.TableContent")?.select("tr:not(.LabelH)")!!
            for (row in boardRows) {
                val columns = row.cells()
                if (columns.size != 5)
                    continue
                val boardColumn = columns[1]
                val boardLink = boardColumn.selectFirst("a")?.getLinkInformation()!!
                addEntry {
                    name = boardLink.title
                    boardId = boardLink.queryParams["boardid"]!!.first().toInt()
                    description = boardColumn.selectFirst("font")?.cleanText() ?: ""
                    posts = columns[2].text().remove(",").toInt()
                    threads = columns[2].text().remove(",").toInt()
                }
                print(boardLink)
            }
        }
    }
}