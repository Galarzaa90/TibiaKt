package com.galarzaa.tibiakt.core.builders

import com.galarzaa.tibiakt.core.models.forums.ForumBoard
import com.galarzaa.tibiakt.core.models.forums.ForumEntry

inline fun forumBoardBuilder(block: ForumBoardBuilder.() -> Unit) = ForumBoardBuilder().apply(block)
inline fun forumBoard(block: ForumBoardBuilder.() -> Unit) = forumBoardBuilder(block).build()

@TibiaKtDsl
class ForumBoardBuilder {
    var name: String? = null
    var boardId: Int? = null
    var section: String? = null
    var currentPage: Int = 1
    var totalPages: Int = 1
    var resultsCount: Int = 0
    val entries: MutableList<ForumEntry> = mutableListOf()

    fun build() = ForumBoard(
        name = name ?: throw IllegalStateException("name is required"),
        boardId = boardId  ?: throw IllegalStateException("boardId is required"),
        section = section  ?: throw IllegalStateException("section is required"),
        currentPage = currentPage,
        totalPages = totalPages,
        resultsCount = resultsCount,
        entries = entries
    )
}