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

import kotlin.time.Instant
import kotlinx.serialization.Serializable

/** A post in a [ForumThread].
 *
 * @property author The author of the post.
 * @property emoticon The emoticon chosen for the post, if any.
 * @property title The title of the post, if any.
 * @property content The HTML content of the post.
 * @property signature The signature of the post's author.
 * @property postId The ID of the post.
 * @property postedDate The date when the post was created.
 * @property editedDate The date when the post was last edited. Null if it hasn't been edited.
 * @property editedBy The name of the character that last edited the post. CipSoft members might edit posts.
 */
@Serializable
public data class ForumPost(
    val author: BaseForumAuthor,
    val emoticon: ForumEmoticon?,
    val title: String?,
    val content: String,
    val signature: String?,
    override val postId: Int,
    val postedDate: Instant,
    val editedDate: Instant?,
    val editedBy: String?,
) : BaseForumPost
