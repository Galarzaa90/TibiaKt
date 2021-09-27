package com.galarzaa.tibiakt.parsers

import com.galarzaa.tibiakt.ParsingException
import com.galarzaa.tibiakt.builders.GuildBuilder
import com.galarzaa.tibiakt.core.parseTibiaDate
import com.galarzaa.tibiakt.models.Guild
import com.galarzaa.tibiakt.models.Vocation
import com.galarzaa.tibiakt.utils.clean
import com.galarzaa.tibiakt.utils.nullIfBlank
import com.galarzaa.tibiakt.utils.remove
import com.galarzaa.tibiakt.utils.wholeCleanText
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.nodes.TextNode
import org.jsoup.select.Elements

object GuildParser : Parser<Guild?> {
    private val descriptionRegex = Regex(
        """(?<description>.*)?The guild was founded on (?<world>\w+) on (?<date>[^.]+)\.""",
        setOf(
            RegexOption.DOT_MATCHES_ALL,
            RegexOption.MULTILINE
        )
    )
    private val guildHallRegex =
        Regex("""Their home on (?<world>\w+) is (?<name>[^.]+)\. The rent is paid until (?<paidUntil>[^.]+)""")
    private val disbandingRegex = Regex("""It will be disbanded on (\w+\s\d+\s\d+)\s([^.]+).""")

    override fun fromContent(content: String): Guild? {
        val document: Document = Jsoup.parse(content)
        val boxContent =
            document.selectFirst("div.BoxContent") ?: throw ParsingException("BoxContent container not found")
        val builder = GuildBuilder()
        boxContent.selectFirst("div#GuildInformationContainer")?.apply {
            parseGuildInformation(this, builder)
        } ?: throw ParsingException("Guild Information container not found")

        builder.name(boxContent.selectFirst("h1")?.text()?.trim() ?: throw ParsingException("Guild title not found"))
        val guildImg = boxContent.selectFirst("img[width=64]")
        builder.logoUrl(guildImg?.attr("src"))

        parseGuildMembers(boxContent, builder)
        return builder.build()
    }

    private fun parseGuildMembers(parsedContent: Element, builder: GuildBuilder) {
        val rows = parsedContent.select("tr[bgcolor]")
        var currentRank: String = ""
        for (row in rows) {
            val columns = row.select("td")
            when (columns.size) {
                6 -> currentRank = parseMemberRow(columns, currentRank, builder)
                2 -> parseInviteRow(columns, builder)
            }
        }
    }

    private fun parseInviteRow(columns: Elements, builder: GuildBuilder) {
        val (name, date) = columns.map { it.text().clean() }
        if (date.contains("Invitation Date"))
            return
        builder.addInvite(name, parseTibiaDate(date))
    }

    private fun parseMemberRow(columns: List<Element>, currentRank: String, builder: GuildBuilder): String {
        var rank = columns[0].text().clean()
        if (rank.isBlank()) {
            rank = currentRank
        }
        val nameLink = columns[1].selectFirst("a") ?: return currentRank
        val titleNode = nameLink.nextSibling()
        val title = (titleNode as TextNode?)?.text()?.remove("(")?.remove(")")?.clean()
        val name = nameLink.text().clean()
        val vocation = columns[2].text().clean()
        builder.addMember(
            rank,
            name,
            title.nullIfBlank(),
            Vocation.fromProperName(vocation) ?: throw ParsingException("unknown vocation in member '$: $vocation"),
            columns[3].text().toInt(),
            parseTibiaDate(columns[4].text().clean()),
            columns[5].text().contains("online")
        )
        return rank
    }

    private fun parseGuildInformation(container: Element, builder: GuildBuilder) {
        val containerText = container.wholeCleanText()
        descriptionRegex.find(containerText)?.apply {
            builder
                .description(groups["description"]?.value?.trim())
                .foundingDate(parseTibiaDate(groups["date"]!!.value))
                .world(groups["world"]!!.value)
        }
        builder
            .applicationsOpen(containerText.contains("opened for applications"))
            .isActive(containerText.contains("it is currently active", true))
        container.selectFirst("a")?.apply { builder.homepage(text()) }
        guildHallRegex.find(containerText)?.apply {
            builder.guildHall(
                groups["name"]!!.value,
                parseTibiaDate(groups["paidUntil"]!!.value)
            )
        }
        disbandingRegex.find(containerText)?.apply {
            val (_, date, reason) = groupValues
            builder.disbanding(
                parseTibiaDate(date),
                reason
            )
        }
    }
}