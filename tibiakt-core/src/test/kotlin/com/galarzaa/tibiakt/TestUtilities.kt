/*
 * Copyright © 2022 Allan Galarza
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

package com.galarzaa.tibiakt

import java.io.IOException
import kotlin.time.Clock
import kotlin.time.Instant

object TestUtilities {

    fun getResource(path: String): String {
        return this::class.java.getResource("/$path")?.readText() ?: throw IOException("Test resource $path not found")
    }

    fun parseQueryParams(url: String): Map<String, String> {
        val query = url.substringAfter('?', "").substringBefore('#')
        if (query.isBlank()) return emptyMap()

        return query.split('&').mapNotNull {
                if (it.isBlank()) return@mapNotNull null
                val (key, value) = it.split('=', limit = 2).let { parts ->
                    parts[0] to (parts.getOrNull(1) ?: "")
                }
                key to java.net.URLDecoder.decode(value, Charsets.UTF_8)
            }.toMap()
    }


    class FakeClock(private val fixedTime: Instant) : Clock {
        override fun now() = fixedTime
    }
}
