package com.galarzaa.tibiakt.core.builders

import com.galarzaa.tibiakt.core.models.forums.BaseForumAuthor
import com.galarzaa.tibiakt.core.models.forums.ForumEmoticon
import com.galarzaa.tibiakt.core.models.forums.ForumPost
import com.galarzaa.tibiakt.core.models.forums.ForumThread
import com.galarzaa.tibiakt.core.utils.BuilderDsl
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

@BuilderDsl
public inline fun forumThreadBuilder(block: ForumThreadBuilder.() -> Unit): ForumThreadBuilder =
    ForumThreadBuilder().apply(block)

@BuilderDsl
public inline fun forumThread(block: ForumThreadBuilder.() -> Unit): ForumThread = forumThreadBuilder(block).build()

@BuilderDsl
public class ForumThreadBuilder : TibiaKtBuilder<ForumThread>() {
    public var title: String? = null
    public var threadId: Int? = null
    public var board: String? = null
    public var boardId: Int? = null
    public var section: String? = null
    public var sectionId: Int? = null
    public var previousTopicNumber: Int? = null
    public var nextTopicNumber: Int? = null
    public var goldenFrame: Boolean = false
    public var anchoredPost: ForumPost? = null
    public var currentPage: Int = 1
    public var totalPages: Int = 1
    public var resultsCount: Int = 1
    public val entries: MutableList<ForumPost> = mutableListOf()

    public fun addPost(post: ForumPost): ForumThreadBuilder = apply { entries.add(post) }

    @BuilderDsl
    public fun addPost(block: ForumPostBuilder.() -> Unit): ForumThreadBuilder =
        apply { entries.add(ForumPostBuilder().apply(block).build()) }


    override fun build(): ForumThread = ForumThread(
        title = title ?: throw IllegalStateException("title is required"),
        threadId = threadId ?: throw IllegalStateException("threadId is required"),
        board = board ?: throw IllegalStateException("board is required"),
        boardId = boardId ?: throw IllegalStateException("boardId is required"),
        section = section ?: throw IllegalStateException("section is required"),
        sectionId = sectionId ?: throw IllegalStateException("sectionId is required"),
        previousTopicNumber = previousTopicNumber,
        nextTopicNumber = nextTopicNumber,
        goldenFrame = goldenFrame,
        anchoredPost = anchoredPost,
        currentPage = currentPage,
        totalPages = totalPages,
        resultsCount = resultsCount,
        entries = entries
    )

    public class ForumPostBuilder : TibiaKtBuilder<ForumPost>() {
        public var author: BaseForumAuthor? = null
        public var emoticon: ForumEmoticon? = null
        public var title: String? = null
        public var content: String? = null
        public var signature: String? = null
        public var postId: Int? = null
        public var postedDate: Instant? = null
        public var editedDate: Instant? = null
        public var editedBy: String? = null

        override fun build(): ForumPost = ForumPost(
            author = author ?: throw IllegalStateException("author is required"),
            emoticon = emoticon,
            title = title,
            content = content ?: throw IllegalStateException("content is required"),
            signature = signature,
            postId = postId ?: throw IllegalStateException("postId is required"),
            postedDate = postedDate ?: Clock.System.now(),
            editedDate = editedDate,
            editedBy = editedBy
        )
    }
}
