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

import com.galarzaa.tibiakt.core.enums.ThreadStatus
import com.galarzaa.tibiakt.core.models.forums.AnnouncementEntry
import com.galarzaa.tibiakt.core.models.forums.DEFAULT_THREAD_AGE
import com.galarzaa.tibiakt.core.models.forums.ForumBoard
import com.galarzaa.tibiakt.core.models.forums.ForumEmoticon
import com.galarzaa.tibiakt.core.models.forums.LastPost
import com.galarzaa.tibiakt.core.models.forums.ThreadEntry
import com.galarzaa.tibiakt.core.utils.BuilderDsl

public inline fun forumBoardBuilder(block: ForumBoardBuilder.() -> Unit): ForumBoardBuilder =
    ForumBoardBuilder().apply(block)

public inline fun forumBoard(block: ForumBoardBuilder.() -> Unit): ForumBoard = forumBoardBuilder(block).build()

/** Builder for [ForumBoard] instances. */
@BuilderDsl
public class ForumBoardBuilder : TibiaKtBuilder<ForumBoard> {
    public var name: String? = null
    public var boardId: Int? = null
    public var sectionId: Int? = null
    public var section: String? = null
    public var threadAge: Int = DEFAULT_THREAD_AGE
    public var currentPage: Int = 1
    public var totalPages: Int = 1
    public var resultsCount: Int = 0
    public val announcements: MutableList<AnnouncementEntry> = mutableListOf()
    public val entries: MutableList<ThreadEntry> = mutableListOf()

    public fun announcement(block: AnnouncementEntryBuilder.() -> Unit) {
        announcements.add(AnnouncementEntryBuilder().apply(block).build())
    }

    public fun thread(block: ThreadEntryBuilder.() -> Unit) {
        entries.add(ThreadEntryBuilder().apply(block).build())
    }

    override fun build(): ForumBoard = ForumBoard(
        name = name ?: error("name is required"),
        boardId = boardId ?: error("boardId is required"),
        sectionId = sectionId ?: error("sectionId is required"),
        section = section ?: error("section is required"),
        threadAge = threadAge,
        currentPage = currentPage,
        totalPages = totalPages,
        resultsCount = resultsCount,
        announcements = announcements,
        entries = entries
    )

    @BuilderDsl
    public class AnnouncementEntryBuilder : TibiaKtBuilder<AnnouncementEntry> {
        public var title: String? = null
        public var announcementId: Int? = null
        public var author: String? = null

        override fun build(): AnnouncementEntry = AnnouncementEntry(
            title = title ?: error("title is required"),
            announcementId = announcementId ?: error("announcementId is required"),
            author = author ?: error("author is required")
        )
    }

    @BuilderDsl
    public class ThreadEntryBuilder : TibiaKtBuilder<ThreadEntry> {
        public var title: String? = null
        public var threadId: Int? = null
        public var author: String? = null
        public var isAuthorTraded: Boolean = false
        public var isAuthorDeleted: Boolean = false
        public var replies: Int = 0
        public var emoticon: ForumEmoticon? = null
        public var views: Int = 0
        public var lastPost: LastPost? = null
        public val status: MutableSet<ThreadStatus> = mutableSetOf()
        public var pages: Int = 1
        public var hasGoldenFrame: Boolean = false

        override fun build(): ThreadEntry = ThreadEntry(
            title = title ?: error("title is required"),
            threadId = threadId ?: error("threadId is required"),
            author = author ?: error("author is required"),
            isAuthorTraded = isAuthorTraded,
            isAuthorDeleted = isAuthorDeleted,
            emoticon = emoticon,
            replies = replies,
            views = views,
            status = status,
            lastPost = lastPost ?: error("lastPost is required"),
            pages = pages,
            goldenFrame = hasGoldenFrame
        )
    }
}
