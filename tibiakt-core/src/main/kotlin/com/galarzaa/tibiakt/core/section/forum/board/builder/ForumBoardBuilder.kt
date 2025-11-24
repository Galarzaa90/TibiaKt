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

package com.galarzaa.tibiakt.core.section.forum.board.builder

import com.galarzaa.tibiakt.core.builder.BuilderDsl
import com.galarzaa.tibiakt.core.builder.TibiaKtBuilder
import com.galarzaa.tibiakt.core.builder.requireField
import com.galarzaa.tibiakt.core.section.forum.board.model.AnnouncementEntry
import com.galarzaa.tibiakt.core.section.forum.board.model.DEFAULT_THREAD_AGE
import com.galarzaa.tibiakt.core.section.forum.board.model.ForumBoard
import com.galarzaa.tibiakt.core.section.forum.board.model.ThreadEntry
import com.galarzaa.tibiakt.core.section.forum.shared.model.ForumEmoticon
import com.galarzaa.tibiakt.core.section.forum.shared.model.LastPost
import com.galarzaa.tibiakt.core.section.forum.shared.model.ThreadStatus

internal inline fun forumBoardBuilder(block: ForumBoardBuilder.() -> Unit): ForumBoardBuilder =
    ForumBoardBuilder().apply(block)

internal inline fun forumBoard(block: ForumBoardBuilder.() -> Unit): ForumBoard = forumBoardBuilder(block).build()

/** Builder for [ForumBoard] instances. */
@BuilderDsl
internal class ForumBoardBuilder : TibiaKtBuilder<ForumBoard> {
    var name: String? = null
    var boardId: Int? = null
    var sectionId: Int? = null
    var sectionName: String? = null
    var threadAge: Int = DEFAULT_THREAD_AGE
    var currentPage: Int = 1
    var totalPages: Int = 1
    var resultsCount: Int = 0
    val announcements: MutableList<AnnouncementEntry> = mutableListOf()
    val entries: MutableList<ThreadEntry> = mutableListOf()

    fun announcement(block: AnnouncementEntryBuilder.() -> Unit) {
        announcements.add(AnnouncementEntryBuilder().apply(block).build())
    }

    fun thread(block: ThreadEntryBuilder.() -> Unit) {
        entries.add(ThreadEntryBuilder().apply(block).build())
    }

    override fun build(): ForumBoard = ForumBoard(
        name = name ?: error("name is required"),
        boardId = boardId ?: error("boardId is required"),
        sectionId = sectionId ?: error("sectionId is required"),
        sectionName = sectionName ?: error("sectionName is required"),
        threadAge = threadAge,
        currentPage = currentPage,
        totalPages = totalPages,
        resultsCount = resultsCount,
        announcements = announcements,
        entries = entries
    )

    @BuilderDsl
    class AnnouncementEntryBuilder : TibiaKtBuilder<AnnouncementEntry> {
        var title: String? = null
        var announcementId: Int? = null
        var author: String? = null

        override fun build(): AnnouncementEntry = AnnouncementEntry(
            title = title ?: error("title is required"),
            announcementId = announcementId ?: error("announcementId is required"),
            authorName = author ?: error("author is required")
        )
    }

    @BuilderDsl
    class ThreadEntryBuilder : TibiaKtBuilder<ThreadEntry> {
        var title: String? = null
        var threadId: Int? = null
        var authorName: String? = null
        var isAuthorTraded: Boolean = false
        var isAuthorDeleted: Boolean = false
        var repliesCount: Int = 0
        var emoticon: ForumEmoticon? = null
        var viewsCount: Int = 0
        var lastPost: LastPost? = null
        val status: MutableSet<ThreadStatus> = mutableSetOf()
        var pages: Int = 1
        var hasGoldenFrame: Boolean = false

        override fun build(): ThreadEntry = ThreadEntry(
            title = requireField(title, "title"),
            threadId = requireField(threadId, "threadId"),
            authorName = requireField(authorName, "authorName"),
            authorIsTraded = isAuthorTraded,
            authorIsDeleted = isAuthorDeleted,
            emoticon = emoticon,
            repliesCount = repliesCount,
            viewsCount = viewsCount,
            status = status,
            lastPost = requireField(lastPost, "lastPost"),
            pages = pages,
            hasGoldenFrame = hasGoldenFrame
        )
    }
}
