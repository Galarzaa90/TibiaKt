@file:UseSerializers(InstantSerializer::class)

package com.galarzaa.tibiakt.client.models

import com.galarzaa.tibiakt.core.InstantSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.time.Instant

/**
 * A response from Tibia.com
 *
 * @property timestamp the time when the request was done.
 * @property isCached Whether the response is cached or not.
 * @property cacheAge How old is the cached response, in seconds.
 * @property fetchingTime The time it took to fetch the content from Tibia.com in seconds.
 * @property parsingTime The time it took to parse the content into data in seconds.
 * @property data The data parsed from Tibia.com.
 * @property T The type of the data returned.
 */
@Serializable
data class TibiaResponse<T>(
    val timestamp: Instant,
    val isCached: Boolean,
    val cacheAge: Int,
    val fetchingTime: Float,
    val parsingTime: Float,
    val data: T
)
