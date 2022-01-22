package com.galarzaa.tibiakt.core.parsers

import com.galarzaa.tibiakt.core.builders.CMPostArchiveBuilder
import com.galarzaa.tibiakt.core.models.forums.CMPost
import com.galarzaa.tibiakt.core.models.forums.CMPostArchive
import com.galarzaa.tibiakt.core.utils.PaginationData
import com.galarzaa.tibiakt.core.utils.ParsingException
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
        val builder = CMPostArchiveBuilder()
        tables["CM Post List"]?.let { parsePostList(it, builder) }
            ?: throw ParsingException("Could not find CM Post List table.")
        val form = boxContent.selectFirst("form") ?: throw ParsingException("Could not find search form.")

        parseSearchTable(form, builder)

        val paginationData =
            boxContent.selectFirst("small")?.parsePagination() ?: PaginationData.default()
        builder
            .currentPage(paginationData.currentPage)
            .totalPages(paginationData.totalPages)
            .resultsCount(paginationData.resultsCount)
        return builder.build()
    }

    private fun parseSearchTable(form: Element, builder: CMPostArchiveBuilder) {
        val formData = form.formData()
        builder.startDate(
            LocalDate.of(formData.data["startyear"]?.toInt()
                ?: throw ParsingException("could not find startyear param"),
                formData.data["startmonth"]?.toInt() ?: throw ParsingException("could not find startmonth param"),
                formData.data["startday"]?.toInt() ?: throw ParsingException("could not find startday param")))
            .endDate(LocalDate.of(formData.data["endyear"]?.toInt()
                ?: throw ParsingException("could not find endyear param"),
                formData.data["endmonth"]?.toInt() ?: throw ParsingException("could not find endmonth param"),
                formData.data["endday"]?.toInt() ?: throw ParsingException("could not find endday param")))
    }

    private fun parsePostList(table: Element, builder: CMPostArchiveBuilder) {
        for (row in table.rows().offsetStart(1)) {
            val columns = row.cells()
            val dateText = columns[0].text()
            val date = parseTibiaDateTime(dateText)
            val (forum, thread) = columns[1].replaceBrs().wholeCleanText().split("\n")
            val postLink =
                columns[2]?.selectFirst("a")?.getLinkInformation() ?: throw ParsingException("could not find post link")
            val postId = postLink.queryParams["postid"]?.get(0)?.toInt()
                ?: throw ParsingException("could not find postid in link")
            builder.addEntry(CMPost(postId, date, forum, thread))
        }
    }

}