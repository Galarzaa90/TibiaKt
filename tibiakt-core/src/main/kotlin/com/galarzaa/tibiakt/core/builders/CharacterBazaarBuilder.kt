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

import com.galarzaa.tibiakt.core.enums.BazaarType
import com.galarzaa.tibiakt.core.models.bazaar.Auction
import com.galarzaa.tibiakt.core.models.bazaar.BazaarFilters
import com.galarzaa.tibiakt.core.models.bazaar.CharacterBazaar
import com.galarzaa.tibiakt.core.utils.BuilderDsl


@BuilderDsl
public inline fun characterBazaar(block: CharacterBazaarBuilder.() -> Unit): CharacterBazaar =
    characterBazaarBuilder(block).build()

@BuilderDsl
public inline fun characterBazaarBuilder(block: CharacterBazaarBuilder.() -> Unit): CharacterBazaarBuilder =
    CharacterBazaarBuilder().apply(block)

public class CharacterBazaarBuilder : TibiaKtBuilder<CharacterBazaar> {
    public var type: BazaarType = BazaarType.CURRENT
    public var currentPage: Int = 0
    public var totalPages: Int = 1
    public var resultsCount: Int = 0
    public var filters: BazaarFilters = BazaarFilters()
    private val entries: MutableList<Auction> = mutableListOf()


    public fun addEntry(entry: Auction): CharacterBazaarBuilder = apply { entries.add(entry) }

    @BuilderDsl
    public fun auction(block: AuctionBuilder.() -> Unit): CharacterBazaarBuilder =
        apply { entries.add(AuctionBuilder().apply(block).build()) }

    override fun build(): CharacterBazaar = CharacterBazaar(
        type = type,
        filters = filters,
        currentPage = currentPage,
        totalPages = totalPages,
        resultsCount = resultsCount,
        entries = entries,
    )
}
