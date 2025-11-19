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


package com.galarzaa.tibiakt.core.section.forum.cmpost.model

import com.galarzaa.tibiakt.core.pagination.PaginatedWithUrl
import com.galarzaa.tibiakt.core.section.forum.urls.cmPostArchiveUrl
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

/**
 * The CM Post Archive, displaying recent posts by Community Managers.
 *
 * @property startOn The start date for the results.
 * @property endOn The end date for the results.
 */
@Serializable
public data class CMPostArchive(
    val startOn: LocalDate,
    val endOn: LocalDate,
    override val currentPage: Int,
    override val totalPages: Int,
    override val resultsCount: Int,
    override val entries: List<CMPost>,
) : PaginatedWithUrl<CMPost> {

    /**
     * The URL to the CM Post archive with the current filters.
     */
    val url: String get() = cmPostArchiveUrl(startOn, endOn, currentPage)

    override fun getPageUrl(page: Int): String = cmPostArchiveUrl(startOn, endOn, page)
}
