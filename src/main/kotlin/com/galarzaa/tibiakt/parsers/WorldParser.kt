package com.galarzaa.tibiakt.parsers

import com.galarzaa.tibiakt.ParsingException
import com.galarzaa.tibiakt.builders.WorldBuilder
import com.galarzaa.tibiakt.core.parseTibiaDateTime
import com.galarzaa.tibiakt.core.parseTibiaFullDate
import com.galarzaa.tibiakt.models.BattlEyeType
import com.galarzaa.tibiakt.models.TransferType
import com.galarzaa.tibiakt.models.World
import com.galarzaa.tibiakt.utils.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.time.YearMonth

object WorldParser : Parser<World?> {
    private val recordRegex = Regex("""(?<count>[\d.,]+) players \(on (?<date>[^)]+)\)""")
    private val battlEyeRegex = Regex("""since ([^.]+).""")

    override fun fromContent(content: String): World? {
        val document: Document = Jsoup.parse(content, "", org.jsoup.parser.Parser.xmlParser())
        val boxContent =
            document.selectFirst("div.BoxContent") ?: throw ParsingException("BoxContent container not found")
        val tables = boxContent.parseTables("table.Table1, table.Table2")
        tables["Error"]?.apply { return null }
        val name = tables["World Selection"]?.select("option[selected]")?.text()?.clean()
            ?: throw ParsingException("World Selection table not found")
        val builder = WorldBuilder().name(name)
        tables["World Information"]?.apply { parseWorldInformation(this, builder) }
        tables.getContaining("Players Online")?.apply { parseOnlinePlayersTable(this, builder) }
        return builder.build()
    }

    private fun parseOnlinePlayersTable(rows: Elements, builder: WorldBuilder) {
        for (row in rows.subList(2, rows.size)) {
            val columns = row.select("td")
            val (name, level, vocation) = columns.map { it.text().clean() }
            builder.addOnlinePlayer(name, level.toInt(), vocation)
        }
    }


    private fun parseWorldInformation(rows: Elements, builder: WorldBuilder) {
        for (row in rows.subList(1, rows.size)) {
            val columns = row.select("td")
            var (field, value) = columns.map { it.text().clean() }
            field = field.remove(":")
            when (field) {
                "Status" -> builder.isOnline(value.contains("online", true))
                "Players Online" -> builder.onlineCount(value.parseInteger())
                "Online Record" -> parseOnlineRecord(value, builder)
                "Creation Date" -> parseCreationDate(value, builder)
                "Location" -> builder.location(value)
                "PvP Type" -> builder.pvpType(value)
                "Premium Type" -> builder.premiumRestricted(true)
                "Transfer Type" -> parseTransferType(value, builder)
                "World Quest Titles" -> if (!value.contains("has no title", true)) {
                    value.split(",").map { builder.addWorldQuest(it.clean()) }
                }
                "BattlEye Status" -> parseBattlEyeStatus(value, builder)
                "Game World Type" -> builder.experimental(value.contains("experimental", true))
            }
        }
    }

    private fun parseTransferType(value: String, builder: WorldBuilder) {
        builder.transferType(
            if (value.contains("locked", true)) {
                TransferType.LOCKED
            } else if (value.contains("blocked", true)) {
                TransferType.BLOCKED
            } else {
                TransferType.REGULAR
            }
        )
    }

    private fun parseBattlEyeStatus(value: String, builder: WorldBuilder) {
        battlEyeRegex.find(value)?.apply {
            val (_, since) = this.groupValues
            if (since.contains("release")) {
                builder.battlEyeType(BattlEyeType.GREEN).battlEyeStartDate(null)
            } else {
                builder.battlEyeType(BattlEyeType.YELLOW).battlEyeStartDate(parseTibiaFullDate(since))
            }
            return
        }
        builder
            .battlEyeType(BattlEyeType.UNPROTECTED)
            .battlEyeStartDate(null)
    }

    private fun parseOnlineRecord(value: String, builder: WorldBuilder) {
        recordRegex.find(value)?.apply {
            val (_, count, date) = this.groupValues
            builder.onlineRecord(
                count.parseInteger(),
                parseTibiaDateTime(date)
            )
        }
    }

    private fun parseCreationDate(value: String, builder: WorldBuilder) {
        val (month, year) = value.split("/").map { it.toInt() }
        builder.creationDate(
            YearMonth.of(
                if (year > 90) year + 1900 else year + 2000,
                month
            )
        )
    }
}