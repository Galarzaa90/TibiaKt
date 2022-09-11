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

package com.galarzaa.tibiakt.core.models.news

import com.galarzaa.tibiakt.core.enums.NewsCategory
import com.galarzaa.tibiakt.core.utils.getNewsUrl

/**
 * Base interface for news related objects.
 *
 * @property id The internal ID of the news entry.
 * @property category The category of the entry.
 */
public interface BaseNews {
    public val id: Int
    public val category: NewsCategory

    /**
     * The URL to this article.
     */
    public val url: String get() = getNewsUrl(id)
}
