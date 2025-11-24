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

package com.galarzaa.tibiakt.core.section.forum.thread.builder

import com.galarzaa.tibiakt.core.builder.BuilderDsl
import com.galarzaa.tibiakt.core.builder.TibiaKtBuilder
import com.galarzaa.tibiakt.core.builder.requireField
import com.galarzaa.tibiakt.core.section.forum.shared.model.ForumAuthor
import com.galarzaa.tibiakt.core.section.forum.shared.model.ForumEmoticon
import com.galarzaa.tibiakt.core.section.forum.thread.model.ForumPost
import com.galarzaa.tibiakt.core.section.forum.thread.model.ForumThread
import kotlin.time.Clock
import kotlin.time.Instant

@BuilderDsl
internal inline fun forumThreadBuilder(block: ForumThreadBuilder.() -> Unit): ForumThreadBuilder =
    ForumThreadBuilder().apply(block)

@BuilderDsl
internal inline fun forumThread(block: ForumThreadBuilder.() -> Unit): ForumThread = forumThreadBuilder(block).build()

/** Builder for [ForumThread] instances. */
@BuilderDsl
internal class ForumThreadBuilder : TibiaKtBuilder<ForumThread> {
    var title: String? = null
    var threadId: Int? = null
    var boardName: String? = null
    var boardId: Int? = null
    var sectionName: String? = null
    var sectionId: Int? = null
    var previousTopicNumber: Int? = null
    var nextTopicNumber: Int? = null
    var hasGoldenFrame: Boolean = false
    var anchoredPost: ForumPost? = null
    var currentPage: Int = 1
    var totalPages: Int = 1
    var resultsCount: Int = 1
    val entries: MutableList<ForumPost> = mutableListOf()

    fun addPost(post: ForumPost): ForumThreadBuilder = apply { entries.add(post) }

    @BuilderDsl
    fun addPost(block: ForumPostBuilder.() -> Unit): ForumThreadBuilder =
        apply { entries.add(ForumPostBuilder().apply(block).build()) }


    override fun build(): ForumThread = ForumThread(
        title = requireField(title, "title"),
        threadId = requireField(threadId, "threadId"),
        boardName = requireField(boardName, "boardName"),
        boardId = requireField(boardId, "boardId"),
        sectionName = requireField(sectionName, "sectionName"),
        sectionId = requireField(sectionId, "sectionId"),
        previousTopicNumber = previousTopicNumber,
        nextTopicNumber = nextTopicNumber,
        hasGoldenFrame = hasGoldenFrame,
        anchoredPost = anchoredPost,
        currentPage = currentPage,
        totalPages = totalPages,
        resultsCount = resultsCount,
        entries = entries
    )

    class ForumPostBuilder : TibiaKtBuilder<ForumPost> {
        var author: ForumAuthor? = null
        var emoticon: ForumEmoticon? = null
        var title: String? = null
        var content: String? = null
        var signature: String? = null
        var postId: Int? = null
        var postedDate: Instant? = null
        var editedDate: Instant? = null
        var editedBy: String? = null

        override fun build(): ForumPost = ForumPost(
            author = requireField(author, "author"),
            emoticon = emoticon,
            title = title,
            content = requireField(content, "content"),
            signature = signature,
            postId = requireField(postId, "postId"),
            postedAt = postedDate ?: Clock.System.now(),
            editedAt = editedDate,
            editedBy = editedBy
        )
    }
}
