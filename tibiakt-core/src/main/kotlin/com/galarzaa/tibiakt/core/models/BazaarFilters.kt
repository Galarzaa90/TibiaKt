package com.galarzaa.tibiakt.core.models

import com.galarzaa.tibiakt.core.enums.*
import kotlinx.serialization.Serializable

@Serializable
data class BazaarFilters(
    val world: String? = null,
    val pvpType: AuctionPvpTypeFilter? = null,
    val battlEyeType: AuctionBattlEyeFilter? = null,
    val vocation: AuctionVocationFilter? = null,
    val minimumLevel: Int? = null,
    val maximumLevel: Int? = null,
    val skill: AuctionSkillFilter? = null,
    val minimumSkillLevel: Int? = null,
    val maximumSkillLevel: Int? = null,
    val order: AuctionOrder? = null,
    val orderBy: AuctionOrderBy? = null,
    val searchString: String? = null,
    val searchType: AuctionSearchType? = null,
)