/*
 * Copyright © 2024 Allan Galarza
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

import com.galarzaa.tibiakt.core.builders.CharacterBazaarBuilder
import com.galarzaa.tibiakt.core.builders.bazaarFilters
import com.galarzaa.tibiakt.core.builders.characterBazaar
import com.galarzaa.tibiakt.core.enums.AuctionBattlEyeFilter
import com.galarzaa.tibiakt.core.enums.AuctionOrderBy
import com.galarzaa.tibiakt.core.enums.AuctionOrderDirection
import com.galarzaa.tibiakt.core.enums.AuctionSearchType
import com.galarzaa.tibiakt.core.enums.AuctionSkillFilter
import com.galarzaa.tibiakt.core.enums.AuctionVocationFilter
import com.galarzaa.tibiakt.core.enums.BazaarType
import com.galarzaa.tibiakt.core.enums.IntEnum
import com.galarzaa.tibiakt.core.enums.PvpType
import com.galarzaa.tibiakt.core.exceptions.ParsingException
import com.galarzaa.tibiakt.core.models.bazaar.CharacterBazaar
import com.galarzaa.tibiakt.core.parsers.AuctionParser.parseAuctionContainer
import com.galarzaa.tibiakt.core.utils.formData
import com.galarzaa.tibiakt.core.utils.nullIfBlank
import com.galarzaa.tibiakt.core.utils.parseInteger
import com.galarzaa.tibiakt.core.utils.parsePagination
import com.galarzaa.tibiakt.core.utils.parseTablesMap
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
            pvpType = PvpType.fromHighscoresFilterValue(searchData.values[PvpType.QUERY_PARAM_BAZAAR]?.toInt())
            battlEyeType = IntEnum.fromValue(searchData.values[AuctionBattlEyeFilter.QUERY_PARAM])
            vocation = IntEnum.fromValue(searchData.values[AuctionVocationFilter.QUERY_PARAM])
            skill = IntEnum.fromValue(searchData.values[AuctionSkillFilter.QUERY_PARAM])
            orderBy = IntEnum.fromValue(searchData.values[AuctionOrderBy.QUERY_PARAM])
            orderDirection = IntEnum.fromValue(searchData.values[AuctionOrderDirection.QUERY_PARAM])
            minimumLevel = searchData.values["filter_levelrangefrom"]?.nullIfBlank()?.parseInteger()
            maximumLevel = searchData.values["filter_levelrangeto"]?.nullIfBlank()?.parseInteger()
            minimumSkillLevel = searchData.values["filter_skillrangefrom"]?.nullIfBlank()?.parseInteger()
            maximumSkillLevel = searchData.values["filter_skillrangeto"]?.nullIfBlank()?.parseInteger()

            if (forms.size > 1) {
                val additionalData = forms[1].formData()
                searchString = additionalData.values["searchstring"]?.nullIfBlank()
                searchType = IntEnum.fromValue(additionalData.values[AuctionSearchType.queryParam])
            }
        }
    }
}
