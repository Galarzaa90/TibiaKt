package com.galarzaa.tibiakt.core.parsers

import com.galarzaa.tibiakt.core.builders.AuctionBuilder
import com.galarzaa.tibiakt.core.builders.BazaarFiltersBuilder
import com.galarzaa.tibiakt.core.builders.CharacterBazaarBuilder
import com.galarzaa.tibiakt.core.enums.AuctionBattlEyeFilter
import com.galarzaa.tibiakt.core.enums.AuctionOrderBy
import com.galarzaa.tibiakt.core.enums.AuctionOrderDirection
import com.galarzaa.tibiakt.core.enums.AuctionSearchType
import com.galarzaa.tibiakt.core.enums.AuctionSkillFilter
import com.galarzaa.tibiakt.core.enums.AuctionVocationFilter
import com.galarzaa.tibiakt.core.enums.BazaarType
import com.galarzaa.tibiakt.core.enums.IntEnum
import com.galarzaa.tibiakt.core.enums.PvpType
import com.galarzaa.tibiakt.core.models.bazaar.CharacterBazaar
import com.galarzaa.tibiakt.core.parsers.AuctionParser.parseAuctionContainer
import com.galarzaa.tibiakt.core.utils.ParsingException
import com.galarzaa.tibiakt.core.utils.formData
import com.galarzaa.tibiakt.core.utils.nullIfBlank
import com.galarzaa.tibiakt.core.utils.parseInteger
import com.galarzaa.tibiakt.core.utils.parsePagination
import com.galarzaa.tibiakt.core.utils.parseTablesMap
import org.jsoup.Jsoup
import org.jsoup.nodes.Element

object CharacterBazaarParser : Parser<CharacterBazaar> {

    override fun fromContent(content: String): CharacterBazaar {
        val builder = CharacterBazaarBuilder()
        val document = Jsoup.parse(content)
        val tables = document.parseTablesMap("table.Table3")
        tables["Filter Auctions"]?.apply { parseAuctionFilters(this, builder) }
        tables["Current Auctions"]?.apply {
            this.select("div.Auction").forEach {
                val auctionBuilder = AuctionBuilder()
                parseAuctionContainer(it, auctionBuilder)
                builder.addEntry(auctionBuilder.build())
            }
            builder.type(BazaarType.CURRENT)
        }
        tables["Auction History"]?.apply {
            this.select("div.Auction").forEach {
                val auctionBuilder = AuctionBuilder()
                parseAuctionContainer(it, auctionBuilder)
                builder.addEntry(auctionBuilder.build())
            }
            builder.type(BazaarType.HISTORY)
        }
        val paginationBlock = document.selectFirst("td.PageNavigation")
        paginationBlock?.parsePagination()?.run {
            builder
                .totalPages(totalPages)
                .currentPage(currentPage)
                .resultsCount(resultsCount)
        }
        return builder.build()
    }

    private fun parseAuctionFilters(filtersTable: Element, builder: CharacterBazaarBuilder) {
        val forms = filtersTable.select("form")
        val searchData = forms.first()?.formData() ?: throw ParsingException("could not find search form")

        val filterBuilder = BazaarFiltersBuilder()
        filterBuilder
            .world(searchData.data["filter_world"])
            .pvpType(PvpType.fromHighscoresFilterValue(searchData.data[PvpType.bazaarQueryParam]?.toInt()))
            .battlEyeType(IntEnum.fromValue(searchData.data[AuctionBattlEyeFilter.queryParam]))
            .vocation(IntEnum.fromValue(searchData.data[AuctionVocationFilter.queryParam]))
            .skill(IntEnum.fromValue(searchData.data[AuctionSkillFilter.queryParam]))
            .orderBy(IntEnum.fromValue(searchData.data[AuctionOrderBy.queryParam]))
            .orderDirection(IntEnum.fromValue(searchData.data[AuctionOrderDirection.queryParam]))
            .minimumLevel(searchData.data["filter_levelrangefrom"]?.nullIfBlank()?.parseInteger())
            .maximumLevel(searchData.data["filter_levelrangeto"]?.nullIfBlank()?.parseInteger())
            .minimumSkillLevel(searchData.data["filter_skillrangefrom"]?.nullIfBlank()?.parseInteger())
            .maximumSkillLevel(searchData.data["filter_skillrangeto"]?.nullIfBlank()?.parseInteger())

        if (forms.size > 1) {
            val additionalData = forms[1].formData()
            filterBuilder.searchString(additionalData.data["searchstring"]?.nullIfBlank())
                .searchType(IntEnum.fromValue(additionalData.data[AuctionSearchType.queryParam]))
        }
        builder
            .filters(filterBuilder.build())
    }
}
