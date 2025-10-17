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

import com.galarzaa.tibiakt.core.net.characterUrl
import kotlinx.serialization.Serializable

/**
 * An announcement listed on a [ForumBoard].
 *
 * The detailed view is represented by [ForumAnnouncement].
 *
 * @property authorName The name of the character posting the announcement.
 */
@Serializable
public data class AnnouncementEntry(
    override val title: String,
    override val announcementId: Int,
    val authorName: String,
) : BaseForumAnnouncement {

    /** The URL to the author's character page. */
    val authorUrl: String get() = characterUrl(authorName)
}
