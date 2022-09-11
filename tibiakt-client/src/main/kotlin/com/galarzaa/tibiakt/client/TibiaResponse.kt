@file:UseSerializers(InstantSerializer::class)

package com.galarzaa.tibiakt.client

import com.galarzaa.tibiakt.core.serializers.InstantSerializer
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers

/**
 * A response from Tibia.com
 *
 * @property timestamp the time when the request was done.
 * @property isCached Whether the response is cached or not.
 * @property cacheAge How old is the cached response, in seconds.
 * @property fetchingTime The time it took to fetch the content from Tibia.com in seconds.
 * @property parsingTime The time it took to parse the content into data in seconds.
 * @property data The data parsed from Tibia.com.
 * @param T The type of the data returned.
 */
@Serializable
public data class TibiaResponse<T>(
    val timestamp: Instant,
    val isCached: Boolean,
    val cacheAge: Int,
    val fetchingTime: Double,
    val parsingTime: Double,
    val data: T,
)
