package com.galarzaa.tibiakt.core.builders

import com.galarzaa.tibiakt.core.models.forums.BoardEntry
import com.galarzaa.tibiakt.core.models.forums.ForumSection
import com.galarzaa.tibiakt.core.models.forums.LastPost


inline fun forumsSectionBuilder(block: ForumsSectionBuilder.() -> Unit) = ForumsSectionBuilder().apply(block)
inline fun forumsSection(block: ForumsSectionBuilder.() -> Unit) = forumsSectionBuilder(block).build()

class ForumsSectionBuilder : TibiaKtBuilder<ForumSection>() {
    var sectionId: Int = 0
    val entries: MutableList<BoardEntry> = mutableListOf()

    fun addEntry(block: BoardEntryBuilder.() -> Unit) {
        entries.add(BoardEntryBuilder().apply(block).build())
    }

    override fun build() = ForumSection(
        sectionId = sectionId,
        entries = entries
    )

    class BoardEntryBuilder : TibiaKtBuilder<BoardEntry>() {
        var name: String? = null
        var boardId: Int = 0
        var description: String? = null
        var posts: Int = 0
        var threads: Int = 0
        var lastPost: LastPost? = null

        override fun build() = BoardEntry(
            name = name ?: throw IllegalArgumentException("name is required"),
            boardId = boardId,
            description = description ?: throw IllegalArgumentException("description is required"),
            posts = posts,
            threads = threads,
            lastPost = lastPost
        )
    }
}

