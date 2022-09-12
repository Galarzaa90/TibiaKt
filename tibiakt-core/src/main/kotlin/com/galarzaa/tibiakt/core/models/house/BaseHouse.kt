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

package com.galarzaa.tibiakt.core.models.house

import com.galarzaa.tibiakt.core.utils.getHouseUrl
import com.galarzaa.tibiakt.core.utils.getStaticFileUrl

/**
 * Base interface for house related classes.
 *
 * @property houseId The internal ID of the house.
 * @property world The world where this house is located.
 */
public interface BaseHouse {
    public val houseId: Int
    public val world: String

    /**
     * The URL to the house's information page.
     */
    public val url: String get() = getHouseUrl(world, houseId)

    /**
     * URL to the house's image.
     */
    public val imageUrl: String get() = getStaticFileUrl("images", "houses", "house_$houseId.png")
}
