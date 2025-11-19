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

package com.galarzaa.tibiakt.core.section.community.guild.parser

import com.galarzaa.tibiakt.core.collections.offsetStart
import com.galarzaa.tibiakt.core.exceptions.ParsingException
import com.galarzaa.tibiakt.core.html.TABLE_SELECTOR
import com.galarzaa.tibiakt.core.html.boxContent
import com.galarzaa.tibiakt.core.html.cleanText
import com.galarzaa.tibiakt.core.parser.Parser
import com.galarzaa.tibiakt.core.section.community.guild.builder.guildsSection
import com.galarzaa.tibiakt.core.section.community.guild.model.GuildsSection
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

/** Parser for the guild section. */
public object GuildsSectionParser : Parser<GuildsSection?> {
    override fun fromContent(content: String): GuildsSection? {
        val document: Document = Jsoup.parse(content, "")
        val boxContent = document.boxContent()
        val tables = boxContent.select(TABLE_SELECTOR)
        return guildsSection {
            val selectedWorld = boxContent.selectFirst("select[name=world]")?.selectFirst("option[selected]")
                ?: throw ParsingException("Could not find selected world")
            if (selectedWorld.attr("value").isBlank())
                return null
            world = selectedWorld.cleanText()
            for ((index, table) in tables.withIndex()) {
                val isActive = index == 0
                val rows = table.select("tr").orEmpty()
                for (row in rows.offsetStart(1)) {
                    val (logoColumn, nameColumn, _) = row.select("td")
                    val nameContainer = nameColumn.selectFirst("b") ?: continue
                    val logoUrl =
                        logoColumn.selectFirst("img")?.attr("src") ?: throw ParsingException("could not find logo URL")
                    val name = nameContainer.cleanText()
                    nameContainer.remove()
                    val description = nameColumn.cleanText().ifBlank { null }
                    addGuild(name, logoUrl, description, isActive)
                }
            }
        }
    }
}
