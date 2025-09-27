/*
 * Copyright © 2024 Allan Galarza
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.galarzaa.tibiakt.core.parsers

import com.galarzaa.tibiakt.core.builders.CMPostArchiveBuilder
import com.galarzaa.tibiakt.core.builders.cmPostArchive
import com.galarzaa.tibiakt.core.exceptions.ParsingException
import com.galarzaa.tibiakt.core.models.forums.CMPost
import com.galarzaa.tibiakt.core.models.forums.CMPostArchive
import com.galarzaa.tibiakt.core.time.parseTibiaDateTime
import com.galarzaa.tibiakt.core.utils.PaginationData
import com.galarzaa.tibiakt.core.utils.cells
import com.galarzaa.tibiakt.core.utils.formData
import com.galarzaa.tibiakt.core.utils.getLinkInformation
import com.galarzaa.tibiakt.core.utils.offsetStart
import com.galarzaa.tibiakt.core.utils.parsePagination
import com.galarzaa.tibiakt.core.utils.parseTablesMap
import com.galarzaa.tibiakt.core.utils.replaceBrs
import com.galarzaa.tibiakt.core.utils.rows
import com.galarzaa.tibiakt.core.utils.wholeCleanText
import org.jsoup.nodes.Element
import kotlinx.datetime.LocalDate

/** Parser for the CM posts archive. */
public object CMPostArchiveParser : Parser<CMPostArchive> {
    override fun fromContent(content: String): CMPostArchive {
        val boxContent = HouseParser.boxContent(content)
        val tables = boxContent.parseTablesMap()
        return cmPostArchive {
            val form = boxContent.selectFirst("form") ?: throw ParsingException("Could not find search form.")
            tables["CM Post List"]?.let { parsePostList(it) }

            parseSearchTable(form)

            val paginationData = boxContent.selectFirst("small")?.parsePagination() ?: PaginationData.default()
            currentPage = paginationData.currentPage
            totalPages = paginationData.totalPages
            resultsCount = paginationData.resultsCount
        }
    }

    private fun CMPostArchiveBuilder.parseSearchTable(form: Element) {
        val formData = form.formData()
        startDate = LocalDate(formData.values["startyear"]?.toInt()
            ?: throw ParsingException("could not find startyear param"),
            formData.values["startmonth"]?.toInt() ?: throw ParsingException("could not find startmonth param"),
            formData.values["startday"]?.toInt() ?: throw ParsingException("could not find startday param"))
        endDate =
            LocalDate(formData.values["endyear"]?.toInt() ?: throw ParsingException("could not find endyear param"),
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
                columns[2].selectFirst("a")?.getLinkInformation() ?: throw ParsingException("could not find post link")
            val postId = postLink.queryParams["postid"]?.get(0)?.toInt()
                ?: throw ParsingException("could not find postid in link")
            addEntry(CMPost(postId, date, forum, thread))
        }
    }

}
