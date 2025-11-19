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

package com.galarzaa.tibiakt.core.section.forum.section.parser

import com.galarzaa.tibiakt.core.exceptions.ParsingException
import com.galarzaa.tibiakt.core.html.TABLE_SELECTOR
import com.galarzaa.tibiakt.core.html.cells
import com.galarzaa.tibiakt.core.html.cleanText
import com.galarzaa.tibiakt.core.html.getLinkInformation
import com.galarzaa.tibiakt.core.html.parseLastPostFromCell
import com.galarzaa.tibiakt.core.html.parseTablesMap
import com.galarzaa.tibiakt.core.html.queryParams
import com.galarzaa.tibiakt.core.parser.Parser
import com.galarzaa.tibiakt.core.section.forum.section.builder.forumsSection
import com.galarzaa.tibiakt.core.section.forum.section.model.ForumSection
import com.galarzaa.tibiakt.core.text.remove
import java.net.URL
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

/** Parser for a forum section. */
public object ForumSectionParser : Parser<ForumSection> {
    override fun fromContent(content: String): ForumSection {
        val boxContent = boxContent(content)
        val tables = boxContent.parseTablesMap()
        if ("Boards" !in tables) throw ParsingException("Boards table not found")

        return forumsSection {
            val boardRows = tables["Boards"]!!.selectFirst(TABLE_SELECTOR)?.select("tr:not(.LabelH)")!!
            for (row in boardRows) {
                val columns = row.cells()
                if (columns.size != 6) continue
                val boardColumn = columns[1]
                val boardLink = boardColumn.selectFirst("a")?.getLinkInformation()!!

                addEntry {
                    name = boardLink.title
                    boardId = boardLink.queryParams["boardid"]!!.first().toInt()
                    description = boardColumn.selectFirst("font")?.cleanText().orEmpty()
                    posts = columns[2].text().remove(",").toInt()
                    threads = columns[3].text().remove(",").toInt()
                    lastPost = parseLastPostFromCell(columns[4])
                }
            }
            // Try to get section ID from login link
            sectionId = boxContent.selectFirst("p.ForumWelcome > a")?.getLinkInformation()?.let {
                val query = it.queryParams["redirect"]?.first() ?: return@let null
                val decodedUrl = URL(URLDecoder.decode(query, StandardCharsets.UTF_8))
                decodedUrl.queryParams()["sectionid"]?.first()?.toInt()
            } ?: 0
        }

    }
}
