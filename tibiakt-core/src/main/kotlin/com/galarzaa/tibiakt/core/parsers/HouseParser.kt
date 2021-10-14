package com.galarzaa.tibiakt.core.parsers

import com.galarzaa.tibiakt.core.builders.HouseBuilder
import com.galarzaa.tibiakt.core.models.House
import com.galarzaa.tibiakt.core.utils.*

object HouseParser : Parser<House?> {
    override fun fromContent(content: String): House? {
        val boxContent = boxContent(content)
        val infoTable = boxContent.selectFirst("table")
        val builder = HouseBuilder()
        val (imageRow, descriptionRow) = infoTable.columns()
        val (title, sizeStr, rentStr, world) = descriptionRow.select("b").map { it.cleanText() }
        builder.name(title)
            .size(sizeStr.remove("square meters").clean().toInt())
            .rent(rentStr.remove("gold").parseThousandPrefix())
            .world(world)
        val descriptionLines = descriptionRow.replaceBrs().wholeText().lines()
        builder.beds(descriptionLines[1].findInteger())
        return null
    }
}