package com.galarzaa.tibiakt.core.parsers

import com.galarzaa.tibiakt.core.builders.bosstableBosses
import com.galarzaa.tibiakt.core.exceptions.ParsingException
import com.galarzaa.tibiakt.core.models.creatures.BosstableBosses
import com.galarzaa.tibiakt.core.utils.cleanText
import com.galarzaa.tibiakt.core.utils.parseTablesMap
import com.galarzaa.tibiakt.core.utils.remove
import java.io.File
import java.net.URL

public object BosstableBossesParser : Parser<BosstableBosses> {
    override fun fromContent(content: String): BosstableBosses {
        val boxContent = boxContent(content)
        val tables = boxContent.parseTablesMap("table.Table1")
        return bosstableBosses {
            tables["Boosted Boss"]?.let {
                val boostedCreatureImageUrl = it.selectFirst("img")?.attr("src")
                    ?: throw ParsingException("boosted boss image not found")
                val fileName = File(URL(boostedCreatureImageUrl).path).name.remove(".gif")
                boostedBoss {
                    name = it.selectFirst("b")?.cleanText() ?: throw ParsingException("Boss title not found")
                    identifier = fileName
                }
            } ?: throw ParsingException("Boosted boss table not found.")

            val entriesTable = boxContent.selectFirst("div[style*=display: table]")
                ?: throw ParsingException("could not find creatures list container")
            val entryContainers = entriesTable.select("div[style*=float: left]")
            for (entryContainer in entryContainers) {
                val imageUrl = entryContainer.selectFirst("img")?.attr("src")
                    ?: throw ParsingException("boss image not found")
                val fileName = File(URL(imageUrl).path).name.remove(".gif")
                addCreature {
                    name = entryContainer.cleanText()
                    identifier = fileName
                }
            }

        }
    }
}
