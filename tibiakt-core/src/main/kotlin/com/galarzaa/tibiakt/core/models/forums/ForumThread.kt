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

package com.galarzaa.tibiakt.core.models.forums

import com.galarzaa.tibiakt.core.models.PaginatedWithUrl
import com.galarzaa.tibiakt.core.utils.getForumThreadUrl
import kotlinx.serialization.Serializable

/** A thread in the Tibia forums. */
@Serializable
public data class ForumThread(
    val title: String,
    val threadId: Int,
    val board: String,
    val boardId: Int,
    val section: String,
    val sectionId: Int,
    val previousTopicNumber: Int?,
    val nextTopicNumber: Int?,
    val goldenFrame: Boolean,
    val anchoredPost: ForumPost? = null,
    override val currentPage: Int,
    override val totalPages: Int,
    override val resultsCount: Int,
    override val entries: List<ForumPost>,
) : PaginatedWithUrl<ForumPost> {

    val url: String get() = getForumThreadUrl(threadId, currentPage)
    override fun getPageUrl(page: Int): String = getForumThreadUrl(threadId, page)
}
