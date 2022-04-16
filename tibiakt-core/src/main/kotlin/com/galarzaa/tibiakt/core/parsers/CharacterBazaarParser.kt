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

object CharacterBazaarParser : Parser<CharacterBazaar> {

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
            pvpType = PvpType.fromHighscoresFilterValue(searchData.values[PvpType.bazaarQueryParam]?.toInt())
            battlEyeType = IntEnum.fromValue(searchData.values[AuctionBattlEyeFilter.queryParam])
            vocation = IntEnum.fromValue(searchData.values[AuctionVocationFilter.queryParam])
            skill = IntEnum.fromValue(searchData.values[AuctionSkillFilter.queryParam])
            orderBy = IntEnum.fromValue(searchData.values[AuctionOrderBy.queryParam])
            orderDirection = IntEnum.fromValue(searchData.values[AuctionOrderDirection.queryParam])
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
