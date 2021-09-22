@file:UseSerializers(LocalDateSerializer::class)

package com.galarzaa.tibiakt.models

import com.galarzaa.tibiakt.LocalDateSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.time.LocalDate

@Serializable
data class GuildHall(
    val name: String,
    val town: String,
    val paidUntil: LocalDate,
    val world: String,
)
