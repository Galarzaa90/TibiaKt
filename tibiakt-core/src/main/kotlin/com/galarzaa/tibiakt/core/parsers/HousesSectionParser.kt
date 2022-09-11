/*
 * Copyright © 2022 Allan Galarza
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.galarzaa.tibiakt.core.parsers

import com.galarzaa.tibiakt.core.builders.housesSection
import com.galarzaa.tibiakt.core.enums.HouseOrder
import com.galarzaa.tibiakt.core.enums.HouseStatus
import com.galarzaa.tibiakt.core.enums.StringEnum
import com.galarzaa.tibiakt.core.exceptions.ParsingException
import com.galarzaa.tibiakt.core.models.house.HousesSection
import com.galarzaa.tibiakt.core.utils.cells
import com.galarzaa.tibiakt.core.utils.cleanText
import com.galarzaa.tibiakt.core.utils.formData
import com.galarzaa.tibiakt.core.utils.getContaining
import com.galarzaa.tibiakt.core.utils.offsetStart
import com.galarzaa.tibiakt.core.utils.parseInteger
import com.galarzaa.tibiakt.core.utils.parseTablesMap
import com.galarzaa.tibiakt.core.utils.parseThousandSuffix
import com.galarzaa.tibiakt.core.utils.remove
import com.galarzaa.tibiakt.core.utils.rows
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.hours

/** Parses content from the houses section. */
public object HousesSectionParser : Parser<HousesSection?> {
    private val auctionInfoRegex = Regex("""\((?<bid>\d+) gold; (?<timeLeft>\w)+ (?<timeUnit>day|hour)s? left\)""")

    override fun fromContent(content: String): HousesSection? {
        val boxContent = boxContent(content)
        return housesSection {
            val tables = boxContent.parseTablesMap()
            tables["House Search"]?.apply {
                val form = boxContent.selectFirst("div.BoxContent > form")?.formData()
                    ?: throw ParsingException("could not find house form")
                world = form.values["world"] ?: throw ParsingException("could not find world value in form")
                town = form.values["town"] ?: throw ParsingException("could not find town value in form")
                status = StringEnum.fromValue(form.values["state"])
                type = StringEnum.fromValue(form.values["type"])
                    ?: throw ParsingException("could not find type value in form")
                StringEnum.fromValue<HouseOrder>(form.values["order"])?.also {
                    order = it
                }
            } ?: throw ParsingException("House Search table not found")
            tables.getContaining("Available")?.apply {
                for (row in rows().offsetStart(1)) {
                    val columns = row.cells()
                    if (columns.size != 5) break
                    val statusText = columns[3].cleanText()
                    var timeLeft: Duration? = null
                    var highestBid: Int? = null
                    auctionInfoRegex.find(statusText)?.apply {
                        highestBid = groups["bid"]!!.value.toInt()
                        val timeUnit = groups["timeUnit"]!!.value
                        timeLeft = groups["timeLeft"]!!.value.toLong().let {
                            when (timeUnit) {
                                "hour" -> it.hours
                                "day" -> it.days
                                else -> throw ParsingException("unknown time unit $timeUnit")
                            }
                        }
                    }
                    addEntry {
                        name = columns[0].cleanText()
                        size = columns[1].cleanText().remove("sqm").parseInteger()
                        rent = columns[2].cleanText().remove("gold").parseThousandSuffix()
                        status = if (statusText.contains("auctioned")) HouseStatus.AUCTIONED else HouseStatus.RENTED
                        houseId = columns[4].selectFirst("input[name=houseid]")?.`val`()?.toInt()
                            ?: throw ParsingException("could not find view button for house")
                        this.timeLeft = timeLeft
                        this.highestBid = highestBid
                        town = this@housesSection.town
                        world = this@housesSection.world
                        type = this@housesSection.type
                    }
                }
            } ?: return null
        }
    }
}
