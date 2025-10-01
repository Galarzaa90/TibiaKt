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

import com.galarzaa.tibiakt.core.net.staticFileUrl
import kotlinx.serialization.Serializable

/**
 * The outfits of a character in an [Auction].
 */
@Serializable
public data class Outfits(
    override val currentPage: Int,
    override val totalPages: Int,
    override val resultsCount: Int,
    override val entries: List<OutfitEntry>,
    override val isFullyFetched: Boolean,
) : AjaxPaginator<OutfitEntry>


/**
 * A base outfit displayed in auctions.
 *
 * @property outfitId The internal ID of the outfit.
 * @property addons The selected or unlocked addons.
 */
public interface BaseOutfit {
    public val outfitId: Int
    public val addons: Int

    /**
     * The URL to the outfit's image.
     */
    public val imageUrl: String
        get() = staticFileUrl(
            "images", "charactertrade", "outfits", "${outfitId}_$addons.gif"
        )
}

/**
 * The currently selected outfit of a character in an [Auction].
 */
@Serializable
public data class OutfitImage(
    override val outfitId: Int,
    override val addons: Int,
) : BaseOutfit

/**
 * An outfit owned or unlocked by a character in an [Auction].
 *
 * @property name The name of the outfit.
 */
@Serializable
public data class OutfitEntry(
    val name: String,
    override val outfitId: Int,
    override val addons: Int,
) : BaseOutfit
