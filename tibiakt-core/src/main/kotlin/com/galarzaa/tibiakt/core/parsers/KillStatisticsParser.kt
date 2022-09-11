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

import com.galarzaa.tibiakt.core.builders.KillStatisticsBuilder
import com.galarzaa.tibiakt.core.builders.killStatistics
import com.galarzaa.tibiakt.core.exceptions.ParsingException
import com.galarzaa.tibiakt.core.models.KillStatistics
import com.galarzaa.tibiakt.core.utils.cellsText
import com.galarzaa.tibiakt.core.utils.formData
import com.galarzaa.tibiakt.core.utils.offsetStart
import com.galarzaa.tibiakt.core.utils.parseTablesMap
import com.galarzaa.tibiakt.core.utils.rows
import org.jsoup.nodes.Element

public object KillStatisticsParser : Parser<KillStatistics?> {
    override fun fromContent(content: String): KillStatistics? {
        val boxContent = boxContent(content)
        val formData =
            boxContent.selectFirst("form")?.formData() ?: throw ParsingException("Could not find world selection form")
        if (formData.values["world"].isNullOrBlank())
            return null
        val tables = boxContent.parseTablesMap("table.Table1, table.Table3")
        return killStatistics {
            tables["Kill Statistics"]?.apply {
                parseKillStatisticsTable(this)
            } ?: throw ParsingException("kill statistics table not found")
            boxContent.selectFirst("form") ?: throw ParsingException("could not find form in value")
            world = formData.values["world"] ?: throw ParsingException("could not find world value in form")
        }
    }

    private fun KillStatisticsBuilder.parseKillStatisticsTable(table: Element) {
        val innerTable = table.selectFirst("table.TableContent")
        for (row in innerTable.rows().offsetStart(2)) {
            val columnns = row.cellsText()
            if (columnns[0] == "Total") {
                total(
                    lastDayKilledPlayers = columnns[1].toInt(),
                    lastDayKilled = columnns[2].toInt(),
                    lastWeekKilledPlayers = columnns[3].toInt(),
                    lastWeekKilled = columnns[4].toInt(),
                )
            } else {
                addEntry(
                    race = columnns[0],
                    lastDayKilledPlayers = columnns[1].toInt(),
                    lastDayKilled = columnns[2].toInt(),
                    lastWeekKilledPlayers = columnns[3].toInt(),
                    lastWeekKilled = columnns[4].toInt(),
                )
            }
        }
    }
}
