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

import com.galarzaa.tibiakt.core.net.forumThreadUrl

/**
 * Base interface for forum related classes.
 */
public interface BaseForumThread {
    /**
     * The title of the thread.
     */
    public val title: String

    /**
     * The internal ID of the thread.
     */
    public val threadId: Int

    /**
     * The URL to the forum thread.
     */
    public val url: String get() = forumThreadUrl(threadId)


}
