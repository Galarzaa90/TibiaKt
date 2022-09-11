package com.galarzaa.tibiakt.core.builders

import com.galarzaa.tibiakt.core.models.forums.BoardEntry
import com.galarzaa.tibiakt.core.models.forums.ForumSection
import com.galarzaa.tibiakt.core.models.forums.LastPost


public inline fun forumsSectionBuilder(block: ForumsSectionBuilder.() -> Unit): ForumsSectionBuilder =
    ForumsSectionBuilder().apply(block)

public inline fun forumsSection(block: ForumsSectionBuilder.() -> Unit): ForumSection =
    forumsSectionBuilder(block).build()

public class ForumsSectionBuilder : TibiaKtBuilder<ForumSection>() {
    public var sectionId: Int = 0
    public val entries: MutableList<BoardEntry> = mutableListOf()

    public fun addEntry(block: BoardEntryBuilder.() -> Unit) {
        entries.add(BoardEntryBuilder().apply(block).build())
    }

    override fun build(): ForumSection = ForumSection(
        sectionId = sectionId,
        entries = entries
    )

    public class BoardEntryBuilder : TibiaKtBuilder<BoardEntry>() {
        public var name: String? = null
        public var boardId: Int = 0
        public var description: String? = null
        public var posts: Int = 0
        public var threads: Int = 0
        public var lastPost: LastPost? = null

        override fun build(): BoardEntry = BoardEntry(
            name = name ?: throw IllegalArgumentException("name is required"),
            boardId = boardId,
            description = description ?: throw IllegalArgumentException("description is required"),
            posts = posts,
            threads = threads,
            lastPost = lastPost
        )
    }
}
