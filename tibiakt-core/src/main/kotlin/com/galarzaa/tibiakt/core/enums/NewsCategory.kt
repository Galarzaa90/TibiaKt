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

package com.galarzaa.tibiakt.core.enums

import com.galarzaa.tibiakt.core.utils.getStaticFileUrl

/**
 * The available news categories.
 */
public enum class NewsCategory(override val value: String) : StringEnum {
    CIPSOFT("cipsoft"),
    COMMUNITY("community"),
    DEVELOPMENT("development"),
    SUPPORT("support"),
    TECHNICAL_ISSUES("technical");

    public val filterName: String
        get() = "filter_$value"

    public val bigIconUrl: String get() = getStaticFileUrl("images", "global", "content", "newsicon_${value}_big.gif")
    public val smallIconUrl: String
        get() = getStaticFileUrl(
            "images",
            "global",
            "content",
            "newsicon_${value}_small.gif"
        )

    public companion object {
        private val iconRegex = Regex("""newsicon_([^_]+)_(?:small|big)""")
        public fun fromIcon(iconUrl: String): NewsCategory? {
            iconRegex.find(iconUrl)?.apply {
                return StringEnum.fromValue(this.groups[1]!!.value.lowercase())
            }
            return null
        }
    }
}
