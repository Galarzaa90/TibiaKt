/*
 * Copyright © 2022 Allan Galarza
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

package com.galarzaa.tibiakt.core.models.bazaar

import com.galarzaa.tibiakt.core.enums.AuctionBattlEyeFilter
import com.galarzaa.tibiakt.core.enums.AuctionOrderBy
import com.galarzaa.tibiakt.core.enums.AuctionOrderDirection
import com.galarzaa.tibiakt.core.enums.AuctionSearchType
import com.galarzaa.tibiakt.core.enums.AuctionSkillFilter
import com.galarzaa.tibiakt.core.enums.AuctionVocationFilter
import com.galarzaa.tibiakt.core.enums.PvpType
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
public data class BazaarFilters(
    val world: String? = null,
    val pvpType: PvpType? = null,
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
