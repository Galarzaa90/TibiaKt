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


package com.galarzaa.tibiakt.core.models.forums

import com.galarzaa.tibiakt.core.models.PaginatedWithUrl
import com.galarzaa.tibiakt.core.utils.getCMPostArchiveUrl
import kotlinx.serialization.Serializable
import kotlinx.datetime.LocalDate

/**
 * The CM Post Archive, displaying recent posts by Community Managers.
 *
 * @property startDate The start date for the results.
 * @property endDate The end date for the results.
 */
@Serializable
public data class CMPostArchive(
    val startDate: LocalDate,
    val endDate: LocalDate,
    override val currentPage: Int,
    override val totalPages: Int,
    override val resultsCount: Int,
    override val entries: List<CMPost>,
) : PaginatedWithUrl<CMPost> {

    /**
     * The URL to the CM Post archive with the current filters.
     */
    val url: String get() = getCMPostArchiveUrl(startDate, endDate, currentPage)

    override fun getPageUrl(page: Int): String = getCMPostArchiveUrl(startDate, endDate, page)
}
