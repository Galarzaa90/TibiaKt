package com.galarzaa.tibiakt.core.builders

import com.galarzaa.tibiakt.core.models.forums.BaseForumAuthor
import com.galarzaa.tibiakt.core.models.forums.ForumEmoticon
import com.galarzaa.tibiakt.core.models.forums.ForumPost
import com.galarzaa.tibiakt.core.models.forums.ForumThread
import com.galarzaa.tibiakt.core.utils.BuilderDsl
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

@BuilderDsl
inline fun forumThreadBuilder(block: ForumThreadBuilder.() -> Unit) = ForumThreadBuilder().apply(block)

@BuilderDsl
inline fun forumThread(block: ForumThreadBuilder.() -> Unit) = forumThreadBuilder(block).build()

@BuilderDsl
class ForumThreadBuilder : TibiaKtBuilder<ForumThread>() {
    var title: String? = null
    var threadId: Int? = null
    var board: String? = null
    var boardId: Int? = null
    var section: String? = null
    var sectionId: Int? = null
    var previousTopicNumber: Int? = null
    var nextTopicNumber: Int? = null
    var goldenFrame: Boolean = false
    var anchoredPost: ForumPost? = null
    var currentPage: Int = 1
    var totalPages: Int = 1
    var resultsCount: Int = 1
    val entries = mutableListOf<ForumPost>()

    fun addPost(post: ForumPost) = apply { entries.add(post) }

    @BuilderDsl
    fun addPost(block: ForumPostBuilder.() -> Unit) = apply { entries.add(ForumPostBuilder().apply(block).build()) }


    override fun build() = ForumThread(title = title ?: throw IllegalStateException("title is required"),
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
        entries = entries)

    class ForumPostBuilder : TibiaKtBuilder<ForumPost>() {
        var author: BaseForumAuthor? = null
        var emoticon: ForumEmoticon? = null
        var title: String? = null
        var content: String? = null
        var signature: String? = null
        var postId: Int? = null
        var postedDate: Instant? = null
        var editedDate: Instant? = null
        var editedBy: String? = null

        override fun build() = ForumPost(
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
