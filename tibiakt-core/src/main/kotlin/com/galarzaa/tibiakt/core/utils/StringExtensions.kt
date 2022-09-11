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

package com.galarzaa.tibiakt.core.utils

/**
 * Split a string enumerating elements, using a different separator for the last item.
 */
public fun String?.splitList(separator: String = ",", lastSeparator: String = " and "): List<String> {
    val items = this?.nullIfBlank()?.split(separator)?.toMutableList() ?: return emptyList()
    val lastSplit: List<String> = items.last().split(lastSeparator)
    if (lastSplit.size > 1) {
        items[items.lastIndex] = lastSplit.subList(0, lastSplit.lastIndex).joinToString(lastSeparator)
        items.add(lastSplit.last())
    }
    return items.map { it.trim() }
}

/**
 * Remove an arbitrary string from a string, as many times as it is found.
 */
public fun String.remove(value: String, ignoreCase: Boolean = false): String = replace(value, "", ignoreCase)

/**
 * Clean the string of non-breaking spaces and trims whitespace.
 */
public fun String.clean(): String = replace("\u00A0", " ").replace("&#xa0;", " ").trim()

/**
 * Parse a string into an integer, removing any thousand separators.
 */
public fun String.parseInteger(): Int = remove(",").trim().toInt()

/**
 * Parse a string into a long integer, removing any thousand separators.
 */
public fun String.parseLong(): Long = remove(",").trim().toLong()

/**
 * Find and parse an integer from a string, ignoring everything that is not a digit.
 *
 * Note that this may cause unexpected results such as "I have 2 apples and 3 oranges" being converted into 23.
 */
public fun String.findInteger(): Int = filter { it.isDigit() }.toInt()

/**
 * Return null if the string is blank
 */
public fun String?.nullIfBlank(): String? = takeIf { !it.isNullOrBlank() }

/**
 *  Parses strings with numbers using "k" as a thousand suffix
 */
public fun String.parseThousandSuffix(): Int =
    remove("k", true).parseInteger() * (count { it == 'k' || it == 'K' } * 1000)
