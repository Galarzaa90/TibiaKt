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

package com.galarzaa.tibiakt.core.section.news.shared.model

import com.galarzaa.tibiakt.core.enums.StringEnum
import com.galarzaa.tibiakt.core.net.staticFileUrl

/**
 * The available news categories.
 *
 * @property value The internal value, used in parameters for filtering, and image paths.
 * @property displayName The display name used on Tibia.com.
 */
public enum class NewsCategory(override val value: String, public val displayName: String) : StringEnum {
    CIPSOFT("cipsoft", "CipSoft"),
    COMMUNITY("community", "Community"),
    DEVELOPMENT("development", "Development"),
    SUPPORT("support", "Support"),
    TECHNICAL_ISSUES("technical", "Technical Issues");

    /**
     * The name of query parameter used to set this filter.
     */
    public val filterName: String
        get() = "filter_$value"

    /**
     * A URL to the big icon version of this category.
     */
    public val bigIconUrl: String get() = staticFileUrl("images", "global", "content", "newsicon_${value}_big.png")

    /**
     * A URL to the small icon version of this category.
     */
    public val smallIconUrl: String
        get() = staticFileUrl(
            "images",
            "global",
            "content",
            "newsicon_${value}_small.png"
        )

    public companion object {
        private val iconRegex = Regex("""newsicon_([^_]+)_(?:small|big)""")

        /**
         * Get the category value from an icon's URL.
         */
        public fun fromIcon(iconUrl: String): NewsCategory? {
            iconRegex.find(iconUrl)?.apply {
                return StringEnum.Companion.fromValue(this.groups[1]!!.value.lowercase())
            }
            return null
        }
    }
}
