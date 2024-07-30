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

import com.galarzaa.tibiakt.core.enums.ThreadStatus
import kotlinx.serialization.Serializable

/**
 * A thread entry in the forums.
 *
 * @property author The name of the author that created the thread.
 * @property isAuthorTraded Whether the author's character was traded after the thread was created.
 * @property isAuthorDeleted Whether the thread author is now deleted.
 * @property emoticon The selected emoticon for the thread.
 * @property replies The total number of replies the thread has.
 * @property views The total number of views the thread has.
 * @property lastPost Brief details of the last post.
 * @property status The status the thread has.
 * @property pages The total number of pages the thread has.
 * @property goldenFrame Whether the thread has a golden frame around it.
 */
@Serializable
public data class ThreadEntry(
    override val title: String,
    override val threadId: Int,
    val author: String,
    val isAuthorTraded: Boolean,
    val isAuthorDeleted: Boolean,
    val emoticon: ForumEmoticon?,
    val replies: Int,
    val views: Int,
    val lastPost: LastPost,
    val status: Set<ThreadStatus>,
    val pages: Int,
    val goldenFrame: Boolean,
) : BaseForumThread
