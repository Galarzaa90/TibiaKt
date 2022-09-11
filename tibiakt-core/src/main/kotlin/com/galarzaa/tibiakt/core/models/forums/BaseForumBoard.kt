package com.galarzaa.tibiakt.core.models.forums

import com.galarzaa.tibiakt.core.utils.getForumBoardUrl

/**
 * Base interface for forum boards.
 *
 * @property boardId The internal ID of the board. Used for linking.
 */
public interface BaseForumBoard {
    public val boardId: Int

    /** The URL to this board. */
    public val url: String get() = getForumBoardUrl(boardId)
}
