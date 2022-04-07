package com.galarzaa.tibiakt.core.parsers

import com.galarzaa.tibiakt.core.builders.forumBoard
import com.galarzaa.tibiakt.core.exceptions.ParsingException
import com.galarzaa.tibiakt.core.models.forums.ForumBoard
import com.galarzaa.tibiakt.core.utils.cleanText

object ForumBoardParser : Parser<ForumBoard?> {
    override fun fromContent(content: String): ForumBoard? {
        val boxContent = boxContent(content)
        val forumBreadcrumbs = boxContent.selectFirst("div.ForumBreadCrumbs")
        if (forumBreadcrumbs == null) {
            boxContent.selectFirst("div.InnerTableContainer")?.takeIf { "board you requested" in it.cleanText() }
                ?: throw ParsingException("content does not belong to a board.")
            return null
        }
        return forumBoard {
            val headerText = forumBreadcrumbs.cleanText()
            val headerParts = headerText.split("|").map { it.trim() }
            name = headerParts.last()
            section = headerParts.first()
            boardId = 0
        }
    }
}