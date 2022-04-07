package com.galarzaa.tibiakt.core.models.forums

import com.galarzaa.tibiakt.core.utils.getForumBoardUrl

/**
 * Base interface for forum boards.
 *
 * @property boardId The internal ID of the board. Used for linking.
 */
interface BaseForumBoard {
    val boardId: Int

    /** The URL to this board. */
    val url get() = getForumBoardUrl(boardId)
}