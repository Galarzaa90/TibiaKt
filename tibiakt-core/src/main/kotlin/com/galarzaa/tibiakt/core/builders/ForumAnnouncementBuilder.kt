package com.galarzaa.tibiakt.core.builders

import com.galarzaa.tibiakt.core.models.forums.BaseForumAuthor
import com.galarzaa.tibiakt.core.models.forums.ForumAnnouncement
import com.galarzaa.tibiakt.core.utils.BuilderDsl
import kotlinx.datetime.Instant

@BuilderDsl
public inline fun forumAnnouncementBuilder(block: ForumAnnouncementBuilder.() -> Unit): ForumAnnouncementBuilder =
    ForumAnnouncementBuilder().apply(block)

@BuilderDsl
public inline fun forumAnnouncement(block: ForumAnnouncementBuilder.() -> Unit): ForumAnnouncement =
    forumAnnouncementBuilder(block).build()

@BuilderDsl
public class ForumAnnouncementBuilder : TibiaKtBuilder<ForumAnnouncement>() {
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
