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

package com.galarzaa.tibiakt.core.section.forum.announcement.builder

import com.galarzaa.tibiakt.core.builder.BuilderDsl
import com.galarzaa.tibiakt.core.builder.TibiaKtBuilder
import com.galarzaa.tibiakt.core.builder.requireField
import com.galarzaa.tibiakt.core.section.forum.announcement.model.ForumAnnouncement
import com.galarzaa.tibiakt.core.section.forum.shared.model.ForumAuthor
import kotlin.time.Instant

@BuilderDsl
internal inline fun forumAnnouncementBuilder(block: ForumAnnouncementBuilder.() -> Unit): ForumAnnouncementBuilder =
    ForumAnnouncementBuilder().apply(block)

@BuilderDsl
internal inline fun forumAnnouncement(block: ForumAnnouncementBuilder.() -> Unit): ForumAnnouncement =
    forumAnnouncementBuilder(block).build()

/** Builder for [ForumAnnouncement] instances. */
@BuilderDsl
internal class ForumAnnouncementBuilder : TibiaKtBuilder<ForumAnnouncement> {
    var announcementId: Int? = null
    var title: String? = null
    var boardName: String? = null
    var boardId: Int? = null
    var sectionName: String? = null
    var sectionId: Int? = null
    var author: ForumAuthor? = null
    var content: String? = null
    var startsAt: Instant? = null
    var endsAt: Instant? = null

    override fun build(): ForumAnnouncement = ForumAnnouncement(
        announcementId = requireField(announcementId, "announcementId"),
        title = requireField(title, "title"),
        boardName = requireField(boardName, "boardName"),
        boardId = requireField(boardId, "boardId"),
        sectionName = requireField(sectionName, "sectionName"),
        sectionId = requireField(sectionId, "sectionId"),
        author = requireField(author, "author"),
        content = requireField(content, "content"),
        startsAt = requireField(startsAt, "startsAt"),
        endsAt = requireField(endsAt, "endsAt"),
    )
}
