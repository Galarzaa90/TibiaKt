@file:UseSerializers(LocalDateSerializer::class)

package com.galarzaa.tibiakt.core.models.guild

import com.galarzaa.tibiakt.core.serializers.LocalDateSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.time.LocalDate

/** A guildhall owned by a [Guild]
 *
 * @property name The name of the guildhall.
 * @property paidUntil The date when the last paid rent is due.
 */
@Serializable
public data class GuildHall(
    val name: String,
    val paidUntil: LocalDate,
)
