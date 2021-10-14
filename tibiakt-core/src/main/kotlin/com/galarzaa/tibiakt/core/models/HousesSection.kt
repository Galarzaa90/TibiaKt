package com.galarzaa.tibiakt.core.models

import com.galarzaa.tibiakt.core.enums.HouseOrder
import com.galarzaa.tibiakt.core.enums.HouseStatus
import com.galarzaa.tibiakt.core.enums.HouseType
import com.galarzaa.tibiakt.core.utils.getHousesSectionUrl
import kotlinx.serialization.Serializable

/**
 * The Houses Section in Tibia.com
 *
 * @world The world of the houses.
 * @town The town of the houses.
 * @status The status filter used for the search, if null, houses of any status will be shown.
 * @type The type of houses to display.
 * @order The ordering to use.
 * @entries The list of houses matching the provided filters.
 */
@Serializable
data class HousesSection(
    val world: String,
    val town: String,
    val status: HouseStatus?,
    val type: HouseType,
    val order: HouseOrder,
    val entries: List<HouseEntry>,
)

val HousesSection.url
    get() = getHousesSectionUrl(world, town, type, status, order)