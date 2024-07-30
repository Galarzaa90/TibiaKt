/*
 * Copyright © 2024 Allan Galarza
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

import com.galarzaa.tibiakt.core.utils.getStaticFileUrl
import kotlinx.serialization.Serializable

/**
 * An in-game item in an [Auction].
 *
 * @property itemId The internal ID of the item.
 * @property name The name of the item.
 * @property description The description or flavor text of the item, if any.
 * @property count The amount of this item the character has.
 * @property tier The upgrade tier of the item.
 */
@Serializable
public data class ItemEntry(
    val itemId: Int,
    val name: String,
    val description: String?,
    val count: Int,
    val tier: Int,
) {
    /**
     * The URL to the item's image.
     */
    val imageUrl: String get() = getStaticFileUrl("images", "charactertrade", "objects", "$itemId.gif")
}
