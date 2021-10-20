package com.galarzaa.tibiakt.core.builders

import com.galarzaa.tibiakt.core.enums.*
import com.galarzaa.tibiakt.core.models.bazaar.BazaarFilters

class BazaarFiltersBuilder {
    private var world: String? = null
    private var pvpType: AuctionPvpTypeFilter? = null
    private var battlEyeType: AuctionBattlEyeFilter? = null
    private var vocation: AuctionVocationFilter? = null
    private var minimumLevel: Int? = null
    private var maximumLevel: Int? = null
    private var skill: AuctionSkillFilter? = null
    private var minimumSkillLevel: Int? = null
    private var maximumSkillLevel: Int? = null
    private var orderDirection: AuctionOrderDirection? = null
    private var orderBy: AuctionOrderBy? = null
    private var searchString: String? = null
    private var searchType: AuctionSearchType? = null

    fun world(world: String?) = apply { this.world = world }
    fun pvpType(pvpType: AuctionPvpTypeFilter?) = apply { this.pvpType = pvpType }
    fun battlEyeType(battlEyeType: AuctionBattlEyeFilter?) = apply { this.battlEyeType = battlEyeType }
    fun vocation(vocation: AuctionVocationFilter?) = apply { this.vocation = vocation }
    fun minimumLevel(minimumLevel: Int?) = apply { this.minimumLevel = minimumLevel }
    fun maximumLevel(maximumLevel: Int?) = apply { this.maximumLevel = maximumLevel }
    fun skill(skill: AuctionSkillFilter?) = apply { this.skill = skill }
    fun minimumSkillLevel(minimumSkillLevel: Int?) = apply { this.minimumSkillLevel = minimumSkillLevel }
    fun maximumSkillLevel(maximumSkillLevel: Int?) = apply { this.maximumSkillLevel = maximumSkillLevel }
    fun orderDirection(orderDirection: AuctionOrderDirection?) = apply { this.orderDirection = orderDirection }
    fun orderBy(orderBy: AuctionOrderBy?) = apply { this.orderBy = orderBy }
    fun searchString(searchString: String?) = apply { this.searchString = searchString }
    fun searchType(searchType: AuctionSearchType?) = apply { this.searchType = searchType }

    fun build() = BazaarFilters(
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
        orderBy = orderBy
    )
}