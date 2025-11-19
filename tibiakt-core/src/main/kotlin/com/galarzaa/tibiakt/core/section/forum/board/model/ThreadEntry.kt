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

package com.galarzaa.tibiakt.core.section.forum.board.model

import com.galarzaa.tibiakt.core.section.forum.shared.model.BaseForumThread
import com.galarzaa.tibiakt.core.section.forum.shared.model.ForumEmoticon
import com.galarzaa.tibiakt.core.section.forum.shared.model.LastPost
import com.galarzaa.tibiakt.core.section.news.shared.model.ThreadStatus
import kotlinx.serialization.Serializable

/**
 * A thread entry in the forums.
 *
 * @property authorName The name of the author that created the thread.
 * @property isAuthorTraded Whether the author's character was traded after the thread was created.
 * @property isAuthorDeleted Whether the thread author is now deleted.
 * @property emoticon The selected emoticon for the thread.
 * @property repliesCount The total number of replies the thread has.
 * @property viewsCount The total number of views the thread has.
 * @property lastPost Brief details of the last post.
 * @property status The status the thread has.
 * @property pages The total number of pages the thread has.
 * @property hasGoldenFrame Whether the thread has a golden frame around it.
 */
@Serializable
public data class ThreadEntry(
    override val title: String,
    override val threadId: Int,
    val authorName: String,
    val isAuthorTraded: Boolean,
    val isAuthorDeleted: Boolean,
    val emoticon: ForumEmoticon?,
    val repliesCount: Int,
    val viewsCount: Int,
    val lastPost: LastPost,
    val status: Set<ThreadStatus>,
    val pages: Int,
    val hasGoldenFrame: Boolean,
) : BaseForumThread
