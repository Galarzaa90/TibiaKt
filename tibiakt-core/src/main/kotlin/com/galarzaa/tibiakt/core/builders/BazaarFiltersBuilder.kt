package com.galarzaa.tibiakt.core.builders

import com.galarzaa.tibiakt.core.enums.AuctionBattlEyeFilter
import com.galarzaa.tibiakt.core.enums.AuctionOrderBy
import com.galarzaa.tibiakt.core.enums.AuctionOrderDirection
import com.galarzaa.tibiakt.core.enums.AuctionSearchType
import com.galarzaa.tibiakt.core.enums.AuctionSkillFilter
import com.galarzaa.tibiakt.core.enums.AuctionVocationFilter
import com.galarzaa.tibiakt.core.enums.PvpType
import com.galarzaa.tibiakt.core.models.bazaar.BazaarFilters
import com.galarzaa.tibiakt.core.utils.BuilderDsl


@BuilderDsl
inline fun bazaarFilters(block: BazaarFiltersBuilder.() -> Unit) = bazaarFiltersBuilder(block).build()

@BuilderDsl
inline fun bazaarFiltersBuilder(block: BazaarFiltersBuilder.() -> Unit) = BazaarFiltersBuilder().apply(block)

@BuilderDsl
class BazaarFiltersBuilder : TibiaKtBuilder<BazaarFilters>() {
    var world: String? = null
    var pvpType: PvpType? = null
    var battlEyeType: AuctionBattlEyeFilter? = null
    var vocation: AuctionVocationFilter? = null
    var minimumLevel: Int? = null
    var maximumLevel: Int? = null
    var skill: AuctionSkillFilter? = null
    var minimumSkillLevel: Int? = null
    var maximumSkillLevel: Int? = null
    var orderDirection: AuctionOrderDirection? = null
    var orderBy: AuctionOrderBy? = null
    var searchString: String? = null
    var searchType: AuctionSearchType? = null

    override fun build() = BazaarFilters(world = world,
        pvpType = pvpType,
        battlEyeType = battlEyeType,
        vocation = vocation,
        minimumLevel = minimumLevel,
        maximumLevel = maximumLevel,
        skill = skill,
        minimumSkillLevel = minimumSkillLevel,
        maximumSkillLevel = maximumSkillLevel,
        searchString = searchString,
        searchType = searchType,
        orderDirection = orderDirection,
        orderBy = orderBy)
}