package com.galarzaa.tibiakt.core.builders

import com.galarzaa.tibiakt.core.models.forums.BoardEntry
import com.galarzaa.tibiakt.core.models.forums.ForumSection
import com.galarzaa.tibiakt.core.models.forums.LastPost


inline fun forumsSectionBuilder(block: ForumsSectionBuilder.() -> Unit) = ForumsSectionBuilder().apply(block)
inline fun forumsSection(block: ForumsSectionBuilder.() -> Unit) = forumsSectionBuilder(block).build()

inline fun boardEntryBuilder(block: BoardEntryBuilder.() -> Unit) = BoardEntryBuilder().apply(block)
inline fun boardEntry(block: BoardEntryBuilder.() -> Unit) = boardEntryBuilder(block).build()

class ForumsSectionBuilder {
    var sectionId: Int = 0
    val entries: MutableList<BoardEntry> = mutableListOf()

    fun addEntry(block: BoardEntryBuilder.() -> Unit) {
        entries.add(boardEntry(block))
    }

    fun build() = ForumSection(
        sectionId = sectionId,
        entries = entries
    )
}

class BoardEntryBuilder {
    var name: String? = null
    var boardId: Int = 0
    var description: String? = null
    var posts: Int = 0
    var threads: Int = 0
    var lastPost: LastPost? = null

    fun build() = BoardEntry(
        name = name ?: throw IllegalArgumentException("name is required"),
        boardId = boardId,
        description = description ?: throw IllegalArgumentException("description is required"),
        posts = posts,
        threads = threads,
        lastPost = lastPost
    )
}