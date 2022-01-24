package com.galarzaa.tibiakt.core.parsers

import com.galarzaa.tibiakt.core.builders.CreaturesSectionBuilder
import com.galarzaa.tibiakt.core.exceptions.ParsingException
import com.galarzaa.tibiakt.core.models.creatures.CreatureEntry
import com.galarzaa.tibiakt.core.models.creatures.CreaturesSection
import com.galarzaa.tibiakt.core.utils.cleanText
import com.galarzaa.tibiakt.core.utils.getLinkInformation
import com.galarzaa.tibiakt.core.utils.parseTablesMap

object CreaturesSectionParser : Parser<CreaturesSection> {
    override fun fromContent(content: String): CreaturesSection {
        val boxContent = boxContent(content)
        val tables = boxContent.parseTablesMap("table.Table1")
        val builder = CreaturesSectionBuilder()
        tables["Boosted Creature"]?.let {
            val boostedCreatureLink =
                it.selectFirst("a")?.getLinkInformation() ?: throw ParsingException("boosted creature link not found")
            builder.boostedCreature(CreatureEntry(name = boostedCreatureLink.title,
                identifier = boostedCreatureLink.queryParams["race"]?.get(0)
                    ?: throw ParsingException("race not found in boosted creature's link")))
        } ?: throw ParsingException("Boosted Creature table not found.")

        val entriesTable = boxContent.selectFirst("div[style*=display: table]")
            ?: throw ParsingException("could not find creatures list container")
        val entryContainers = entriesTable.select("div[style*=float: left]")
        for (entryContainer in entryContainers) {
            val linkInfo = entryContainer.selectFirst("a")?.getLinkInformation()
                ?: throw ParsingException("creature link not found")
            builder.addCreature(CreatureEntry(
                name = entryContainer.cleanText(),
                identifier = linkInfo.queryParams["race"]?.get(0)
                    ?: throw ParsingException("race not found in creature's link")
            )
            )
        }
        return builder.build()
    }

}