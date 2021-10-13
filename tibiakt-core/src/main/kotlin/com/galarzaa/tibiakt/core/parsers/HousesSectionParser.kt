package com.galarzaa.tibiakt.core.parsers

import com.galarzaa.tibiakt.core.builders.HousesSectionBuilder
import com.galarzaa.tibiakt.core.enums.StringEnum
import com.galarzaa.tibiakt.core.models.HousesSection
import com.galarzaa.tibiakt.core.utils.formData
import com.galarzaa.tibiakt.core.utils.parseTablesMap

object HousesSectionParser : Parser<HousesSection> {
    override fun fromContent(content: String): HousesSection {
        val boxContent = boxContent(content)
        val builder = HousesSectionBuilder()
        val tables = boxContent.parseTablesMap()
        tables["House Search"]?.apply {
            val form = boxContent.formData()
            builder
                .world(form.data["world"]!!)
                .town(form.data["town"]!!)
                .status(StringEnum.fromValue(form.data["status"]))
                .type(StringEnum.fromValue(form.data["type"])!!)
                .order(StringEnum.fromValue(form.data["order"]))
        }
        return builder.build()
    }
}