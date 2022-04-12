package com.galarzaa.tibiakt.core.builders

import com.galarzaa.tibiakt.core.models.forums.ForumAnnouncement
import com.galarzaa.tibiakt.core.models.forums.ForumAuthor
import java.time.Instant

inline fun forumAnnouncementBuilder(block: ForumAnnouncementBuilder.() -> Unit) = ForumAnnouncementBuilder().apply(block)
inline fun forumAnnouncement(block: ForumAnnouncementBuilder.() -> Unit) = forumAnnouncementBuilder(block).build()

class ForumAnnouncementBuilder {
    var announcementId: Int? = null
    var title: String? = null
    var board: String? = null
    var boardId: Int? = null
    var section: String? = null
    var sectionId: Int? = null
    var author: ForumAuthor? = null
    var content: String? = null
    var startDate: Instant? = null
    var endDate: Instant? = null

    fun build(): ForumAnnouncement {
        return ForumAnnouncement(
            announcementId = announcementId ?: throw IllegalStateException("announcementId is required"),
            title = title ?: throw IllegalStateException("title is required"),
            board = board ?: throw IllegalStateException("board is required"),
            boardId = boardId ?: throw IllegalStateException("boardId is required"),
            section = section ?: throw IllegalStateException("section is required"),
            sectionId = sectionId ?: throw IllegalStateException("sectionId is required"),
            author = author ?: throw IllegalStateException("author is required"),
            content = content ?: throw IllegalStateException("content is required"),
            startDate = startDate ?: throw IllegalStateException("startDate is required"),
            endDate = endDate ?: throw IllegalStateException("endDate is required")
        )
    }
}