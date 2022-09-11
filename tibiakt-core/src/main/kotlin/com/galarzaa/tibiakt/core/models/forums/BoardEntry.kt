package com.galarzaa.tibiakt.core.models.forums

import kotlinx.serialization.Serializable

/**
 * A forum board listen within a [ForumSection].
 *
 * @property name The name of the board.
 * @property description The description of the board.
 * @property posts The number of posts in the board.
 * @property threads The number of threads in the board.
 * @property lastPost The last post in the board.
 */
@Serializable
public data class BoardEntry(
    val name: String,
    override val boardId: Int,
    val description: String,
    val posts: Int,
    val threads: Int,
    val lastPost: LastPost?,
) : BaseForumBoard
