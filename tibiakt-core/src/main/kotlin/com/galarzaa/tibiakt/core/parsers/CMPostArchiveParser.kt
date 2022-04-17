package com.galarzaa.tibiakt.core.parsers

import com.galarzaa.tibiakt.core.builders.CMPostArchiveBuilder
import com.galarzaa.tibiakt.core.builders.cmPostArchive
import com.galarzaa.tibiakt.core.exceptions.ParsingException
import com.galarzaa.tibiakt.core.models.forums.CMPost
import com.galarzaa.tibiakt.core.models.forums.CMPostArchive
import com.galarzaa.tibiakt.core.utils.PaginationData
import com.galarzaa.tibiakt.core.utils.cells
import com.galarzaa.tibiakt.core.utils.formData
import com.galarzaa.tibiakt.core.utils.getLinkInformation
import com.galarzaa.tibiakt.core.utils.offsetStart
import com.galarzaa.tibiakt.core.utils.parsePagination
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
        return cmPostArchive {
            tables["CM Post List"]?.let { parsePostList(it) }
                ?: throw ParsingException("Could not find CM Post List table.")
            val form = boxContent.selectFirst("form") ?: throw ParsingException("Could not find search form.")

            parseSearchTable(form)

            val paginationData = boxContent.selectFirst("small")?.parsePagination() ?: PaginationData.default()
            currentPage = paginationData.currentPage
            totalPages = paginationData.totalPages
            resultsCount = paginationData.resultsCount
        }
    }

    private fun CMPostArchiveBuilder.parseSearchTable(form: Element) {
        val formData = form.formData()
        startDate = LocalDate.of(formData.values["startyear"]?.toInt()
            ?: throw ParsingException("could not find startyear param"),
            formData.values["startmonth"]?.toInt() ?: throw ParsingException("could not find startmonth param"),
            formData.values["startday"]?.toInt() ?: throw ParsingException("could not find startday param"))
        endDate =
            LocalDate.of(formData.values["endyear"]?.toInt() ?: throw ParsingException("could not find endyear param"),
                formData.values["endmonth"]?.toInt() ?: throw ParsingException("could not find endmonth param"),
                formData.values["endday"]?.toInt() ?: throw ParsingException("could not find endday param"))
    }

    private fun CMPostArchiveBuilder.parsePostList(table: Element) {
        for (row in table.rows().offsetStart(1)) {
            val columns = row.cells()
            val dateText = columns[0].text()
            val date = parseTibiaDateTime(dateText)
            val (forum, thread) = columns[1].replaceBrs().wholeCleanText().split("\n")
            val postLink =
                columns[2]?.selectFirst("a")?.getLinkInformation() ?: throw ParsingException("could not find post link")
            val postId = postLink.queryParams["postid"]?.get(0)?.toInt()
                ?: throw ParsingException("could not find postid in link")
            addEntry(CMPost(postId, date, forum, thread))
        }
    }

}