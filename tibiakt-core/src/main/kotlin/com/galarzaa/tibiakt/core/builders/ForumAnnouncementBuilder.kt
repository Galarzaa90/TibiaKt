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

package com.galarzaa.tibiakt.core.builders

import com.galarzaa.tibiakt.core.models.forums.BaseForumAuthor
import com.galarzaa.tibiakt.core.models.forums.ForumAnnouncement
import kotlin.time.Instant

@BuilderDsl
public inline fun forumAnnouncementBuilder(block: ForumAnnouncementBuilder.() -> Unit): ForumAnnouncementBuilder =
    ForumAnnouncementBuilder().apply(block)

@BuilderDsl
public inline fun forumAnnouncement(block: ForumAnnouncementBuilder.() -> Unit): ForumAnnouncement =
    forumAnnouncementBuilder(block).build()

/** Builder for [ForumAnnouncement] instances. */
@BuilderDsl
public class ForumAnnouncementBuilder : TibiaKtBuilder<ForumAnnouncement> {
    public var announcementId: Int? = null
    public var title: String? = null
    public var board: String? = null
    public var boardId: Int? = null
    public var section: String? = null
    public var sectionId: Int? = null
    public var author: BaseForumAuthor? = null
    public var content: String? = null
    public var startDate: Instant? = null
    public var endDate: Instant? = null

    override fun build(): ForumAnnouncement = ForumAnnouncement(
        announcementId = announcementId ?: error("announcementId is required"),
        title = title ?: error("title is required"),
        boardName = board ?: error("board is required"),
        boardId = boardId ?: error("boardId is required"),
        sectionName = section ?: error("section is required"),
        sectionId = sectionId ?: error("sectionId is required"),
        author = author ?: error("author is required"),
        content = content ?: error("content is required"),
        startsAt = startDate ?: error("startDate is required"),
        endsAt = endDate ?: error("endDate is required")
    )
}
