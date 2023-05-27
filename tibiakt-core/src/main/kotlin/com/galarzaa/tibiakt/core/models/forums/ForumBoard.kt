/*
 * Copyright © 2023 Allan Galarza
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
import com.galarzaa.tibiakt.core.utils.getForumBoardUrl
import kotlinx.serialization.Serializable

public const val DEFAULT_THREAD_AGE: Int = 30

/** A board in the Tibia forums. */
@Serializable
public data class ForumBoard(
    val name: String,
    override val boardId: Int,
    val sectionId: Int,
    val section: String,
    val threadAge: Int,
    val announcements: List<AnnouncementEntry>,
    override val currentPage: Int,
    override val totalPages: Int,
    override val resultsCount: Int,
    override val entries: List<ThreadEntry>,
) : PaginatedWithUrl<ThreadEntry>, BaseForumBoard {

    override val url: String get() = getForumBoardUrl(boardId, currentPage, threadAge)
    override fun getPageUrl(page: Int): String = getForumBoardUrl(boardId, page, threadAge)
}
