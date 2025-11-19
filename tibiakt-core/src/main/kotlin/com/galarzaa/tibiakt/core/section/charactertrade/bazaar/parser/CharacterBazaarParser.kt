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

package com.galarzaa.tibiakt.core.section.charactertrade.bazaar.parser

import com.galarzaa.tibiakt.core.domain.world.PvpType
import com.galarzaa.tibiakt.core.enums.IntEnum
import com.galarzaa.tibiakt.core.exceptions.ParsingException
import com.galarzaa.tibiakt.core.html.formData
import com.galarzaa.tibiakt.core.html.parsePagination
import com.galarzaa.tibiakt.core.html.parseTablesMap
import com.galarzaa.tibiakt.core.parser.Parser
import com.galarzaa.tibiakt.core.section.charactertrade.bazaar.builder.CharacterBazaarBuilder
import com.galarzaa.tibiakt.core.section.charactertrade.bazaar.builder.bazaarFilters
import com.galarzaa.tibiakt.core.section.charactertrade.bazaar.builder.characterBazaar
import com.galarzaa.tibiakt.core.section.charactertrade.bazaar.model.AuctionBattlEyeFilter
import com.galarzaa.tibiakt.core.section.charactertrade.bazaar.model.AuctionOrderBy
import com.galarzaa.tibiakt.core.section.charactertrade.bazaar.model.AuctionOrderDirection
import com.galarzaa.tibiakt.core.section.charactertrade.bazaar.model.AuctionSearchType
import com.galarzaa.tibiakt.core.section.charactertrade.bazaar.model.AuctionSkillFilter
import com.galarzaa.tibiakt.core.section.charactertrade.bazaar.model.AuctionVocationFilter
import com.galarzaa.tibiakt.core.section.charactertrade.bazaar.model.BazaarType
import com.galarzaa.tibiakt.core.section.charactertrade.bazaar.model.CharacterBazaar
import com.galarzaa.tibiakt.core.section.charactertrade.bazaar.parser.AuctionParser.parseAuctionContainer
import com.galarzaa.tibiakt.core.text.nullIfBlank
import com.galarzaa.tibiakt.core.text.parseInteger
import org.jsoup.Jsoup
import org.jsoup.nodes.Element

/** Parser for the character bazaar. */
public object CharacterBazaarParser : Parser<CharacterBazaar> {

    override fun fromContent(content: String): CharacterBazaar {
        val document = Jsoup.parse(content)
        val tables = document.parseTablesMap("table.Table3")
        return characterBazaar {
            tables["Filter Auctions"]?.apply { parseAuctionFilters(this) }
            tables["Current Auctions"]?.apply {
                select("div.Auction").forEach {
                    auction { parseAuctionContainer(it) }
                }
                type = BazaarType.CURRENT
            }
            tables["Auction History"]?.apply {
                select("div.Auction").forEach {
                    auction { parseAuctionContainer(it) }
                }
                type = BazaarType.HISTORY
            }
            val paginationBlock = document.selectFirst("td.PageNavigation")
            paginationBlock?.parsePagination()?.let {
                totalPages = it.totalPages
                currentPage = it.currentPage
                resultsCount = it.resultsCount
            }
        }

    }

    private fun CharacterBazaarBuilder.parseAuctionFilters(filtersTable: Element) {
        val forms = filtersTable.select("form")
        val searchData = forms.first()?.formData() ?: throw ParsingException("could not find search form")

        filters = bazaarFilters {
            world = searchData.values["filter_world"]
            pvpType =
                PvpType.Companion.fromHighscoresFilterValue(searchData.values[PvpType.Companion.QUERY_PARAM_BAZAAR]?.toInt())
            battlEyeType = IntEnum.Companion.fromValue(searchData.values[AuctionBattlEyeFilter.Companion.QUERY_PARAM])
            vocation = IntEnum.Companion.fromValue(searchData.values[AuctionVocationFilter.Companion.QUERY_PARAM])
            skill = IntEnum.Companion.fromValue(searchData.values[AuctionSkillFilter.Companion.QUERY_PARAM])
            orderBy = IntEnum.Companion.fromValue(searchData.values[AuctionOrderBy.Companion.QUERY_PARAM])
            orderDirection = IntEnum.Companion.fromValue(searchData.values[AuctionOrderDirection.Companion.QUERY_PARAM])
            minimumLevel = searchData.values["filter_levelrangefrom"]?.nullIfBlank()?.parseInteger()
            maximumLevel = searchData.values["filter_levelrangeto"]?.nullIfBlank()?.parseInteger()
            minimumSkillLevel = searchData.values["filter_skillrangefrom"]?.nullIfBlank()?.parseInteger()
            maximumSkillLevel = searchData.values["filter_skillrangeto"]?.nullIfBlank()?.parseInteger()

            if (forms.size > 1) {
                val additionalData = forms[1].formData()
                searchString = additionalData.values["searchstring"]?.nullIfBlank()
                searchType = IntEnum.Companion.fromValue(additionalData.values[AuctionSearchType.Companion.queryParam])
            }
        }
    }
}
