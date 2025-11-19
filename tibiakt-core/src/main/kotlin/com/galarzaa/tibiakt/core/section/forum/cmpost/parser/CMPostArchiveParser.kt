/*
 * Copyright © 2025 Allan Galarza
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

package com.galarzaa.tibiakt.core.section.forum.cmpost.parser

import com.galarzaa.tibiakt.core.collections.offsetStart
import com.galarzaa.tibiakt.core.exceptions.ParsingException
import com.galarzaa.tibiakt.core.html.PaginationData
import com.galarzaa.tibiakt.core.html.cells
import com.galarzaa.tibiakt.core.html.formData
import com.galarzaa.tibiakt.core.html.getLinkInformation
import com.galarzaa.tibiakt.core.html.parsePagination
import com.galarzaa.tibiakt.core.html.parseTablesMap
import com.galarzaa.tibiakt.core.html.replaceBrs
import com.galarzaa.tibiakt.core.html.rows
import com.galarzaa.tibiakt.core.html.wholeCleanText
import com.galarzaa.tibiakt.core.parser.Parser
import com.galarzaa.tibiakt.core.section.community.house.parser.HouseParser
import com.galarzaa.tibiakt.core.section.forum.cmpost.builder.CMPostArchiveBuilder
import com.galarzaa.tibiakt.core.section.forum.cmpost.builder.cmPostArchive
import com.galarzaa.tibiakt.core.section.forum.cmpost.model.CMPost
import com.galarzaa.tibiakt.core.section.forum.cmpost.model.CMPostArchive
import com.galarzaa.tibiakt.core.time.parseTibiaDateTime
import kotlinx.datetime.LocalDate
import org.jsoup.nodes.Element

/** Parser for the CM posts archive. */
public object CMPostArchiveParser : Parser<CMPostArchive> {
    override fun fromContent(content: String): CMPostArchive {
        val boxContent = HouseParser.boxContent(content)
        val tables = boxContent.parseTablesMap()
        return cmPostArchive {
            val form = boxContent.selectFirst("form") ?: throw ParsingException("Could not find search form.")
            tables["CM Post List"]?.let { parsePostList(it) }

            parseSearchTable(form)

            val paginationData =
                boxContent.selectFirst("small")?.parsePagination() ?: PaginationData.Companion.default()
            currentPage = paginationData.currentPage
            totalPages = paginationData.totalPages
            resultsCount = paginationData.resultsCount
        }
    }

    private fun CMPostArchiveBuilder.parseSearchTable(form: Element) {
        val formData = form.formData()
        startOn = LocalDate(
            formData.values["startyear"]?.toInt()
                ?: throw ParsingException("could not find startyear param"),
            formData.values["startmonth"]?.toInt() ?: throw ParsingException("could not find startmonth param"),
            formData.values["startday"]?.toInt() ?: throw ParsingException("could not find startday param")
        )
        endOn =
            LocalDate(
                formData.values["endyear"]?.toInt() ?: throw ParsingException("could not find endyear param"),
                formData.values["endmonth"]?.toInt() ?: throw ParsingException("could not find endmonth param"),
                formData.values["endday"]?.toInt() ?: throw ParsingException("could not find endday param")
            )
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
