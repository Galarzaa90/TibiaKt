@file:UseSerializers(LocalDateSerializer::class)

package com.galarzaa.tibiakt.core.models.character

import com.galarzaa.tibiakt.core.models.house.BaseHouse
import com.galarzaa.tibiakt.core.serializers.LocalDateSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.time.LocalDate

/**
 * A house owned by a [Character].
 *
 * @property name The name of the house.
 * @property town The town where the city is or is closest to.
 * @property paidUntil The date when the last paid rent is due.
 */
@Serializable
public data class CharacterHouse(
    val name: String,
    override val houseId: Int,
    val town: String,
    val paidUntil: LocalDate,
    override val world: String,
) : BaseHouse
