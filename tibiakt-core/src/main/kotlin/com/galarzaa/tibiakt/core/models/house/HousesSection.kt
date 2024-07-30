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

package com.galarzaa.tibiakt.core.models.house

import com.galarzaa.tibiakt.core.enums.HouseOrder
import com.galarzaa.tibiakt.core.enums.HouseStatus
import com.galarzaa.tibiakt.core.enums.HouseType
import com.galarzaa.tibiakt.core.utils.getHousesSectionUrl
import kotlinx.serialization.Serializable

/**
 * The Houses Section in Tibia.com.
 *
 * @property world The world of the houses.
 * @property town The town of the houses.
 * @property status The status filter used for the search, if null, houses of any status will be shown.
 * @property type The type of houses to display.
 * @property order The ordering to use.
 * @property entries The list of houses matching the provided filters.
 */
@Serializable
public data class HousesSection(
    val world: String,
    val town: String,
    val status: HouseStatus?,
    val type: HouseType,
    val order: HouseOrder,
    val entries: List<HouseEntry>,
) {

    /**
     * The URL to the Houses section, with the selected filters.
     */
    val url: String
        get() = getHousesSectionUrl(world, town, type, status, order)
}
