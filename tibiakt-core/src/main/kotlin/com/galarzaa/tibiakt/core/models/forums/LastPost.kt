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

import com.galarzaa.tibiakt.core.utils.getCharacterUrl
import kotlin.time.Instant
import kotlinx.serialization.Serializable

/**
 * Information about the last post in a board or thread.
 *
 * @property author The name of the character that made the last post.
 * @property postId The internal ID of the last post.
 * @property date The date and time when the post was published.
 * @property deleted Whether the author of the post is currently deleted.
 * @property traded Whether the post was made by a character that was traded afterwards.
 */
@Serializable
public data class LastPost(
    val author: String,
    override val postId: Int,
    val date: Instant,
    val deleted: Boolean,
    val traded: Boolean,
) : BaseForumPost {
    /** The URL to the author's character page. If the author is deleted, there is no URL. */
    val authorUrl: String? get() = if (!deleted) getCharacterUrl(author) else null
}
