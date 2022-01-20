package com.galarzaa.tibiakt.core.parsers

import com.galarzaa.tibiakt.core.builders.CMPostArchiveBuilder
import com.galarzaa.tibiakt.core.models.forums.CMPost
import com.galarzaa.tibiakt.core.models.forums.CMPostArchive
import com.galarzaa.tibiakt.core.utils.cells
import com.galarzaa.tibiakt.core.utils.getLinkInformation
import com.galarzaa.tibiakt.core.utils.offsetStart
import com.galarzaa.tibiakt.core.utils.parseTablesMap
import com.galarzaa.tibiakt.core.utils.parseTibiaDateTime
import com.galarzaa.tibiakt.core.utils.replaceBrs
import com.galarzaa.tibiakt.core.utils.rows
import com.galarzaa.tibiakt.core.utils.wholeCleanText
import org.jsoup.nodes.Element
import java.time.LocalDate

object CMPostArchiveParser : Parser<CMPostArchive> {
    override fun fromContent(content: String): CMPostArchive {
        val boxContent = HouseParser.boxContent(content)
        val tables = boxContent.parseTablesMap()
        val builder = CMPostArchiveBuilder()
        tables["CM Post List"]?.let { parsePostList(it, builder) }
        return CMPostArchive(LocalDate.now(), LocalDate.now(), 0, 0, 0, emptyList())
    }

    private fun parsePostList(table: Element, builder: CMPostArchiveBuilder) {
        for (row in table.rows().offsetStart(1)) {
            val columns = row.cells()
            val dateText = columns[0].text()
            val date = parseTibiaDateTime(dateText)
            val (forum, thread) = columns[1].replaceBrs().wholeCleanText().split("\n")
            val postId =
                columns[2]?.selectFirst("a")?.getLinkInformation()?.queryParams?.get("postid")?.get(0)?.toInt()!!
            builder.addEntry(CMPost(postId, date, forum, thread))
        }
    }

}