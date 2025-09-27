/*
 * Copyright © 2022 Allan Galarza
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

import com.galarzaa.tibiakt.core.builders.GuildBuilder
import com.galarzaa.tibiakt.core.builders.guild
import com.galarzaa.tibiakt.core.enums.StringEnum
import com.galarzaa.tibiakt.core.exceptions.ParsingException
import com.galarzaa.tibiakt.core.models.guild.Guild
import com.galarzaa.tibiakt.core.html.boxContent
import com.galarzaa.tibiakt.core.text.clean
import com.galarzaa.tibiakt.core.text.nullIfBlank
import com.galarzaa.tibiakt.core.html.parseTablesMap
import com.galarzaa.tibiakt.core.time.parseTibiaDate
import com.galarzaa.tibiakt.core.text.remove
import com.galarzaa.tibiakt.core.html.wholeCleanText
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.nodes.TextNode
import org.jsoup.select.Elements

/** Parser for the guild information page. */
public object GuildParser : Parser<Guild?> {
    private val descriptionRegex =
        Regex(
            """(?<description>.*)?The guild was founded on (?<world>\w+) on (?<date>[^.]+)\.""",
            setOf(RegexOption.DOT_MATCHES_ALL, RegexOption.MULTILINE)
        )
    private val guildHallRegex =
        Regex("""Their home on (?<world>\w+) is (?<name>[^.]+)\. The rent is paid until (?<paidUntil>[^.]+)""")
    private val disbandingRegex = Regex("""It will be disbanded on (\w+\s\d+\s\d+)\s([^.]+).""")

    override fun fromContent(content: String): Guild? {
        val document: Document = Jsoup.parse(content)
        val boxContent = document.boxContent()
        return guild {
            val tables = boxContent.parseTablesMap("table.Table1, table.Table3")
            if ("Error" in tables) {
                return null
            }
            tables["Guild Information"]?.apply { parseGuildInformation(this) }
                ?: throw ParsingException("Guild Information container not found")

            name = boxContent.selectFirst("h1")?.text()?.trim() ?: throw ParsingException("Guild title not found")
            val guildImg = boxContent.selectFirst("img[width=64]")
            logoUrl = guildImg?.attr("src")
            tables["Guild Members"]?.apply { parseGuildMembers(this) }
            tables["Invited Characters"]?.apply { parseGuildMembers(this) }
        }
    }

    private fun GuildBuilder.parseGuildMembers(parsedContent: Element) {
        val rows = parsedContent.select("tr[bgcolor]")
        var currentRank = ""
        for (row in rows) {
            val columns = row.select("td")
            when (columns.size) {
                6 -> currentRank = parseMemberRow(columns, currentRank)
                2 -> parseInviteRow(columns)
            }
        }
    }

    private fun GuildBuilder.parseInviteRow(columns: Elements) {
        val (name, date) = columns.map { it.text().clean() }
        if (date.contains("Invitation Date")) return
        addInvite(name, parseTibiaDate(date))
    }

    private fun GuildBuilder.parseMemberRow(columns: List<Element>, currentRank: String): String {
        var rank = columns[0].text().clean()
        if (rank.isBlank()) {
            rank = currentRank
        }
        val nameLink = columns[1].selectFirst("a") ?: return currentRank
        val titleNode = nameLink.nextSibling()
        addMember {
            this.rank = rank
            name = nameLink.text().clean()
            title = (titleNode as? TextNode)?.text()?.remove("(")?.remove(")")?.clean()
            vocation = StringEnum.fromValue(columns[2].text().clean())
                ?: throw ParsingException("unknown vocation in member: ${columns[2].text().clean()}")
            level = columns[3].text().toInt()
            joiningDate = parseTibiaDate(columns[4].text().clean())
            isOnline = columns[5].text().contains("online")
        }
        return rank
    }

    private fun GuildBuilder.parseGuildInformation(container: Element) {
        val containerText = container.wholeCleanText()
        descriptionRegex.find(containerText)?.apply {
            description = groups["description"]?.value?.trim().nullIfBlank()
            foundingDate = parseTibiaDate(groups["date"]!!.value)
            world = groups["world"]!!.value
        }
        hasOpenApplications = containerText.contains("opened for applications")
        isActive = containerText.contains("it is currently active", true)
        container.selectFirst("a")?.apply { homepage = text() }
        guildHallRegex.find(containerText)?.apply {
            guildHall(groups["name"]!!.value, parseTibiaDate(groups["paidUntil"]!!.value))
        }
        disbandingRegex.find(containerText)?.apply {
            val (_, date, reason) = groupValues
            disbandingDate = parseTibiaDate(date)
            disbandingReason = reason
        }
    }
}
