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
import com.galarzaa.tibiakt.core.net.forumThreadUrl
import kotlinx.serialization.Serializable

/**
 * A thread in the Tibia forums.
 *
 * @property boardName The name of the board the thread is in.
 * @property boardId The internal ID of the board the thread is in.
 * @property sectionName The name of the section the board is in.
 * @property sectionId The internal ID of the section the board is in.
 * @property previousTopicNumber The ID of the thread before this one.
 * @property nextTopicNumber The ID of the thread after this one.
 * @property goldenFrame Whether the post has a golden frame or not.
 * @property anchoredPost The post that corresponds to the anchor in the URL if any.
 */
@Serializable
public data class ForumThread(
    override val title: String,
    override val threadId: Int,
    val boardName: String,
    val boardId: Int,
    val sectionName: String,
    val sectionId: Int,
    val previousTopicNumber: Int?,
    val nextTopicNumber: Int?,
    val goldenFrame: Boolean,
    val anchoredPost: ForumPost? = null,
    override val currentPage: Int,
    override val totalPages: Int,
    override val resultsCount: Int,
    override val entries: List<ForumPost>,
) : BaseForumThread, PaginatedWithUrl<ForumPost> {

    override val url: String get() = forumThreadUrl(threadId, currentPage)

    /**
     * Get the URL to a specific page in the thread.
     */
    public override fun getPageUrl(page: Int): String = forumThreadUrl(threadId, page)
}


