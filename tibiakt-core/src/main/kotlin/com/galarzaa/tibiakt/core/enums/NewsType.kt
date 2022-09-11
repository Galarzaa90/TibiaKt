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

/** The available types of news. */
public enum class NewsType(override val value: String) : StringEnum {
    NEWS("News"),
    NEWS_TICKER("News Ticker"),
    FEATURED_ARTICLE("Featured Article");

    public val filterName: String
        get() = "filter_$filterValue"

    public val filterValue: String
        get() = value.split(" ").last().lowercase()
}
