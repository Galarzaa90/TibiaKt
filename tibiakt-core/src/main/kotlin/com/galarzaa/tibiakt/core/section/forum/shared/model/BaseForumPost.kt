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

package com.galarzaa.tibiakt.core.section.forum.shared.model

import com.galarzaa.tibiakt.core.section.forum.urls.forumPostUrl

/**
 * Base interface for forum post related classes.
 *
 * @property postId The internal ID of the post.
 */
public interface BaseForumPost {
    public val postId: Int

    /** The URL to this post. */
    public val url: String get() = forumPostUrl(postId)
}
