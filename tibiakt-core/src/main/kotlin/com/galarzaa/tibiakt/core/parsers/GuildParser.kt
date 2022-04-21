package com.galarzaa.tibiakt.core.parsers

import com.galarzaa.tibiakt.core.builders.GuildBuilder
import com.galarzaa.tibiakt.core.builders.guild
import com.galarzaa.tibiakt.core.enums.StringEnum
import com.galarzaa.tibiakt.core.exceptions.ParsingException
import com.galarzaa.tibiakt.core.models.guild.Guild
import com.galarzaa.tibiakt.core.utils.boxContent
import com.galarzaa.tibiakt.core.utils.clean
import com.galarzaa.tibiakt.core.utils.nullIfBlank
import com.galarzaa.tibiakt.core.utils.parseTablesMap
import com.galarzaa.tibiakt.core.utils.parseTibiaDate
import com.galarzaa.tibiakt.core.utils.remove
import com.galarzaa.tibiakt.core.utils.wholeCleanText
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.nodes.TextNode
import org.jsoup.select.Elements


object GuildParser : Parser<Guild?> {
    private val descriptionRegex =
        Regex("""(?<description>.*)?The guild was founded on (?<world>\w+) on (?<date>[^.]+)\.""",
            setOf(RegexOption.DOT_MATCHES_ALL, RegexOption.MULTILINE))
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
        val title = (titleNode as TextNode?)?.text()?.remove("(")?.remove(")")?.clean()
        val name = nameLink.text().clean()
        val vocation = columns[2].text().clean()
        addMember(rank,
            name,
            title.nullIfBlank(),
            StringEnum.fromValue(vocation) ?: throw ParsingException("unknown vocation in member '$: $vocation"),
            columns[3].text().toInt(),
            parseTibiaDate(columns[4].text().clean()),
            columns[5].text().contains("online"))
        return rank
    }

    private fun GuildBuilder.parseGuildInformation(container: Element) {
        val containerText = container.wholeCleanText()
        descriptionRegex.find(containerText)?.apply {
            description = groups["description"]?.value?.trim()
            foundingDate = parseTibiaDate(groups["date"]!!.value)
            world = groups["world"]!!.value
        }
        applicationsOpen = containerText.contains("opened for applications")
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