package com.galarzaa.tibiakt.parsers

import com.galarzaa.tibiakt.ParsingException
import com.galarzaa.tibiakt.builders.GuildBuilder
import com.galarzaa.tibiakt.core.parseTibiaDate
import com.galarzaa.tibiakt.models.Guild
import com.galarzaa.tibiakt.utils.parseTables
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

object GuildParser : Parser<Guild?> {
    private val descriptionRegex = Regex(
        """(?<description>.*)The guild was founded on (?<world>\w+) on (?<date>[^.]+)\.\nIt is currently (?<status>[^.]+)\.""",
        setOf(
            RegexOption.DOT_MATCHES_ALL,
            RegexOption.MULTILINE
        )
    )

    override fun fromContent(content: String): Guild? {
        val document: Document = Jsoup.parse(content, "", org.jsoup.parser.Parser.xmlParser())
        val boxContent =
            document.selectFirst("div.BoxContent") ?: throw ParsingException("BoxContent container not found")
        val tables = boxContent.parseTables("table.Table1, table.Table2, table.Table3")
        val builder = GuildBuilder()
        parseGuildInformationTable(
            tables["Guild Information"] ?: throw ParsingException("Guild information table not found"), builder
        )
        builder.name(boxContent.selectFirst("h1")?.text()?.trim() ?: throw ParsingException("Guild title not found"))
        val guildImg = boxContent.selectFirst("img[width=64]")
        builder.logoUrl(guildImg?.attr("src"))
        return builder.build()
    }

    private fun parseGuildInformationTable(elements: Elements, builder: GuildBuilder) {
        val container = elements.first()?.selectFirst("#GuildInformationContainer")
            ?: throw ParsingException("No guild information container found")
        descriptionRegex.find(container.wholeText())?.apply {
            builder
                .description(groups["description"]?.value?.trim())
                .isActive(groups["status"]!!.value.contains("active"))
                .world(groups["world"]!!.value)
                .foundingDate(parseTibiaDate(groups["date"]!!.value))
        }
        return
    }
}