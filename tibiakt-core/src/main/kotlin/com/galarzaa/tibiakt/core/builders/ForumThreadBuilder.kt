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
import com.galarzaa.tibiakt.core.models.forums.ForumEmoticon
import com.galarzaa.tibiakt.core.models.forums.ForumPost
import com.galarzaa.tibiakt.core.models.forums.ForumThread
import kotlin.time.Clock
import kotlin.time.Instant

@BuilderDsl
public inline fun forumThreadBuilder(block: ForumThreadBuilder.() -> Unit): ForumThreadBuilder =
    ForumThreadBuilder().apply(block)

@BuilderDsl
public inline fun forumThread(block: ForumThreadBuilder.() -> Unit): ForumThread = forumThreadBuilder(block).build()

/** Builder for [ForumThread] instances. */
@BuilderDsl
public class ForumThreadBuilder : TibiaKtBuilder<ForumThread> {
    public var title: String? = null
    public var threadId: Int? = null
    public var board: String? = null
    public var boardId: Int? = null
    public var section: String? = null
    public var sectionId: Int? = null
    public var previousTopicNumber: Int? = null
    public var nextTopicNumber: Int? = null
    public var hasGoldenFrame: Boolean = false
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
        title = title ?: error("title is required"),
        threadId = threadId ?: error("threadId is required"),
        board = board ?: error("board is required"),
        boardId = boardId ?: error("boardId is required"),
        section = section ?: error("section is required"),
        sectionId = sectionId ?: error("sectionId is required"),
        previousTopicNumber = previousTopicNumber,
        nextTopicNumber = nextTopicNumber,
        goldenFrame = hasGoldenFrame,
        anchoredPost = anchoredPost,
        currentPage = currentPage,
        totalPages = totalPages,
        resultsCount = resultsCount,
        entries = entries
    )

    public class ForumPostBuilder : TibiaKtBuilder<ForumPost> {
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
            author = author ?: error("author is required"),
            emoticon = emoticon,
            title = title,
            content = content ?: error("content is required"),
            signature = signature,
            postId = postId ?: error("postId is required"),
            postedAt = postedDate ?: Clock.System.now(),
            editedAt = editedDate,
            editedBy = editedBy
        )
    }
}
