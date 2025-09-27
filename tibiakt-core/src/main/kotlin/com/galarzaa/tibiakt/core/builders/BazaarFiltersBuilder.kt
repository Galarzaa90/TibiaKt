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

package com.galarzaa.tibiakt.core.builders

import com.galarzaa.tibiakt.core.enums.AuctionBattlEyeFilter
import com.galarzaa.tibiakt.core.enums.AuctionOrderBy
import com.galarzaa.tibiakt.core.enums.AuctionOrderDirection
import com.galarzaa.tibiakt.core.enums.AuctionSearchType
import com.galarzaa.tibiakt.core.enums.AuctionSkillFilter
import com.galarzaa.tibiakt.core.enums.AuctionVocationFilter
import com.galarzaa.tibiakt.core.enums.PvpType
import com.galarzaa.tibiakt.core.models.bazaar.BazaarFilters
import com.galarzaa.tibiakt.core.builders.BuilderDsl

@BuilderDsl
public inline fun bazaarFilters(block: BazaarFiltersBuilder.() -> Unit): BazaarFilters =
    bazaarFiltersBuilder(block).build()

@BuilderDsl
public inline fun bazaarFiltersBuilder(block: BazaarFiltersBuilder.() -> Unit): BazaarFiltersBuilder =
    BazaarFiltersBuilder().apply(block)

/** Builder for [BazaarFilters] instances. */
@BuilderDsl
public class BazaarFiltersBuilder : TibiaKtBuilder<BazaarFilters> {
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
