@file:UseSerializers(InstantSerializer::class)

package com.galarzaa.tibiakt.models

import com.galarzaa.tibiakt.InstantSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.time.Instant

@Serializable
data class TibiaResponse<T>(
    val timestamp: Instant,
    val isCached: Boolean,
    val cacheAge: Int,
    val fetchingTime: Float,
    val parsingTime: Float,
    val data: T?
)
