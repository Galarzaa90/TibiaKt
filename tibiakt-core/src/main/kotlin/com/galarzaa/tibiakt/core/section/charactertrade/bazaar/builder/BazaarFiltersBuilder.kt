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

package com.galarzaa.tibiakt.core.section.charactertrade.bazaar.builder

import com.galarzaa.tibiakt.core.builder.BuilderDsl
import com.galarzaa.tibiakt.core.builder.TibiaKtBuilder
import com.galarzaa.tibiakt.core.domain.world.PvpType
import com.galarzaa.tibiakt.core.section.charactertrade.bazaar.model.AuctionBattlEyeFilter
import com.galarzaa.tibiakt.core.section.charactertrade.bazaar.model.AuctionOrderBy
import com.galarzaa.tibiakt.core.section.charactertrade.bazaar.model.AuctionOrderDirection
import com.galarzaa.tibiakt.core.section.charactertrade.bazaar.model.AuctionSearchType
import com.galarzaa.tibiakt.core.section.charactertrade.bazaar.model.AuctionSkillFilter
import com.galarzaa.tibiakt.core.section.charactertrade.bazaar.model.AuctionVocationFilter
import com.galarzaa.tibiakt.core.section.charactertrade.bazaar.model.BazaarFilters

@BuilderDsl
internal inline fun bazaarFilters(block: BazaarFiltersBuilder.() -> Unit): BazaarFilters =
    bazaarFiltersBuilder(block).build()

@BuilderDsl
internal inline fun bazaarFiltersBuilder(block: BazaarFiltersBuilder.() -> Unit): BazaarFiltersBuilder =
    BazaarFiltersBuilder().apply(block)

/** Builder for [BazaarFilters] instances. */
@BuilderDsl
internal class BazaarFiltersBuilder : TibiaKtBuilder<BazaarFilters> {
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

    override fun build(): BazaarFilters = BazaarFilters(
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
