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

/** An object made of multiple pages with URLs */
public interface PaginatedWithUrl<T> : Paginated<T> {
    /** Get the URL to the next page if there is any. */
    public val nextPageUrl: String?
        get() = if (currentPage == totalPages) null else getPageUrl(currentPage + 1)

    /** Get the URL to the previous page if there is any. */
    public val previousPageUrl: String?
        get() = if (currentPage == 0) null else getPageUrl(currentPage - 1)

    /** Get the URL to a specific page */
    public fun getPageUrl(page: Int): String
}
