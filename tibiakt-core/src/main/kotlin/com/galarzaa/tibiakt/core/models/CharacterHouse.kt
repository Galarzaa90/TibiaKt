@file:UseSerializers(LocalDateSerializer::class)

package com.galarzaa.tibiakt.core.models

import com.galarzaa.tibiakt.core.utils.LocalDateSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.time.LocalDate

@Serializable
data class CharacterHouse(
    val name: String,
    override val houseId: Int,
    val town: String,
    val paidUntil: LocalDate,
    override val world: String,
) : BaseHouse