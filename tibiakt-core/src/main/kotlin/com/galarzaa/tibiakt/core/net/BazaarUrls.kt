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

package com.galarzaa.tibiakt.core.net

import com.galarzaa.tibiakt.core.enums.AuctionBattlEyeFilter
import com.galarzaa.tibiakt.core.enums.AuctionOrderBy
import com.galarzaa.tibiakt.core.enums.AuctionOrderDirection
import com.galarzaa.tibiakt.core.enums.AuctionSearchType
import com.galarzaa.tibiakt.core.enums.AuctionSkillFilter
import com.galarzaa.tibiakt.core.enums.AuctionVocationFilter
import com.galarzaa.tibiakt.core.enums.BazaarType
import com.galarzaa.tibiakt.core.enums.PvpType
import com.galarzaa.tibiakt.core.models.bazaar.BazaarFilters

/**
 * URL to the character bazaar.
 *
 * @param type Whether to show current auctions or the auction history.
 */
public fun bazaarUrl(type: BazaarType = BazaarType.CURRENT, filters: BazaarFilters? = null, page: Int = 1): String =
    tibiaUrl("charactertrade", type.subtopic, "currentpage" to page, *filters.queryParams())


private fun BazaarFilters?.queryParams(): Array<Pair<String, Any?>> {
    return if (this != null) arrayOf(
        "filter_world" to world,
        AuctionVocationFilter.QUERY_PARAM to vocation?.value,
        "filter_levelrangefrom" to minimumLevel,
        "filter_levelrangeto" to maximumLevel,
        PvpType.QUERY_PARAM_BAZAAR to pvpType?.bazaarFilterValue,
        AuctionBattlEyeFilter.QUERY_PARAM to battlEyeType?.value,
        AuctionSkillFilter.QUERY_PARAM to skill?.value,
        "filter_skillrangefrom" to minimumSkillLevel,
        "filter_skillrangeto" to maximumSkillLevel,
        AuctionOrderBy.QUERY_PARAM to orderBy?.value,
        AuctionOrderDirection.QUERY_PARAM to orderDirection?.value,
        "searchstring" to searchString,
        AuctionSearchType.queryParam to searchType?.value,
    ) else emptyArray()
}

/**
 * URL to a specific auction.
 */
public fun auctionUrl(auctionId: Int): String =
    tibiaUrl("charactertrade", "currentcharactertrades", "page" to "details", "auctionid" to auctionId)
