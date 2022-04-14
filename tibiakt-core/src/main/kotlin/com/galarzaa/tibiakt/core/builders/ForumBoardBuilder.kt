package com.galarzaa.tibiakt.core.builders

import com.galarzaa.tibiakt.core.enums.ThreadStatus
import com.galarzaa.tibiakt.core.models.forums.AnnouncementEntry
import com.galarzaa.tibiakt.core.models.forums.ForumBoard
import com.galarzaa.tibiakt.core.models.forums.ForumEmoticon
import com.galarzaa.tibiakt.core.models.forums.LastPost
import com.galarzaa.tibiakt.core.models.forums.ThreadEntry
import com.galarzaa.tibiakt.core.utils.BuilderDsl

inline fun forumBoardBuilder(block: ForumBoardBuilder.() -> Unit) = ForumBoardBuilder().apply(block)
inline fun forumBoard(block: ForumBoardBuilder.() -> Unit) = forumBoardBuilder(block).build()

@BuilderDsl
class ForumBoardBuilder {
    var name: String? = null
    var boardId: Int? = null
    var sectionId: Int? = null
    var section: String? = null
    var threadAge: Int = 30
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

    fun build() = ForumBoard(
        name = name ?: throw IllegalStateException("name is required"),
        boardId = boardId ?: throw IllegalStateException("boardId is required"),
        sectionId = sectionId ?: throw IllegalStateException("sectionId is required"),
        section = section ?: throw IllegalStateException("section is required"),
        threadAge = threadAge,
        currentPage = currentPage,
        totalPages = totalPages,
        resultsCount = resultsCount,
        announcements = announcements,
        entries = entries
    )
}

@BuilderDsl
class AnnouncementEntryBuilder {
    var title: String? = null
    var announcementId: Int? = null
    var author: String? = null

    fun build() = AnnouncementEntry(
        title = title ?: throw IllegalStateException("title is required"),
        announcementId = announcementId  ?: throw IllegalStateException("announcementId is required"),
        author = author  ?: throw IllegalStateException("author is required")
    )
}

@BuilderDsl
class ThreadEntryBuilder {
    var title: String? = null
    var threadId: Int? = null
    var author: String? = null
    var authorTraded: Boolean = false
    var authorDeleted: Boolean = false
    var replies: Int = 0
    var emoticon: ForumEmoticon? = null
    var views: Int = 0
    var lastPost: LastPost? = null
    val status: MutableSet<ThreadStatus> = mutableSetOf()
    var pages: Int = 1
    var goldenFrame: Boolean = false

    fun build() = ThreadEntry(
        title = title ?: throw IllegalStateException("title is required"),
        threadId = threadId ?: throw IllegalStateException("threadId is required"),
        author = author ?: throw IllegalStateException("author is required"),
        authorTraded = authorTraded,
        authorDeleted = authorDeleted,
        emoticon = emoticon,
        replies = replies,
        views = views,
        status = status,
        lastPost = lastPost ?: throw IllegalStateException("lastPost is required"),
        pages = pages,
        goldenFrame = goldenFrame
    )
}