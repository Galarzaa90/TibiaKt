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

import com.galarzaa.tibiakt.core.builders.WorldBuilder
import com.galarzaa.tibiakt.core.builders.worldBuilder
import com.galarzaa.tibiakt.core.enums.BattlEyeType
import com.galarzaa.tibiakt.core.enums.StringEnum
import com.galarzaa.tibiakt.core.enums.TransferType
import com.galarzaa.tibiakt.core.exceptions.ParsingException
import com.galarzaa.tibiakt.core.models.world.World
import com.galarzaa.tibiakt.core.text.clean
import com.galarzaa.tibiakt.core.collections.getContaining
import com.galarzaa.tibiakt.core.collections.offsetStart
import com.galarzaa.tibiakt.core.text.parseInteger
import com.galarzaa.tibiakt.core.html.parseTablesMap
import com.galarzaa.tibiakt.core.time.parseTibiaDateTime
import com.galarzaa.tibiakt.core.time.parseTibiaFullDate
import com.galarzaa.tibiakt.core.text.remove
import com.galarzaa.tibiakt.core.html.rows
import com.galarzaa.tibiakt.core.time.FORMAT_YEAR_MONTH
import kotlinx.datetime.YearMonth
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

/** Parser for world pages. */
public object WorldParser : Parser<World?> {
    private val recordRegex = Regex("""(?<count>[\d.,]+) players \(on (?<date>[^)]+)\)""")
    private val battlEyeRegex = Regex("""since ([^.]+).""")

    override fun fromContent(content: String): World? {
        val document: Document = Jsoup.parse(content, "", org.jsoup.parser.Parser.xmlParser())
        val boxContent =
            document.selectFirst("div.BoxContent") ?: throw ParsingException("BoxContent container not found")
        val tables = boxContent.parseTablesMap("table.Table1, table.Table2")
        tables["Error"]?.apply { return null }
        val name = tables["World Selection"]?.selectFirst("option[selected]")?.text()?.clean()
            ?: throw ParsingException("World Selection table not found")
        val builder = worldBuilder {
            this.name = name
        }
        tables["World Information"]?.let { builder.parseWorldInformation(it) }
        tables.getContaining("Players Online")?.let { builder.parseOnlinePlayersTable(it) }
        return builder.build()
    }

    private fun WorldBuilder.parseOnlinePlayersTable(table: Element) {
        for (row in table.rows().offsetStart(2)) {
            val columns = row.select("td")
            val (name, level, vocation) = columns.map { it.text().clean() }
            addOnlinePlayer(name,
                level.toInt(),
                StringEnum.fromValue(vocation) ?: throw ParsingException("unknown vocation: $vocation"))
        }
    }

    private fun WorldBuilder.parseWorldInformation(table: Element) {
        for (row in table.rows().offsetStart(1)) {
            val columns = row.select("td")
            var (field, value) = columns.map { it.text().clean() }
            field = field.remove(":")
            when (field) {
                "Status" -> isOnline = value.contains("online", true)
                "Players Online" -> onlineCount = value.parseInteger()
                "Online Record" -> parseOnlineRecord(value)
                "Creation Date" -> parseCreationDate(value)
                "Location" -> location = value
                "PvP Type" -> pvpType =
                    StringEnum.fromValue(value) ?: throw ParsingException("unknown pvp type found: $value")

                "Premium Type" -> isPremiumRestricted = true
                "Transfer Type" -> parseTransferType(value)
                "World Quest Titles" -> if (!value.contains("has no title", true)) {
                    value.split(",").map { worldQuest(it.clean()) }
                }

                "BattlEye Status" -> parseBattlEyeStatus(value)
                "Game World Type" -> isExperimental = (value.contains("experimental", true))
            }
        }
    }

    private fun WorldBuilder.parseTransferType(value: String) {
        transferType = if (value.contains("locked", true)) {
            TransferType.LOCKED
        } else if (value.contains("blocked", true)) {
            TransferType.BLOCKED
        } else {
            TransferType.REGULAR
        }

    }

    private fun WorldBuilder.parseBattlEyeStatus(value: String) {
        battlEyeRegex.find(value)?.apply {
            val (_, since) = this.groupValues
            if (since.contains("release")) {
                battlEyeType = BattlEyeType.GREEN
                battlEyeStartDate = null
            } else {
                battlEyeType = BattlEyeType.YELLOW
                battlEyeStartDate = parseTibiaFullDate(since.clean())
            }
            return
        }
        battlEyeType = BattlEyeType.UNPROTECTED
        battlEyeStartDate = null
    }

    private fun WorldBuilder.parseOnlineRecord(value: String) {
        recordRegex.find(value)?.apply {
            val (_, count, date) = this.groupValues
            onlineRecordCount = count.parseInteger()
            onlineRecordDateTime = parseTibiaDateTime(date)

        }
    }

    private fun WorldBuilder.parseCreationDate(value: String) {
        creationDate = YearMonth.parse(value, FORMAT_YEAR_MONTH)

    }
}
