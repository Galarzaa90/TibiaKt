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
import com.galarzaa.tibiakt.core.section.charactertrade.bazaar.model.Auction
import com.galarzaa.tibiakt.core.section.charactertrade.bazaar.model.BazaarFilters
import com.galarzaa.tibiakt.core.section.charactertrade.bazaar.model.BazaarType
import com.galarzaa.tibiakt.core.section.charactertrade.bazaar.model.CharacterBazaar

@BuilderDsl
internal inline fun characterBazaar(block: CharacterBazaarBuilder.() -> Unit): CharacterBazaar =
    characterBazaarBuilder(block).build()

@BuilderDsl
internal inline fun characterBazaarBuilder(block: CharacterBazaarBuilder.() -> Unit): CharacterBazaarBuilder =
    CharacterBazaarBuilder().apply(block)

/** Builder for [CharacterBazaar] instances. */
internal class CharacterBazaarBuilder : TibiaKtBuilder<CharacterBazaar> {
    var type: BazaarType = BazaarType.CURRENT
    var currentPage: Int = 0
    var totalPages: Int = 1
    var resultsCount: Int = 0
    var filters: BazaarFilters = BazaarFilters()
    private val entries: MutableList<Auction> = mutableListOf()


    fun addEntry(entry: Auction): CharacterBazaarBuilder = apply { entries.add(entry) }

    @BuilderDsl
    fun auction(block: AuctionBuilder.() -> Unit): CharacterBazaarBuilder =
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
