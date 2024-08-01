/*
 * Copyright © 2024 Allan Galarza
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */



package com.galarzaa.tibiakt.client

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

/**
 * A response from Tibia.com.
 *
 * @property timestamp the time when the request was done.
 * @property isCached Whether the current data is cached or not.
 * @property cacheAge How old is the cached response, in seconds.
 * @property isCachingEnabled Whether the response has caching enabled or not.
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
    val isCachingEnabled: Boolean,
    val fetchingTime: Double,
    val parsingTime: Double,
    val data: T,
)
