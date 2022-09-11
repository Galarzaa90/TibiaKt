package com.galarzaa.tibiakt.core.models.house

import com.galarzaa.tibiakt.core.enums.HouseOrder
import com.galarzaa.tibiakt.core.enums.HouseStatus
import com.galarzaa.tibiakt.core.enums.HouseType
import com.galarzaa.tibiakt.core.utils.getHousesSectionUrl
import kotlinx.serialization.Serializable

/**
 * The Houses Section in Tibia.com
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

    val url: String
        get() = getHousesSectionUrl(world, town, type, status, order)
}
