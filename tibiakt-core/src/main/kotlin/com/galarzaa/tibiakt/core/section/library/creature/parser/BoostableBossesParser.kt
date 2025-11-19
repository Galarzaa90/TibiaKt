/*
 * Copyright © 2025 Allan Galarza
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

package com.galarzaa.tibiakt.core.section.library.creature.parser

import com.galarzaa.tibiakt.core.exceptions.ParsingException
import com.galarzaa.tibiakt.core.html.cleanText
import com.galarzaa.tibiakt.core.html.parseTablesMap
import com.galarzaa.tibiakt.core.parser.Parser
import com.galarzaa.tibiakt.core.section.library.creature.builder.boostableBosses
import com.galarzaa.tibiakt.core.section.library.creature.model.BoostableBosses
import com.galarzaa.tibiakt.core.text.remove
import java.io.File
import java.net.URL

/** Parser for the boostable bosses section. */
public object BoostableBossesParser : Parser<BoostableBosses> {
    override fun fromContent(content: String): BoostableBosses {
        val boxContent = boxContent(content)
        val tables = boxContent.parseTablesMap("table.Table1")
        return boostableBosses {
            tables["Boosted Boss"]?.let {
                val boostedCreatureImageUrl = it.selectFirst("img")?.attr("src")
                    ?: throw ParsingException("boosted boss image not found")
                val fileName = File(URL(boostedCreatureImageUrl).path).name.remove(".gif")
                boostedBoss {
                    name = it.selectFirst("p")?.cleanText()?.substringAfter("Today's boosted boss: ")
                        ?: throw ParsingException("Boss title not found")
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
