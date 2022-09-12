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

import com.galarzaa.tibiakt.core.enums.ThreadStatus
import kotlinx.serialization.Serializable

/** A thread entry in the forums. */
@Serializable
public data class ThreadEntry(
    val title: String,
    val threadId: Int,
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
)
