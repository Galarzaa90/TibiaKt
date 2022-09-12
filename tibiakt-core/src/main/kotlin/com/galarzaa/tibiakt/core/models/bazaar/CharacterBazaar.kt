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

import com.galarzaa.tibiakt.core.enums.BazaarType
import com.galarzaa.tibiakt.core.models.PaginatedWithUrl
import com.galarzaa.tibiakt.core.utils.getBazaarUrl
import kotlinx.serialization.Serializable

/**
 * The Character Bazaar.
 *
 * @property type The type of bazaar, current auctions or auction history.
 * @property filters The currently active filters for the bazaar.
 */
@Serializable
public data class CharacterBazaar(
    val type: BazaarType,
    val filters: BazaarFilters = BazaarFilters(),
    override val currentPage: Int,
    override val totalPages: Int,
    override val resultsCount: Int,
    override val entries: List<Auction>,
) : PaginatedWithUrl<Auction> {
    /**
     * The URL to the bazaar with the current filters and page.
     */
    val url: String get() = getBazaarUrl(type, filters, currentPage)
    override fun getPageUrl(page: Int): String = getBazaarUrl(type, filters, page)
}
