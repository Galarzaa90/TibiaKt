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
public inline fun bazaarFilters(block: BazaarFiltersBuilder.() -> Unit): BazaarFilters =
    bazaarFiltersBuilder(block).build()

@BuilderDsl
public inline fun bazaarFiltersBuilder(block: BazaarFiltersBuilder.() -> Unit): BazaarFiltersBuilder =
    BazaarFiltersBuilder().apply(block)

@BuilderDsl
public class BazaarFiltersBuilder : TibiaKtBuilder<BazaarFilters>() {
    public var world: String? = null
    public var pvpType: PvpType? = null
    public var battlEyeType: AuctionBattlEyeFilter? = null
    public var vocation: AuctionVocationFilter? = null
    public var minimumLevel: Int? = null
    public var maximumLevel: Int? = null
    public var skill: AuctionSkillFilter? = null
    public var minimumSkillLevel: Int? = null
    public var maximumSkillLevel: Int? = null
    public var orderDirection: AuctionOrderDirection? = null
    public var orderBy: AuctionOrderBy? = null
    public var searchString: String? = null
    public var searchType: AuctionSearchType? = null

    public override fun build(): BazaarFilters = BazaarFilters(
        world = world,
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
