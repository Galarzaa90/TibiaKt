/*
 * Copyright © 2025 Allan Galarza
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

package com.galarzaa.tibiakt.core.net

import java.net.URLEncoder
import java.nio.charset.StandardCharsets

private const val BASE_HOST = "www.tibia.com"
private const val TEST_HOST = "www.test.tibia.com"
private const val STATIC_HOST = "static.tibia.com"
private val LATIN1 = StandardCharsets.ISO_8859_1


/**
 * Build a URL to Tibia.com.
 *
 * @param section The desired section.
 * @param params The query arguments to pass.
 * @param test Whether to get a URL to the testing version of Tibia.com.
 * @param anchor The anchor to append to the URL.
 */
public fun tibiaUrl(
    section: String,
    vararg params: Pair<String, Any?>,
    test: Boolean = false,
    anchor: String? = null,
): String {
     val host = if (test) TEST_HOST else BASE_HOST

    val query = params.asSequence()
        .filter { ( _, v ) -> v != null }
        .map { (k, v) -> "$k=${URLEncoder.encode(v.toString(), LATIN1)}" }
        .joinToString("&")

    return buildString {
        append("https://$host/${section.trimEnd('/')}/")
        if (query.isNotEmpty()) append('?').append(query)
        if (!anchor.isNullOrEmpty()) append('#').append(anchor)
    }
}

/**
 * Build a URL to Tibia.com.
 *
 * @param section The desired section.
 * @param subtopic The desired subtopic.
 * @param params The query arguments to pass.
 * @param test Whether to get a URL to the testing version of Tibia.com.
 * @param anchor The anchor to append to the URL.
 */
public fun tibiaUrl(
    section: String,
    subtopic: String,
    vararg params: Pair<String, Any?>,
    test: Boolean = false,
    anchor: String? = null,
): String {
    val newParams = mutableListOf(*params)
    newParams.add(0, "subtopic" to subtopic)
    return tibiaUrl(section, params = newParams.toTypedArray(), test = test, anchor = anchor)
}

/**
 * Get the URL of a static asset in Tibia.com.
 *
 * @param path The path to the asset.
 * @param test Whether to use the testing version of Tibia.com or not.
 */
public fun staticFileUrl(path: String, test: Boolean = false): String =
    "https://${if (test) "test." else ""}$STATIC_HOST/${path.replace("//", "/")}"

/**
 * Get the URL of a static asset in Tibia.com.
 *
 * @param path The path to the asset, represented as an array of directories with the filename at the end.
 * @param test Whether to use the testing version of Tibia.com or not.
 */
public fun staticFileUrl(vararg path: String, test: Boolean = false): String =
    staticFileUrl(path.joinToString("/"), test)


