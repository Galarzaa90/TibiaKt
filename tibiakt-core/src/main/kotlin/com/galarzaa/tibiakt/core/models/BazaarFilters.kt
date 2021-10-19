package com.galarzaa.tibiakt.core.models

import com.galarzaa.tibiakt.core.enums.*
import kotlinx.serialization.Serializable

/**
 * Filtering parameters for the [CharacterBazaar]
 *
 * All values are optional, only set values are considered when filtering.
 *
 * @property world Only show auctions of characters of this world.
 * @property pvpType Only show auctions of characters in worlds with this PvP type.
 * @property battlEyeType Only show auctions of characters in worlds with this BattlEye type.
 * @property vocation Only show auctions of characters of this vocation, including promoted vocations.
 * @property minimumLevel Only show characters above this level.
 * @property maximumLevel Only show characters below this level.
 * @property skill The skill to use for filtering by [minimumSkillLevel] and [maximumSkillLevel].
 * @property minimumSkillLevel Only show characters with a level on the selected [skill] above this.
 * @property maximumSkillLevel Only show characters with a level on the selected [skill] below this.
 * @property orderDirection The ordering direction for the results.
 * @property orderBy The column or value to order by.
 * @property searchString The string used to search based on the [searchType].
 * @property searchType The type of search to perform.
 */
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
    val orderDirection: AuctionOrderDirection? = null,
    val orderBy: AuctionOrderBy? = null,
    val searchString: String? = null,
    val searchType: AuctionSearchType? = null,
)