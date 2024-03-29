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

package com.galarzaa.tibiakt.core.models

/**
 * An object made of multiple pages.
 *
 * @property currentPage The currently viewed page.
 * @property totalPages The total number of pages.
 * @property resultsCount The total number of [entries] across all pages.
 * @property entries The entries in this page.
 */
public interface Paginated<T> {
    public val currentPage: Int
    public val totalPages: Int
    public val resultsCount: Int
    public val entries: List<T>
}
