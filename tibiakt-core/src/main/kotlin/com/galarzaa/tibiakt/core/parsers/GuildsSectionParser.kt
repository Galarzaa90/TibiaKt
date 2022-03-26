package com.galarzaa.tibiakt.core.parsers

import com.galarzaa.tibiakt.core.builders.guildsSection
import com.galarzaa.tibiakt.core.exceptions.ParsingException
import com.galarzaa.tibiakt.core.models.guild.GuildsSection
import com.galarzaa.tibiakt.core.utils.cleanText
import com.galarzaa.tibiakt.core.utils.offsetStart
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

object GuildsSectionParser : Parser<GuildsSection?> {
    override fun fromContent(content: String): GuildsSection? {
        val document: Document = Jsoup.parse(content, "")
        val boxContent =
            document.selectFirst("div.BoxContent") ?: throw ParsingException("BoxContent container not found")
        val tables = boxContent.select("table.TableContent")
        return guildsSection {
            val selectedWorld = boxContent.selectFirst("select[name=world]")?.selectFirst("option[selected]")
                ?: throw ParsingException("Could not find selected world")
            world = selectedWorld.cleanText()
            for ((index, table) in tables.withIndex()) {
                val isActive = index == 0
                val rows = table?.select("tr") ?: emptyList()
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