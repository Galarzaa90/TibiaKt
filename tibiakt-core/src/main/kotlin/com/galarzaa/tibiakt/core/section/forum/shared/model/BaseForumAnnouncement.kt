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

import com.galarzaa.tibiakt.core.section.forum.urls.forumAnnouncementUrl

/** Base interface for forum announcements.
 *
 * @property title The title of the announcement.
 * @property announcementId The internal ID of the announcement, used for linking.
 */
public interface BaseForumAnnouncement {
    public val title: String
    public val announcementId: Int

    /**
     * The URL to go to the specific announcement.
     */
    public val url: String get() = forumAnnouncementUrl(announcementId)
}
