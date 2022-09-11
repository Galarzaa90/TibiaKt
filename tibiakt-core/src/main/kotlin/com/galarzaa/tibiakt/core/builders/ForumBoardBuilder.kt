package com.galarzaa.tibiakt.core.builders

import com.galarzaa.tibiakt.core.enums.ThreadStatus
import com.galarzaa.tibiakt.core.models.forums.AnnouncementEntry
import com.galarzaa.tibiakt.core.models.forums.ForumBoard
import com.galarzaa.tibiakt.core.models.forums.ForumEmoticon
import com.galarzaa.tibiakt.core.models.forums.LastPost
import com.galarzaa.tibiakt.core.models.forums.ThreadEntry
import com.galarzaa.tibiakt.core.utils.BuilderDsl

public inline fun forumBoardBuilder(block: ForumBoardBuilder.() -> Unit): ForumBoardBuilder =
    ForumBoardBuilder().apply(block)

public inline fun forumBoard(block: ForumBoardBuilder.() -> Unit): ForumBoard = forumBoardBuilder(block).build()

@BuilderDsl
public class ForumBoardBuilder : TibiaKtBuilder<ForumBoard>() {
    public var name: String? = null
    public var boardId: Int? = null
    public var sectionId: Int? = null
    public var section: String? = null
    public var threadAge: Int = 30
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

    @BuilderDsl
    public class AnnouncementEntryBuilder : TibiaKtBuilder<AnnouncementEntry>() {
        public var title: String? = null
        public var announcementId: Int? = null
        public var author: String? = null

        override fun build(): AnnouncementEntry = AnnouncementEntry(
            title = title ?: throw IllegalStateException("title is required"),
            announcementId = announcementId ?: throw IllegalStateException("announcementId is required"),
            author = author ?: throw IllegalStateException("author is required")
        )
    }

    @BuilderDsl
    public class ThreadEntryBuilder : TibiaKtBuilder<ThreadEntry>() {
        public var title: String? = null
        public var threadId: Int? = null
        public var author: String? = null
        public var authorTraded: Boolean = false
        public var authorDeleted: Boolean = false
        public var replies: Int = 0
        public var emoticon: ForumEmoticon? = null
        public var views: Int = 0
        public var lastPost: LastPost? = null
        public val status: MutableSet<ThreadStatus> = mutableSetOf()
        public var pages: Int = 1
        public var goldenFrame: Boolean = false

        override fun build(): ThreadEntry = ThreadEntry(
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
}
