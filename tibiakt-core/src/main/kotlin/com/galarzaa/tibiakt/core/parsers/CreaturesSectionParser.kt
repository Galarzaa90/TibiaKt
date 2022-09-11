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

import com.galarzaa.tibiakt.core.builders.creaturesSection
import com.galarzaa.tibiakt.core.exceptions.ParsingException
import com.galarzaa.tibiakt.core.models.creatures.CreaturesSection
import com.galarzaa.tibiakt.core.utils.cleanText
import com.galarzaa.tibiakt.core.utils.getLinkInformation
import com.galarzaa.tibiakt.core.utils.parseTablesMap

/** Parser for the creatures section. */
public object CreaturesSectionParser : Parser<CreaturesSection> {
    override fun fromContent(content: String): CreaturesSection {
        val boxContent = boxContent(content)
        val tables = boxContent.parseTablesMap("table.Table1")
        return creaturesSection {
            tables["Boosted Creature"]?.let {
                val boostedCreatureLink = it.selectFirst("a")?.getLinkInformation()
                    ?: throw ParsingException("boosted creature link not found")
                boostedCreature {
                    name = boostedCreatureLink.title
                    identifier = boostedCreatureLink.queryParams["race"]?.get(0)
                        ?: throw ParsingException("race not found in boosted creature's link")
                }
            } ?: throw ParsingException("Boosted Creature table not found.")

            val entriesTable = boxContent.selectFirst("div[style*=display: table]")
                ?: throw ParsingException("could not find creatures list container")
            val entryContainers = entriesTable.select("div[style*=float: left]")
            for (entryContainer in entryContainers) {
                val linkInfo = entryContainer.selectFirst("a")?.getLinkInformation()
                    ?: throw ParsingException("creature link not found")
                addCreature {
                    name = entryContainer.cleanText()
                    identifier = linkInfo.queryParams["race"]?.get(0)
                        ?: throw ParsingException("race not found in creature's link")
                }

            }

        }
    }
}
