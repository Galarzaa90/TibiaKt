@file:UseSerializers(InstantSerializer::class)

package com.galarzaa.tibiakt.models

import com.galarzaa.tibiakt.InstantSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.time.Instant

@Serializable
data class AccountInformation(
    val creation: Instant,
    val loyaltyTitle: String?,
    val position: String? = null,
    val tutorStars: Int? = null
)