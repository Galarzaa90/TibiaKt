package com.galarzaa.tibiakt.core.parsers

import com.galarzaa.tibiakt.core.builders.HousesSectionBuilder
import com.galarzaa.tibiakt.core.enums.HouseStatus
import com.galarzaa.tibiakt.core.enums.StringEnum
import com.galarzaa.tibiakt.core.models.HousesSection
import com.galarzaa.tibiakt.core.utils.*
import java.time.Duration

object HousesSectionParser : Parser<HousesSection?> {
    private val auctionInfoRegex = Regex("""\((?<bid>\d+) gold; (?<timeLeft>\w)+ (?<timeUnit>day|hour)s? left\)""")

    override fun fromContent(content: String): HousesSection? {
        val boxContent = boxContent(content)
        val builder = HousesSectionBuilder()
        val tables = boxContent.parseTablesMap()
        tables["House Search"]?.apply {
            val form = boxContent.selectFirst("div.BoxContent > form")!!.formData()
            builder
                .world(form.data["world"]!!)
                .town(form.data["town"]!!)
                .status(StringEnum.fromValue(form.data["state"]))
                .type(StringEnum.fromValue(form.data["type"])!!)
                .order(StringEnum.fromValue(form.data["order"]))
        } ?: throw ParsingException("House Search table not found")
        tables.getContaining("Available")?.apply {
            for (row in rows().offsetStart(1)) {
                val columns = row.columns()
                if (columns.size != 5)
                    break
                val statusText = columns[3].cleanText()
                var timeLeft: Duration? = null
                var highestBid: Int? = null
                auctionInfoRegex.find(statusText)?.apply {
                    highestBid = groups["bid"]!!.value.toInt()
                    val timeUnit = groups["timeUnit"]!!.value
                    timeLeft = groups["timeLeft"]!!.value.toLong().let {
                        when (timeUnit) {
                            "hour" -> Duration.ofHours(it)
                            "day" -> Duration.ofDays(it)
                            else -> throw ParsingException("unknown time unit $timeUnit")
                        }
                    }
                }
                builder.addEntry(
                    name = columns.first()!!.cleanText(),
                    size = columns[1].cleanText().remove("sqm").parseInteger(),
                    rent = columns[2].cleanText().remove("gold").parseThousandPrefix(),
                    status = if (statusText.contains("auctioned")) HouseStatus.AUCTIONED else HouseStatus.RENTED,
                    houseId = columns[4].selectFirst("input[name=houseid]")?.`val`()?.toInt() ?: throw ParsingException(
                        "could not find view button for house"
                    ),
                    timeLeft = timeLeft,
                    highestBid = highestBid
                )
            }
        } ?: return null
        return builder.build()
    }
}